package client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.swing.JOptionPane;

import CloudManagment.*;
import print.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import loginCMP.*;
import server.ServerCMP;

public class ClientCMP {
	


	public static void main(String[] args) {

	    // Instance of the Client class
		ClientCMP client = new ClientCMP();

		/*THE RUN METHOD FIRST DISCOVER EACH SERVICE IN THE SERVER, GET THE HOST,
		 * PORT NUMBER AND FINALLY START A CHANNEL FOR EACH SERVICE-*/
		client.run(); // makes a connection channel with each service

		
		/* BECAUSE THE JMDN TAKES A WHILE TO DISCOVER ALL THE 3 SERVICES, I ALSO CREATED A SEPARATED METHOD
		 * FOR EACH. WHICH CAN BE CALLED INDIVIDUALLY, MAKING LIFE EASIER WHEN IT COMES TO TESTING*/
//		client.loginChannel();
//		client.printChannel();
//		client.cloudChannel();
		
		/*-------------------- LOGIN AND LOGOUT SERVICE (UNARY API)------------------*/
		//REQUIRES A CONNECTION TO THE LOGIN CANNEL
		
		//Username: Patryck	Password: test
		//login("Patryck", "test"); 
		
		// for logout it only needs to repeat the username, in this case: Patryck
		//logout("Patryck"); 
		
		/*-------------------- PRINTING SERVICE (SERVER STREAMING API) ------------------*/
		
		//The print service prints each data from database individually with
		//a interval of a second. IT REQUIRES A CONNECTION TO THE "PRINT CHANNEL"
		print();
		
		
		/*---------- CLOUD SERVICE (CLIENT STREAMING AND BIDIRECTIONAL STREAMING API) -----*/
		//IT REQUIRES A CONNECTION TO THE CLOUD CHANNEL 
		
		/*Insert data function takes the number of employees records you want to insert in the database
		*as parameter. It will request a series of questions about the employee, which will be validated
		*in the server side. For example:
		*-> Birth date and Hire date must be in the format (yyyy-mm-dd)
		*-> Employee Birthday must be over "1950-01-01" (Retirement age)
		*-> Employee also need to be older than 18 compared to the Hire Date
		*-> Employee's name only accepts letters*/
		
//		InsertData(2); // Call the function with the number of employees to be ADDED. The questions will be prompt to the user
		
		
		/*The remove method takes as parameter the number of employees to be removed.
		 *Then it will prompt the Employee number and Name, and use the "Linear Search"
		 *algorithm to search for the employee. If found it will be removed, otherwise 
		 *a message "not found" will be prompt */
		
//		removeData(1); // Call the function with the number of employees to be REMOVED. The questions will be prompt to the user

				
		

		
	} // End of Main
	
	
	
	/*-------- Discovering the services ---------------*/
	
	// jmdns - service info for each channel
	private static ServiceInfo serviceInfo_LoginChannel = null;
	private static ServiceInfo serviceInfo_PrintChannel = null;
	private static ServiceInfo serviceInfo_CloudChannel = null;
	
