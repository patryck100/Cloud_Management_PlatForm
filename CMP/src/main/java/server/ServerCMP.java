package server;


import java.io.IOException;
import java.util.logging.Logger;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import services_Implementation.*;

public class ServerCMP implements Runnable{

	public static void main(String[] args) {
		// Thread of the server
		Thread server = new Thread(new ServerCMP()); 
		
		//starts whatever has in the "run()" function, in this case, the server
		server.start();
		 
		 
	}
	
	private static final Logger logger = Logger.getLogger(ServerCMP.class.getName());

	@Override
	public void run() {
		//creates an object of each service class
		LoginService loginService = new LoginService();
		CloudService cloudService = new CloudService();
		CapacityService capacityService = new CapacityService();
		
		//Instance of the register services class, so then the services are registered automatically before the server starts
		registerServices register = new registerServices();
		
		//register all services
		System.out.println("Registering services, please wait until server get started...\n");
		register.main(null);
		
		
	    int port1 = 50051;
	    int port2 = 50050;
	    int port3 = 50052;
	    
	    //creates a Server with the port number above which will be accessed by the client
	    Server server1;
	    Server server2;
	    Server server3;
	    
	    //A server can have many services, but here we are making a server for each service in order to have many port numbers
		try {
			server1 = ServerBuilder.forPort(port1)// add new services here like .addService(name of the service)
//				.addService(capacityService)
//				.addService(cloudService)
				.addService(loginService)
			    .build()
			    .start();
			
			server2 = ServerBuilder.forPort(port2)
					.addService(capacityService)
				    .build()
				    .start();
			
			server3 = ServerBuilder.forPort(port3)
					.addService(cloudService)
				    .build()
				    .start();
			
			logger.info("Server started, listening on ports: " + port1 + ", " + port2 + " and " + port3);
			
			//Prints a message when the server receives a shutdown request
			Runtime.getRuntime().addShutdownHook(new Thread( () ->{
				System.out.println("---------------------------");
				System.out.println();
				System.out.println("Received Shutdown Request");
				server1.shutdown();
				server2.shutdown();
				server3.shutdown();
				System.out.println("Successfully stopped the server");
				
			}));
			

			
			//server keeps in stand by, awaiting for requests
		    server1.awaitTermination(); 
		    server2.awaitTermination();
		    server3.awaitTermination();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
				
	}
	

}
