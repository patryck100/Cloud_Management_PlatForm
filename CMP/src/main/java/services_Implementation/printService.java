package services_Implementation;


import print.printServiceGrpc.printServiceImplBase;
import io.grpc.stub.StreamObserver;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import print.*;

public class printService extends printServiceImplBase {

	
	//Server Streaming API. Prints all record from the cloud + size of database. 
	@Override
	public void print(printRequest request, StreamObserver<printResponse> responseObserver) {
		
		boolean print = request.getPrint(); //set to true when the client request "print"
		Scanner sc;
		int counter = 0; // variable counter will be used to track the lines from the csv file and check how many data we have
		String st;
	
	try {
		sc = new Scanner(new FileReader("employees_data.csv")); //path to the Employees record generated

		
		//when a print is requested, "print" is set to true and a loop which goes through the entire list in the cloud
		//returns each record individually with a pause of a second
		if(print) {
						
			 if(sc.hasNextLine()) { //if file has any data (represented by rows/lines)
						
				//Starting with the header
				responseObserver.onNext(printResponse.newBuilder()
						.setPrinting("Printing data from your cloud... \n"
								+ "EmpNo, Date Of Birth, First Name, Last Name, Gender, Hire Date")
						.build());
				
				
				
				// goes through the entire list in the cloud
				while (sc.hasNextLine())  //if it has one or more line in the csv file, it keeps running
				{
					st = sc.nextLine(); //set value of "st" to next line
					String[] data = st.split(","); //creates a String array separating the columns from the CSV file
					//this will generate a response for each data in the cloud
					printResponse response = printResponse.newBuilder()
							.setPrinting("["+data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5]+"]")
							.build(); 
					responseObserver.onNext(response); //send response to the client
					
					counter++; //keeps track of how many data is in the database ( represented by the CSV Employees file)
					
					Thread.sleep(1000L); //sleeps before each response
				}
				sc.close();  //closes the scanner
				
			 } else { //If file has no data, inform the Client that the cloud is empty, and completes print request
				 responseObserver.onNext(printResponse.newBuilder()
							.setPrinting("Your cloud is empty!").build());
				responseObserver.onCompleted(); //close response when it finishes
			 }
				
				
				
			}
		}catch (InterruptedException | FileNotFoundException e) {
				e.printStackTrace();
			} 
			//At the end when it finishes sending responses, it sends a final message with the amount of Employee
			//records and close the streaming with "onComplete".
			responseObserver.onNext(printResponse.newBuilder()
					.setPrinting("Your cloud has " + counter + " records.\nEnd of Streaming...").build());
			responseObserver.onCompleted(); //close response when it finishes
			
		}
		

	//Returns the properties for the Print service, stored in the same folder as the proto files.
	//This function is used by the JMDNS to discover the properties from the service
	public Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/print.properties")) {

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
	}
	

}
