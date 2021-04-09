package client;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
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
		
		login("Patryck", "test");
		
		System.out.println("Shutting down the channel");
		channel.shutdown();
		
		t.stop(); //stops server so then if the client run again it wont give exceptions for server being running already
		}
		
		
	}
	
	/*------------------------ Blocking Stub and Async Stub are initialized here ----------------*/
	private static LoginServiceGrpc.LoginServiceBlockingStub LoginClient;
	
	
	
	/* ------------------------- Client Implementation ---------------------*/
	
	//Unary API
	//Passes as parameters an userName and password which will be validated against a database into the server
	public static void login(String userName, String password) {
		LoginRequest request = LoginRequest.newBuilder().setUsername(userName).setPassword(password).build();
		
		LoginResponse response = LoginClient.login(request);
		
		System.out.println(response.getResponseMessage());
		
	}
	
	
	

}
