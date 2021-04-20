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
		/* --- Start server straight from the client ---*/
//		ServerCMP server = new ServerCMP();
//		Thread t = new Thread(server);
//
//		try {
//		t.run();
//	
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(t.isAlive()) {
		

	    // Instance of the Client class
		ClientCMP client = new ClientCMP();
		
		
		

		System.out.println("Discovering services, please wait, it will take a moment...");
		client.run(); // makes a connection channel with each service

//		client.loginChannel();
//		client.capacityChannel();
//		client.cloudChannel();
		
		
//		login("Patryck", "test");

		
		InsertData(2);
		
		print();
		
//		removeData(1);
		
//		System.out.println("Shutting down the channel");
//		logout("Patryck");
		

		
//		t.stop(); //stops server so then if the client run again it wont give exceptions for server being running already
//		}
		

		
	}
	
	
	
	/*-------- Discovering the services ---------------*/
	
	// jmdns - service info for each channel
	private static ServiceInfo serviceInfo_LoginChannel = null;
	private static ServiceInfo serviceInfo_CapacityChannel = null;
	private static ServiceInfo serviceInfo_CloudChannel = null;
	
	public ServiceInfo discover(String str) {
		ServiceInfo serviceInfo = null;
		try {
    		DiscoverServices ds = new DiscoverServices();
            // Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." and then local host. It gave me an error before
			//and the solution was to use this method and collect only the address after the "/" and write "local/" before
    		String address = InetAddress.getLocalHost().toString().split("/")[1];
            JmDNS jmdns = JmDNS.create("local/" + address);
//            ServiceDiscover sd = new ServiceDiscover();
//            
//            jmdns.addServiceListener(str, sd);
            
//            serviceInfo = sd.getServiceInfo();
    		serviceInfo = ds.discoverService(str);
    		
            
            // Wait a bit
            Thread.sleep(5000);
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

	
	/*------------------------ Creating a variable Blocking Stub or Async Stub for each specific client channel ----------------*/
	private static LoginServiceGrpc.LoginServiceBlockingStub LoginClient;
	private static CloudServiceGrpc.CloudServiceStub CloudClient;
	private static printServiceGrpc.printServiceBlockingStub CapacityClient;
	
	
	/* --------------------------- Initializing a channel for each service --------------------------*/
	 //(make the connection faster since it only creates a channel for each service needed for the specific service)
    
    
   
	//This channel gives access to the login and logout services
	public void loginChannel() {
		
		// discover the service
	    String login_service_type = "_loginCMP._tcp.local.";
	    serviceInfo_LoginChannel = discover(login_service_type);
	    
	    /*----- Gets the local host and port number to be set on the channel -------*/
//	    String host = serviceInfo.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
        int port = serviceInfo_LoginChannel.getPort();
		
		System.out.println("Starting login channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(channel);
		
	}
	
	//This channel gives access to the print and change storage services
	public void capacityChannel() {

		// discover the service
		String capacity_service_type = "_capacity._tcp.local.";
		serviceInfo_CapacityChannel = discover(capacity_service_type);
		
		/*----- Gets the local host and port number to be set on the channel -------*/
//		String host = serviceInfo3.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
		int port = serviceInfo_CapacityChannel.getPort(); 

		System.out.println("Starting Capacity channel...");
	
	
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		CapacityClient = printServiceGrpc.newBlockingStub(channel);

	}
	
	public void cloudChannel() {

		// discover the service
		String cloud_service_type = "_cloud._tcp.local.";
		serviceInfo_CloudChannel = discover(cloud_service_type);
		
		/*----- Gets the local host and port number to be set on the channel -------*/
//		String host = serviceInfo2.getHostAddresses()[0]; //on MacBook it returns a hexagonal IP which does not work as local host. But it works on windows
		int port = serviceInfo_CloudChannel.getPort();

		System.out.println("Starting login channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		CloudClient = CloudServiceGrpc.newStub(channel);
				
	}
	
	
	public void run() {
		
//		String login_service_type = "_loginCMP._tcp.local.";
//	    serviceInfo_LoginChannel = discover(login_service_type);
//	    
//	    String capacity_service_type = "_capacity._tcp.local.";
//	    serviceInfo_CapacityChannel = discover(capacity_service_type);
//	    
//	    String cloud_service_type = "_cloud._tcp.local.";
//	    serviceInfo_CloudChannel = discover(cloud_service_type);
		
//	    String host_Login = ""+serviceInfo_LoginChannel.getHostAddresses()[0];
		//int port_Login = serviceInfo_LoginChannel.getPort();
//	    String host_Capacity = ""+serviceInfo_CapacityChannel.getHostAddresses()[0];
		//int port_Capacity = serviceInfo_CapacityChannel.getPort();
//	    String host_Cloud = ""+serviceInfo_CloudChannel.getHostAddresses()[0];
		//int port_Cloud = serviceInfo_CloudChannel.getPort();
	    
		System.out.println("Starting the Client Cloud Managment Platform...");
		
				
		ManagedChannel login_Channel = ManagedChannelBuilder.forAddress("localhost", 50051)//.forAddress("localhost", 50050).forAddress("localhost", 50052)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();
		
		ManagedChannel capacity_Channel = ManagedChannelBuilder.forAddress("localhost", 50050)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();
		
		ManagedChannel cloud_Channel = ManagedChannelBuilder.forAddress("localhost", 50052)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(login_Channel);
		CloudClient = CloudServiceGrpc.newStub(cloud_Channel);
		CapacityClient = printServiceGrpc.newBlockingStub(capacity_Channel);
		
//		login("Patryck", "test");
//		print();
//		removeData(2);
		
//		login_Channel.shutdown();
//		capacity_Channel.shutdown();
//		cloud_Channel.shutdown();
		
		
	}
	

	
	

	
	/* ------------------------- Client Implementation starts here ---------------------*/
	
	//Unary API
	//Passes as parameters an userName and password which will be validated against a database into the server
	public static void login(String userName, String password) {
		LoginRequest request = LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
		
		LoginResponse response = LoginClient.login(request);
		
		System.out.println(response.getResponseMessage());
		
	} //end of login function
	
	
	//Unary API, validates user name against a database into the server to attempt logout
	public static void logout(String username) {
		System.out.println("\nInside Logout in Client: ");

		LogoutRequest logoutRequest = LogoutRequest.newBuilder().setUsername(username).build();

		LogoutResponse response = LoginClient.logout(logoutRequest);
			
		int responseCode = response.getResponseCode();

		System.out.println("Response from Server: " + response.getResponseMessage());
		
		} //end of logout function
	
	
	//Client Streaming API, gives the number of input as parameter. Insert data into database in the server side
	public static void InsertData(int numbOfData) {
		
		//Blocks the channel and waits for the response from the server when client send "onComplete"  
		CountDownLatch latch = new CountDownLatch(1);
		
		StreamObserver<AddRequest> requestObserver = CloudClient.add( new StreamObserver <ResponseMessage>(){

			@Override
			public void onNext(ResponseMessage value) {
				// TODO Auto-generated method stub
				System.out.println(value.getResponse());
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
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
	
	//Server Streaming, the client makes one request and the server sends many responses.
	public static void print() {
		// Client request
		printRequest print = printRequest.newBuilder().setPrint(true).build();
		

		try {
			Iterator<printResponse> responces = CapacityClient.print(print);
			
			//This loop will print each of the responses while the server is still sending it 
			while(responces.hasNext()) {
				printResponse temp = responces.next();
				System.out.println(temp.getPrinting());	
				
			} //end of while loop

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
		
		
	}// end of Print function
	
	//Bi Directional Streaming API.
	public static void removeData(int numberData) {
		
		//Blocks the channel and waits for the response from the server when client send "onComplete"  
				CountDownLatch latch = new CountDownLatch(1);
				
				StreamObserver<RemoveRequest> requestObserver = CloudClient.remove( new StreamObserver <ResponseMessage>(){

					@Override
					public void onNext(ResponseMessage value) {
						// TODO Auto-generated method stub
						System.out.println(value.getResponse());
					}

					@Override
					public void onError(Throwable t) {
						t.printStackTrace();
						
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
				
				RemoveRequest request;
				Scanner sc = new Scanner(System.in);
				String empNumber = "";
				String firstName = "";

				
				//This will set each field individually by prompting to the client
				for (int i = 0; i< numberData; i++) {
				System.out.println("Please enter the Employee number: ");
				empNumber = sc.nextLine();
				
				System.out.println("Please enter First Name of the Employee (Only letters): ");
				firstName = sc.nextLine();
				

				
				//Set values to the the request
				request = RemoveRequest.newBuilder().setEmpNo(empNumber)
						.setFirstName(firstName)
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
		
	}
	

}
