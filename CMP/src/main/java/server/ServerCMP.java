package server;

import java.io.IOException;
import java.util.logging.Logger;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;

public class ServerCMP implements Runnable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new ServerCMP()); //just a test using Thread to run the server from the client
		 t.start();
		
	}
	
	private static final Logger logger = Logger.getLogger(ServerCMP.class.getName());

	@Override
	public void run() {
		//creates an object of the class
		ServerCMP CMP = new ServerCMP();
	   
	    int port = 50051;
	    //creates a channel with the port number above which will be accessed by the client
	    Server server;
		try {
			server = ServerBuilder.forPort(port)// add new services here like .addService(name of the service)
			    .addService(ProtoReflectionService.newInstance()) //test for reflection
			    .build()
			    .start();
			
			logger.info("Server started, listening on " + port);
			
			//If the server is stopped from the client, a message will be popped up
			Runtime.getRuntime().addShutdownHook(new Thread( () ->{
				System.out.println("---------------------------");
				System.out.println();
				System.out.println("Received Shutdown Request");
				server.shutdown();
				System.out.println("Successfully stopped the server");
				
			}));
			
		    
		    server.awaitTermination(); //server keeps in stand by awaiting for requests
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
		
	}

}
