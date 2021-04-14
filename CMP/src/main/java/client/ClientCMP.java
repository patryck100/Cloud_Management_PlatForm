package client;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
		ServerCMP server = new ServerCMP();
		Thread t = new Thread(server);
		try {
		t.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(t.isAlive()) {
		
		
		System.out.println("Starting the Client Cloud Managment Platform...");
		
		
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();

		LoginClient = LoginServiceGrpc.newBlockingStub(channel);
		CloudClient = CloudServiceGrpc.newStub(channel);
		CapacityClient = capacityServiceGrpc.newBlockingStub(channel);
		
//		login("Patryck", "test");
//		
//		System.out.println("Shutting down the channel");
//		logout("Patryck");
		
//		InsertData(2);
		
		print();
		
		channel.shutdown();
		
		t.stop(); //stops server so then if the client run again it wont give exceptions for server being running already
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
