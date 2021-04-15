package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;
import java.util.logging.Logger;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import services_Implementation.*;

public class ServerCMP implements Runnable{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Thread t = new Thread(new ServerCMP()); //just a test using Thread to run the server from the client
		 t.start();
		 
		 
	}
	
	private static final Logger logger = Logger.getLogger(ServerCMP.class.getName());

	@Override
	public void run() {
		//creates an object of the services classes
		LoginService loginService = new LoginService();
		CloudService cloudService = new CloudService();
		CapacityService capacityService = new CapacityService();
		
		
		registerService(loginService.getProperties());
		registerService(cloudService.getProperties());
		registerService(capacityService.getProperties());
	   
	    int port1 = 50051;
	    int port2 = 50050;
	    int port3 = 50052;
	    
	    //creates a Server with the port number above which will be accessed by the client
	    Server server1;
	    Server server2;
	    Server server3;
		try {
			server1 = ServerBuilder.forPort(port1)// add new services here like .addService(name of the service)
//				.addService(capacityService)
//				.addService(cloudService)
				.addService(loginService)
			    .build()
			    .start();
			
			server2 = ServerBuilder.forPort(port2)// add new services here like .addService(name of the service)
					.addService(capacityService)
				    .build()
				    .start();
			
			server3 = ServerBuilder.forPort(port3)// add new services here like .addService(name of the service)
					.addService(cloudService)
				    .build()
				    .start();
			
			logger.info("Server started, listening on " + port1 + ", " + port2 + " and " + port3);
			
			//If the server is stopped from the client, a message will be popped up
			Runtime.getRuntime().addShutdownHook(new Thread( () ->{
				System.out.println("---------------------------");
				System.out.println();
				System.out.println("Received Shutdown Request");
				server1.shutdown();
				server2.shutdown();
				server3.shutdown();
				System.out.println("Successfully stopped the server");
				
			}));
			
			//server keeps in stand by awaiting for requests
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
	
	
	private static void registerService(Properties prop) {

		try {
			// Create a JmDNS instance
			JmDNS jmdns = JmDNS.create(InetAddress.getLocalHost());

			String service_type = prop.getProperty("service_type");// "_http._tcp.local.";
			String service_name = prop.getProperty("service_name");// "example";
			// int service_port = 1234;
			int service_port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

			String service_description_properties = prop.getProperty("service_description");// "path=index.html";

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port,
					service_description_properties);
			jmdns.registerService(serviceInfo);

			System.out.printf("registrering service with type %s and name %s \n", service_type, service_name);

			// Wait a bit
			Thread.sleep(500);

			// Unregister all services
			//jmdns.unregisterAllServices();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
