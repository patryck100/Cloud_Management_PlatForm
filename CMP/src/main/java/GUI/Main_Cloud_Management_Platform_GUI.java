package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import CloudManagment.CloudServiceGrpc;
import client.ClientCMP;
import client.DiscoverServices;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import loginCMP.LoginRequest;
import loginCMP.LoginResponse;
import loginCMP.LoginServiceGrpc;
import print.printServiceGrpc;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Main_Cloud_Management_Platform_GUI {

	/*-------- Global variable to be accessed from the class --------*/
	JFrame frame;
	private JTextField LoginText;
	private JPasswordField passwordField;
	private ClientCMP client = new ClientCMP();
	
	
	/*------------------------ Creating a variable Blocking Stub or Async Stub for each specific client channel ----------------*/
	public LoginServiceGrpc.LoginServiceBlockingStub LoginClient;
	public CloudServiceGrpc.CloudServiceStub CloudClient;
	public printServiceGrpc.printServiceBlockingStub PrintClient;
	
	
	/*---------------Channels------------ */
	private ManagedChannel login_Channel;
	private ManagedChannel print_Channel;
	private ManagedChannel cloud_Channel;
	
	
	/*-------- Service information for each channel ---------*/
	
	// jmdns - service info for each channel
	private static ServiceInfo serviceInfo_LoginChannel = null;
	private static ServiceInfo serviceInfo_PrintChannel = null;
	private static ServiceInfo serviceInfo_CloudChannel = null;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Cloud_Management_Platform_GUI window = new Main_Cloud_Management_Platform_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	} 

	/**
	 * Create the application.
	 */
	public Main_Cloud_Management_Platform_GUI() {
		
			
		
//		String login_service_type = "_loginCMP._tcp.local.";
//	    serviceInfo_LoginChannel = discover(login_service_type);
//	    
//	    String print_service_type = "_print._tcp.local.";
//	    serviceInfo_PrintChannel = discover(print_service_type);
//	    
//	    String cloud_service_type = "_cloud._tcp.local.";
//	    serviceInfo_CloudChannel = discover(cloud_service_type);
		
//	    String host_Login = ""+serviceInfo_LoginChannel.getHostAddresses()[0];
		//int port_Login = serviceInfo_LoginChannel.getPort();
//	    String host_Print = ""+serviceInfo_PrintChannel.getHostAddresses()[0];
		//int port_Printrint = serviceInfo_PrintChannel.getPort();
//	    String host_Cloud = ""+serviceInfo_CloudChannel.getHostAddresses()[0];
		//int port_Cloud = serviceInfo_CloudChannel.getPort();
		
		
		
		System.out.println("Starting the Client Cloud Managment Platform...");
		
		/*------- Starts a channel each service -------*/
		login_Channel = ManagedChannelBuilder.forAddress("localhost", 50051)//  host: "localHost", port: 50051
					.usePlaintext() //force SSL to be deactivated during our development. (security measures)
					.build();
		
		print_Channel = ManagedChannelBuilder.forAddress("localhost", 50050)//  host: "localHost", port: 50050
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();
		
		cloud_Channel = ManagedChannelBuilder.forAddress("localhost", 50052)//  host: "localHost", port: 50052
				.usePlaintext() //force SSL to be deactivated during our development. (security measures)
				.build();

		/*------ Connect each stub to its channel -------*/
		LoginClient = LoginServiceGrpc.newBlockingStub(login_Channel);
		CloudClient = CloudServiceGrpc.newStub(cloud_Channel);
		PrintClient = printServiceGrpc.newBlockingStub(print_Channel);
			
		// Prints a message when the server receives a shutdown request
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			System.out.println("---------------------------");
			System.out.println();
			System.out.println("Received Shutdown Request");
			login_Channel.shutdown();
			print_Channel.shutdown();
			cloud_Channel.shutdown();
			System.out.println("Successfully shutdown the channel!");
		}));
		
		//Starts the GUI
		initialize();
	}
	
	
	/*------------ Get method allows second GUI to connect with each
	 * stub without having to start a new channel all over again-----------*/

	public LoginServiceGrpc.LoginServiceBlockingStub getLoginClient() {
		return LoginClient;
	}

	public CloudServiceGrpc.CloudServiceStub getCloudClient() {
		return CloudClient;
	}

	public printServiceGrpc.printServiceBlockingStub getPrintClient() {
		return PrintClient;
	}


	//Discover service information from the server
	public ServiceInfo discover(String str) {
		ServiceInfo serviceInfo = null;
		try {
    		DiscoverServices ds = new DiscoverServices();
            // Create a JmDNS instance
			//When a macbook call the "InetAddress" it returns "MacBook-model." and then local host. It gave me an error before
			//and the solution was to use this method and collect only the address after the "/" and write "local/" before
    		String address = InetAddress.getLocalHost().toString().split("/")[1];
            JmDNS jmdns = JmDNS.create("local/" + address);

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
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBackground(new Color(0, 0, 51));
		frame.getContentPane().setBackground(new Color(25, 25, 112));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//----------------------------- Login button ----------------------------
		JButton LoginButton = new JButton("Login");
		LoginButton.setBackground(Color.LIGHT_GRAY);
		LoginButton.setForeground(new Color(144, 238, 144));
		LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //when hover the "Login" button, it changes the cursor to a hand cursor
		
		//When clicked on the "Login" button...
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//validates login and password request (not empty)
				if (LoginText.getText().equals("") || String.valueOf(passwordField.getPassword()).equals("")) {
					//Inform the user it can not be empty
					JOptionPane.showMessageDialog(LoginButton,
							"Please enter Username and Password before attempt login!");
					
				} else { //if everything is okay, attempt the login...
					//Build a login request
					LoginRequest request = LoginRequest.newBuilder().setUsername(LoginText.getText())
							.setPassword(String.valueOf(passwordField.getPassword())).build();
					
					//Get response from stub and print to the user
					LoginResponse response = LoginClient.login(request);
					JOptionPane.showMessageDialog(LoginButton, response.getResponseMessage());

					//if response is positive, go to next GUI and dispose this one (will be still running but invisible)
					//so then the channel does not loose connection
					if (response.getResponseCode() == 1) {
						Cloud_Management_Platform_GUI nextGUI = new Cloud_Management_Platform_GUI();
						nextGUI.setVisible(true);
						nextGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.dispose();

					}
				}
			}
		});
		
		
		LoginButton.setBounds(123, 161, 97, 29);
		frame.getContentPane().add(LoginButton);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(85, 84, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		LoginText = new JTextField();
		LoginText.setBackground(new Color(248, 248, 255));
		LoginText.setBounds(172, 79, 130, 26);
		frame.getContentPane().add(LoginText);
		LoginText.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(85, 116, 75, 16);
		frame.getContentPane().add(lblPassword);
		
		
		
		// -------------------------- Clear Button --------------------------
		JButton ClearButton = new JButton("Clear");
		//When clicked on "Clear" button, it set Login and password fields to null, in other words it clear the fields
		ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				LoginText.setText(null);
				passwordField.setText(null);
			}
		});
		
		
		ClearButton.setBackground(Color.LIGHT_GRAY);
		ClearButton.setForeground(new Color(244, 164, 96));
		ClearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ClearButton.setBounds(221, 161, 81, 29);
		frame.getContentPane().add(ClearButton);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(248, 248, 255));
		passwordField.setBounds(172, 111, 130, 26);
		frame.getContentPane().add(passwordField);
		
		
	}
		
}