	public ServiceInfo discover(String str) {
		ServiceInfo serviceInfo = null;
		try {
    		DiscoverServices ds = new DiscoverServices();
            // Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." + local host. It gave me an error before
			//and the solution was to use this method and collect only the address after the "/" and write "local/" before
    		String address = InetAddress.getLocalHost().toString().split("/")[1];
            JmDNS jmdns = JmDNS.create("local/" + address);

    		//Catch the serviceInfo from the "DiscoverServices" class and returns it
            serviceInfo = ds.discoverService(str);
    		
            
            // Wait a bit
            Thread.sleep(6000);
            jmdns.close();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serviceInfo;
		
	}

	
	/*------------------------ CREATING A VARIABLE BLOCKING STUB OR ASYNC STUB FOR EACH SPECIFIC CLIENT CHANNEL ----------------*/
	private static LoginServiceGrpc.LoginServiceBlockingStub LoginClient;
	private static CloudServiceGrpc.CloudServiceStub CloudClient;
	private static printServiceGrpc.printServiceBlockingStub PrintClient;
	
	
	/* --------------------------- INITIALIZING A CHANNEL FOR EACH SERVICE --------------------------*/
	 //(make the connection faster since it only creates a channel for the specific service. Useful for tests)
    
    
   
	//This channel gives access to the login and logout services
	public void loginChannel() {
		
		// discover the service
	    String login_service_type = "_loginCMP._tcp.local.";
	    serviceInfo_LoginChannel = discover(login_service_type);
	    
	    /*----- Gets the local host and port number to be set on the channel -------*/
//	    String host = serviceInfo_LoginChannel.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
        int port = serviceInfo_LoginChannel.getPort();
		
		System.out.println("Starting login channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(channel);
		
	}
	
	//This channel gives access to the print and change storage services
	public void printChannel() {

		// discover the service
		String print_service_type = "_print._tcp.local.";
		serviceInfo_PrintChannel = discover(print_service_type);
		
		/*----- Gets the local host and port number to be set on the channel -------*/
//		String host = serviceInfo_PrintChannel.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
		int port = serviceInfo_PrintChannel.getPort(); 

		System.out.println("Starting Printing channel...");
	
	
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		PrintClient = printServiceGrpc.newBlockingStub(channel);

	}
	
	public void cloudChannel() {

		// discover the service
		String cloud_service_type = "_cloud._tcp.local.";
		serviceInfo_CloudChannel = discover(cloud_service_type);
		
		/*----- Gets the local host and port number to be set on the channel -------*/
//		String host = serviceInfo_CloudChannel.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
		int port = serviceInfo_CloudChannel.getPort();

		System.out.println("Starting Cloud channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		CloudClient = CloudServiceGrpc.newStub(channel);
				
	}
	
	/*------ THE RUN METHOD DISCOVER ALL THE 3 SERVICES FROM THE SERVER AND START A CHANNEL FOR EACH -------*/
	public void run() {
		
		System.out.println("Discovering services, please wait...");
		
		/*Decided to use thread to register each service because it is around 3 times faster than registering all together in a same function*/
		Thread t1 = new Thread()
		{
		    public void run() {
		    	String login_service_type = "_loginCMP._tcp.local.";
			    serviceInfo_LoginChannel = discover(login_service_type);
		    }
		};
		
		Thread t2 = new Thread()
		{
		    public void run() {
		    	String print_service_type = "_print._tcp.local.";
			    serviceInfo_PrintChannel = discover(print_service_type);
		    }
		};
		
		Thread t3 = new Thread()
		{
		    public void run() {
		    	String cloud_service_type = "_cloud._tcp.local.";
			    serviceInfo_CloudChannel = discover(cloud_service_type);
		    }
		}; 
		
		/* --- Discover each service information*/
		t1.start();
		t2.start();
		t3.start();
		
		try { //wait until all threads are done
			
			t1.join();
			t2.join();
			t3.join();
			
		} catch(Exception e) {
			
		}
		
		String login_service_type = "_loginCMP._tcp.local.";
	    serviceInfo_LoginChannel = discover(login_service_type);
	    
	    String print_service_type = "_print._tcp.local.";
	    serviceInfo_PrintChannel = discover(print_service_type);
	    
	    String cloud_service_type = "_cloud._tcp.local.";
	    serviceInfo_CloudChannel = discover(cloud_service_type);
		
//	    String host_Login = ""+serviceInfo_LoginChannel.getHostAddresses()[0];
		int port_Login = serviceInfo_LoginChannel.getPort();
//	    String host_Print = ""+serviceInfo_PrintChannel.getHostAddresses()[0];
		int port_Printrint = serviceInfo_PrintChannel.getPort();
//	    String host_Cloud = ""+serviceInfo_CloudChannel.getHostAddresses()[0];
		int port_Cloud = serviceInfo_CloudChannel.getPort();
	    
		System.out.println("Starting the Client Cloud Managment Platform...");
		
				
		ManagedChannel login_Channel = ManagedChannelBuilder.forAddress("localhost", port_Login)//  host: "localHost", port: 50051
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();
	
		ManagedChannel print_Channel = ManagedChannelBuilder.forAddress("localhost", port_Printrint)//  host: "localHost", port: 50050
			.usePlaintext() //force SSL to be deactivated during our development. (security measures)
			.build();
	
		ManagedChannel cloud_Channel = ManagedChannelBuilder.forAddress("localhost", port_Cloud)//  host: "localHost", port: 50052
			.usePlaintext() //force SSL to be deactivated during our development. (security measures)
			.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(login_Channel);
		CloudClient = CloudServiceGrpc.newStub(cloud_Channel);
		PrintClient = printServiceGrpc.newBlockingStub(print_Channel);
		
		// Prints a message when the server receives a shutdown request
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("---------------------------");
			System.out.println();
			System.out.println("Received Shutdown Request");
			login_Channel.shutdown();
			print_Channel.shutdown();
			cloud_Channel.shutdown();
			System.out.println("Successfully shutdown the channel!");
		}));
		
		
//		login("Patryck", "test");
//		print();
//		removeData(2);
		
		//Shutdown the channel
//		login_Channel.shutdown();
//		print_Channel.shutdown();
//		cloud_Channel.shutdown();
		
		
	}//End of Run function
	

	
	

	
	/* ------------------------- Client Implementation starts here ---------------------*/
	
