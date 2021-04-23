package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import services_Implementation.printService;
import services_Implementation.CloudService;
import services_Implementation.LoginService;

public class registerServices {

	public void registerService(Properties prop) {

		try {
			// Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." + local host. It gave me an error before
			//and the solution was to use this method and collect only the address after the "/" and write "local/" before.
			//This way it runs in any OS
    		String address = InetAddress.getLocalHost().toString().split("/")[1]; 
            JmDNS jmdns = JmDNS.create("local/" + address);

            // This will access the properties file for the specific service in the server, and collect its properties
			String service_type = prop.getProperty("service_type");// "_http._tcp.local.";
			String service_name = prop.getProperty("service_name");// "example";
			
			int service_port = Integer.valueOf(prop.getProperty("service_port"));// #.50051;

			String service_description_properties = prop.getProperty("service_description");// "path=index.html";

			// Register a service
			ServiceInfo serviceInfo = ServiceInfo.create(service_type, service_name, service_port,
					service_description_properties);
			jmdns.registerService(serviceInfo);

			System.out.printf("Registering service with type %s and name %s \n", service_type, service_name);

			// Wait a bit
			Thread.sleep(1000);

			// Unregister all services
			//jmdns.unregisterAllServices();

		} catch (IOException e) {
			System.out.println(e.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static void main (String [] args) {
		
		//Creates an instance of the Register services class
		registerServices register = new registerServices();
		
		//Creates an instance of each service
		LoginService loginService = new LoginService();
		CloudService cloudService = new CloudService();
		printService printService = new printService();
		
		//Call the function "getProperties" inside each of the services
		//Register each service individually
		register.registerService(loginService.getProperties());

		register.registerService(cloudService.getProperties());
		
		register.registerService(printService.getProperties());

		
	}


}
