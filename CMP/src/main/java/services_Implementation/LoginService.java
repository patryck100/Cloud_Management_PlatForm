package services_Implementation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import io.grpc.stub.StreamObserver;
import loginCMP.*;
import loginCMP.LoginServiceGrpc.LoginServiceImplBase;


public class LoginService extends LoginServiceImplBase{
	
	//This is just to represent a database in the server side
	// It writes a user name and password into a file "in the cloud", which will later be used to validate login
	private static void validateUser() {
		user validate = user.newBuilder().setUserName("Patryck").setPassword("test").build(); //USER NAME AND PASSWORD
				
		//writing the username and password in a txt file. This will be overwritten every time the function is called, so we can 
		// add more user accounts or alter it in the file through the build method above.
		try {
			//writing in a txt file but it could be written in binary to make it more secure and faster for example
			System.out.println("Writing user account which will be used to validate into txt file. Can be checked into the project folder"); 
			FileOutputStream outputStream = new FileOutputStream("userAccount.txt"); //has to surround it with catch to handle any errors
			validate.writeTo(outputStream); //can only write outputStream
			//has to surround it with catch to handle any errors
			outputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}// End of "validateUser"
	
	
	//Unary service API. Validates user name and password and later returns permission to access other files
	@Override
	public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
		validateUser(); //write username and password which will be used to validate into a txt file

		//reads what comes from the protocol buffer txt file "userAccount.txt" that was written
		try {
			System.out.println("Reading from file userAccount.txt...");
			FileInputStream inputStream = new FileInputStream("userAccount.txt"); //has to surround it with catch to handle any errors
			user messageFromFile = user.parseFrom(inputStream);  //can only parseFrom inputStream 	//has also to surround it with catch to handle any errors
			System.out.println(messageFromFile); //this will print the user name and password. Just to test if it is working
		
		/* Creates variable to store input from user and later validate it*/
		String UserName = request.getUsername();
		String Password = request.getPassword();
		
		/* starts variable "response", and depending on the result it returns a message positive or negative */
		LoginResponse.Builder response = LoginResponse.newBuilder();
		System.out.println("UserName given by user = " + UserName + "\npassword given by the user = " + Password);
		
		
		
		//gives access to next GUI page when response code is set to 1 (login successful)
		//read user and password from a file in the server
		if(UserName.equals(messageFromFile.getUserName()) && Password.equals(messageFromFile.getPassword())) { 
			//if "UserName" is equals to "Patryck" and "password" equals to "test"
			//return success response
			response.setResponseCode(1).setResponseMessage("Hi " + UserName + ".... the Login was Successfull");
			
		} else {
			// return fail response
			response.setResponseCode(0).setResponseMessage("Sorry, UserName or Password is wrong!");
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
		
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}// End of "login"

	
	//Unary service API. Validates user name and returns code to 0 if logout is successful, and 1 when attempt fails and client keeps logged in
	@Override
	public void logout(LogoutRequest request, StreamObserver<LogoutResponse> responseObserver) {
		//reads what comes from the protocol buffer txt file "userAccount.txt" that was written before
		try {
			System.out.println("Reading from file userAccount.txt...");
			FileInputStream inputStream = new FileInputStream("userAccount.txt"); //has to surround it with catch to handle any errors
			user messageFromFile = user.parseFrom(inputStream);  //can only parseFrom inputStream 	//has to surround it with catch to handle any errors
			System.out.println(messageFromFile); //this will print the user name and password. Just to test if it is working
		
		
		String UserName = request.getUsername();
		
		LogoutResponse.Builder response = LogoutResponse.newBuilder();
		System.out.println("Loging out username = " + UserName);
		
		//In the GUI when the response code is set to "0" it closes the interface and return to the Login page
		if (UserName.equals(messageFromFile.getUserName())) { //if "UserName" is equals to "Patryck"
			//return success response
			response.setResponseCode(0).setResponseMessage("See you later " + UserName);
		} else {
			//return fail response
			response.setResponseCode(1).setResponseMessage("Sorry bro, wrong UserName! You are still Logged in");
		}
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}// end of "logout"
	
	public Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/loginCMP.properties")) {

			prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			System.out.println("CMP Service properies ...");
			System.out.println("\t service_type: " + prop.getProperty("service_type"));
			System.out.println("\t service_name: " + prop.getProperty("service_name"));
			System.out.println("\t service_description: " + prop.getProperty("service_description"));
			System.out.println("\t service_port: " + prop.getProperty("service_port"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return prop;
	}// end of "properties"

	
	
	
}
