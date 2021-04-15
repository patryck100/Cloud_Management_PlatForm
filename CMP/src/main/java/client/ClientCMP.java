package client;


import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.jmdns.*;

import CloudManagment.*;
import capacity_print.capacityServiceGrpc;
import capacity_print.printRequest;
import capacity_print.printResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import loginCMP.*;
import server.ServerCMP;

public class ClientCMP {
	


	public static void main(String[] args) {
//		ServerCMP server = new ServerCMP();
//		Thread t = new Thread(server);
//		
//		
//		try {
//		t.start();
//		
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		if(t.isAlive()) {
		
		ClientCMP client = new ClientCMP();
		
		// discover the service
	    String login_service_type = "_loginCMP._tcp.local.";
	    discoverService(login_service_type);
	    
//	    String host = client.serviceInfo.getHostAddresses()[0];
//		port = serviceInfo.getPort();
	    
	    String cloud_service_type = "_cloud._tcp.local.";
		client.discoverService(cloud_service_type);
		
		String capacity_service_type = "_capacity._tcp.local.";
		client.discoverService(capacity_service_type);
		
		
		System.out.println("Starting the Client Cloud Managment Platform...");
		
		
		ManagedChannel channel1 = ManagedChannelBuilder.forAddress("localhost", 50051)//.forAddress("localhost", 50050).forAddress("localhost", 50052)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();
		
		ManagedChannel channel2 = ManagedChannelBuilder.forAddress("localhost", 50050)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();
		
		ManagedChannel channel3 = ManagedChannelBuilder.forAddress("localhost", 50052)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(channel1);
		CloudClient = CloudServiceGrpc.newStub(channel3);
		CapacityClient = capacityServiceGrpc.newBlockingStub(channel2);
		
		login("Patryck", "test");
//		
//		System.out.println("Shutting down the channel");
//		logout("Patryck");
		
//		InsertData(2);
		
//		client.loginChannel();
//		client.capacityChannel();
//		client.cloudChannel();
		
//		print();
		
		channel1.shutdown();
		channel2.shutdown();
		channel3.shutdown();
		
//		t.stop(); //stops server so then if the client run again it wont give exceptions for server being running already
//		}
		
//		ClientCMP client = new ClientCMP();
//		
//		client.run();
//		
		
	}
	
	
	
	/* --------------- Initializing a channel for each service ----------------*/
	
	
	// jmdns - service info
    private static ServiceInfo serviceInfo;
    private static int port = 0;
	
	
	
	public void loginChannel() {
		
		// discover the service
	    String login_service_type = "_loginCMP._tcp.local.";
	    discoverService(login_service_type);
	    
	    String host = serviceInfo.getHostAddresses()[0];
        int port = serviceInfo.getPort();
		
		System.out.println("Starting login channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(channel);
		
	}
	
	public void cloudChannel() {

		// discover the service
		String cloud_service_type = "_cloud._tcp.local.";
		discoverService(cloud_service_type);

		String host = serviceInfo.getHostAddresses()[0];
		int port = serviceInfo.getPort();

		System.out.println("Starting login channel...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		CloudClient = CloudServiceGrpc.newStub(channel);
				
	}
	

	public void capacityChannel() {

		// discover the service
		String capacity_service_type = "_capacity._tcp.local.";
		discoverService(capacity_service_type);

		String host = serviceInfo.getHostAddresses()[0];
		int port = serviceInfo.getPort();

		System.out.println("Starting Capacity channel...");
	
	
		ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port)
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		CapacityClient = capacityServiceGrpc.newBlockingStub(channel);

	}
	
	
	

	
	// jmDNS discovery service (non-static method)
    private static void discoverService(String service_type) {
        try {
            // Create a JmDNS instance
            JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());
            jmdns.addServiceListener(service_type, new ServiceListener() {
                @Override
                public void serviceResolved(ServiceEvent event) {
                    System.out.println("Service resolved: " + event.getInfo());
                    serviceInfo = event.getInfo();
                    port = serviceInfo.getPort();
                    System.out.println("resolving " + service_type + " with properties ...");
                    System.out.println("\t port: " + port);
                    System.out.println("\t type:" + event.getType());
                    System.out.println("\t name: " + event.getName());
                    System.out.println("\t description/properties: " + serviceInfo.getNiceTextString());
                    System.out.println("\t host: " + serviceInfo.getHostAddresses()[0]);
                }
                @Override
                public void serviceRemoved(ServiceEvent event) {
                    System.out.println("Service removed: " + event.getInfo());
                }
                @Override
                public void serviceAdded(ServiceEvent event) {
                    System.out.println("Service added: " + event.getInfo());
                }
            });
            // Wait a bit
            Thread.sleep(2000);
            jmdns.close();
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
		
	
	
	
	
	
	/*------------------------ Blocking Stub and Async Stub are initialized here ----------------*/
	private static LoginServiceGrpc.LoginServiceBlockingStub LoginClient;
	private static CloudServiceGrpc.CloudServiceStub CloudClient;
	private static capacityServiceGrpc.capacityServiceBlockingStub CapacityClient;
	
	
	
	/* ------------------------- Client Implementation ---------------------*/
	
	//Unary API
	//Passes as parameters an userName and password which will be validated against a database into the server
	public static void login(String userName, String password) {
		LoginRequest request = LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
		
		LoginResponse response = LoginClient.login(request);
		
		System.out.println(response.getResponseMessage());
		
	}
	
	
	//Unary API, validates user name against a database into the server to attempt logout
	public static void logout(String username) {
		System.out.println("\nInside Logout in Client: ");

		LogoutRequest logoutRequest = LogoutRequest.newBuilder().setUsername(username).build();

		LogoutResponse response = LoginClient.logout(logoutRequest);
			
		int responseCode = response.getResponseCode();

		System.out.println("Response from Server: " + response.getResponseMessage());
		
		}
	
	
	public static void InsertData(int numbOfData) {
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
				
				latch.countDown();
			}

			
			
		});
		
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
		
		request = AddRequest.newBuilder().setDateOfBirth(dateOfBirth)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setGender(gender)
				.setHireDate(hireDate)
				.build();
		
		//Send each request one by one
		requestObserver.onNext(request);
		
		}
				
		requestObserver.onCompleted();
		
			
		
		
		//This is used to wait for the server's response. Otherwise the channel closes and there is no time to get response
		try {
			latch.await(3L, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public static void print() {
		printRequest print = printRequest.newBuilder().setPrint(true).build();
		
		try {
			Iterator<printResponse> responces = CapacityClient.print(print);
			
			while(responces.hasNext()) {
				printResponse temp = responces.next();
				System.out.println(temp.getPrinting());				
			}

		} catch (StatusRuntimeException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	

}
