package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;

public class DiscoverServices {

    private ServiceInfo serviceInfo;

	
	
	// jmDNS discovery service (non-static method)
    public ServiceInfo discoverService(String service_type) {
        try {
            // Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." and then local host. It gave me an error before
			//and the solution was to use this method and collect only the address after the "/" and write "local/" before
    		String address = InetAddress.getLocalHost().toString().split("/")[1];
            JmDNS jmdns = JmDNS.create("local/" + address);
            jmdns.addServiceListener(service_type, new ServiceListener() {
                @Override
                public void serviceResolved(ServiceEvent event) {
                    System.out.println("Service resolved: " + event.getInfo());
                    serviceInfo = event.getInfo();
                    int port = serviceInfo.getPort();
                    System.out.println("resolving " + service_type + " with properties ...");
                    System.out.println("\t port: " + port);
                    System.out.println("\t type:" + event.getType());
                    System.out.println("\t name: " + event.getName());
                    System.out.println("\t description/properties: " + serviceInfo.getNiceTextString());
                    System.out.println("\t host: " + serviceInfo.getHostAddresses()[0]);
                }
                @Override
                public void serviceRemoved(ServiceEvent event) {
                    System.out.println("Service removed: " + event.getInfo().toString());
                }
                @Override
                public void serviceAdded(ServiceEvent event) {
                    System.out.println("Service added: " + event.getInfo().getPort());
                }
            });
            // Wait a bit
            Thread.sleep(1000);
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
	
    public static void main (String [] args) {
    	try {
    		DiscoverServices ds = new DiscoverServices();
            // Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." and then local host. It gave me an error before
			//and the solution was to use this method and collect only the address before the "/" and write "local/" before
    		String address = InetAddress.getLocalHost().toString().split("/")[1];
            JmDNS jmdns = JmDNS.create("local/" + address);
            
            // Add a service listener (test)
//    	    String login_service_type = "_loginCMP._tcp.local.";
//    	    ds.discoverService(login_service_type);
    	    
//    	    String cloud_service_type = "_cloud._tcp.local.";
//    		ds.discoverService(cloud_service_type);
    		
    		String capacity_service_type = "_capacity._tcp.local.";
    		ds.discoverService(capacity_service_type);
    		

            // Wait a bit
            Thread.sleep(30000);
        } catch (UnknownHostException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
	
}