	//Unary API
	//Passes as parameters an userName and password which will be validated against a database into the server
	public static LoginResponse login(String userName, String password) {
		LoginRequest request = LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
		
		//Login response will be equals to whatever come as response form the server
		LoginResponse response = LoginClient.login(request);

		
		System.out.println(response.getResponseMessage());
		return response;
		
	} //end of login function
	
	
	//Unary API, validates user name against a database into the server to attempt logout
	public static LogoutResponse logout(String username) {
		System.out.println("\nInside Logout in Client: ");

		LogoutRequest logoutRequest = LogoutRequest.newBuilder().setUsername(username).build();

		//Logout response will be equals to whatever come as response form the server
		LogoutResponse response = LoginClient.logout(logoutRequest);
			
		int responseCode = response.getResponseCode();

		System.out.println("Response from Server: " + response.getResponseMessage());
		return response;
		
		} //end of logout function
	
		
	//Client Streaming API, gives the number of input as parameter. Insert data into database in the server side
	public static void InsertData(int numbOfData) {
			
			//Blocks the channel and waits for the response from the server when client send "onComplete"  
			CountDownLatch latch = new CountDownLatch(1);
			
			StreamObserver<AddRequest> requestObserver = CloudClient.add( new StreamObserver <ResponseMessage>(){

				@Override
				public void onNext(ResponseMessage value) {
					// TODO Auto-generated method stub
					System.out.println(value.getResponse()); //prints response for each request
				}

				@Override
				public void onError(Throwable t) {
					t.printStackTrace(); //print error if occurs
					
				}

				@Override
				public void onCompleted() {
					// the server is done sending us data
					// onCompleted will be called right after onNext();
					System.out.println("Server has completed sending messages");
					
					//Client finished sending requests, so it count down waiting for the server's response
					latch.countDown();
				}

				
				
			}); //end of "requestObserver"
			
			AddRequest request;
			Scanner sc = new Scanner(System.in);
			String dateOfBirth = "";
			String firstName = "";
			String lastName = "";
			String gender = "";
			String hireDate = "";
			
			//This will set each field individually by prompting to the client
			for (int i = 0; i< numbOfData; i++) {
			System.out.println("Please enter date of birth of the Employee (yyyy-mm-dd): ");
			dateOfBirth = sc.nextLine();
			
			System.out.println("Please enter First Name of the Employee (Only letters): ");
			firstName = sc.nextLine();
			
			System.out.println("Please enter Last Name of the Employee (Only Letters): ");
			lastName = sc.nextLine();
			
			System.out.println("Please enter Gender of the Employee ('M' or 'F'): ");
			gender = sc.nextLine();
			
			System.out.println("Please enter Date of the Employee is being hired (yyyy-mm-dd): ");
			hireDate = sc.nextLine();
			
			//Set values to the the request
			request = AddRequest.newBuilder().setDateOfBirth(dateOfBirth)
					.setFirstName(firstName)
					.setLastName(lastName)
					.setGender(gender)
					.setHireDate(hireDate)
					.build();
			
			//Send each request one by one
			requestObserver.onNext(request);
			
			}// end of for loop
			
			//Tell the server that the client has completed sending requests
			requestObserver.onCompleted();
				
			
			
			//This is used to wait for the server's response. Otherwise the channel closes and there is no time to get response
			try {
				latch.await(3L, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} //end of "instertData" function
	
	
	
	// Server Streaming, the client makes one request and the server sends many responses.
	public static void print() {
		// Client request
		printRequest print = printRequest.newBuilder().setPrint(true).build();

		try {
			Iterator<printResponse> responces = PrintClient.print(print);

			// This loop will print each of the responses while the server is still sending
			// it
			while (responces.hasNext()) {
				printResponse temp = responces.next();
				System.out.println(temp.getPrinting());

			} // end of while loop

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}

	}// end of Print function
	
	
	// Bi Directional Streaming API.
	public static void removeData(int numberData) {

		// Blocks the channel and waits for the response from the server when client
		// send "onComplete"
		CountDownLatch latch = new CountDownLatch(1);

		StreamObserver<RemoveRequest> requestObserver = CloudClient.remove(new StreamObserver<ResponseMessage>() {

			@Override
			public void onNext(ResponseMessage value) {
				// TODO Auto-generated method stub
				System.out.println(value.getResponse()); //Print each response from the server
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace(); //Print error if occurs

			}

			@Override
			public void onCompleted() {
				// When the server is done sending data
				// onCompleted is called right after onNext();
				System.out.println("Server has completed sending messages");

				// Client finished sending requests, so it count down waiting for the server's
				// response
				latch.countDown();
			}

		}); // end of "requestObserver"

		RemoveRequest request;
		Scanner sc = new Scanner(System.in);
		int empNumber = 0;
		String firstName = "";

		// This will set each field individually by prompting to the client
		for (int i = 0; i < numberData; i++) {
			System.out.println("Please enter the Employee number: ");
			empNumber = Integer.parseInt(sc.nextLine());

			System.out.println("Please enter First Name of the Employee (Only letters): ");
			firstName = sc.nextLine();

			// Set values to the the request
			request = RemoveRequest.newBuilder().setEmpNo(empNumber).setFirstName(firstName).build();

			// Send each request one by one
			requestObserver.onNext(request);

		} // end of for loop

		// Tell the server that the client has completed sending requests
		requestObserver.onCompleted();

		// This is used to wait for the server's response. Otherwise the channel closes
		// and there is no time to get response
		try {
			latch.await(3L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

}
