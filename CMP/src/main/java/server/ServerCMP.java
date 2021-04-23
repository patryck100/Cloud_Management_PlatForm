package server;


import java.io.IOException;
import java.util.logging.Logger;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import services_Implementation.*;

public class ServerCMP implements Runnable{
	
	//creates an object of each service class
	private static LoginService loginService = new LoginService();
	private static CloudService cloudService = new CloudService();
	private static printService printService = new printService();

	public static void main(String[] args) {
		
		
		// Thread of the Server CMP class
		Thread server = new Thread(new ServerCMP());
		
		//Instance of the register services class, so then the services are registered automatically before the server starts
		registerServices register = new registerServices();
		
		
		//register all services
		System.out.println("Registering services, please wait until server get started...\n");
		
		/*Decided to use thread to register each service because it is around 3 times faster than registering all together in a same function*/
		Thread t1 = new Thread()
		{
		    public void run() {
		    	register.registerService(loginService.getProperties());
		    }
		};
		
		
		
		Thread t2 = new Thread()
		{
		    public void run() {
		    	register.registerService(cloudService.getProperties());
		    }
		};
		
		
		Thread t3 = new Thread()
		{
		    public void run() {
		    	register.registerService(printService.getProperties());
		    }
		}; 
				
		t1.start();
		t2.start();
		t3.start();
		
		try { //wait until all threads are done
			
			t1.join();
			t2.join();
			t3.join();
		} catch(Exception e) {
			
		}
		
		//starts whatever has in the "run()" function, in this case, the server
		server.start();
		 
		 
	}
	
	private static final Logger logger = Logger.getLogger(ServerCMP.class.getName());

	@Override
	public void run() {
		
		
		//Instance of the register services class, so then the services are registered automatically before the server starts
		//registerServices register = new registerServices();
		
//		//register all services
//		System.out.println("Registering services, please wait until server get started...\n");
		
		//It takes arond 25.75seconds to register the 3 services and run the server. Using thread is more efficient
		//register.main(null);
			
		
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
					.addService(printService)
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
