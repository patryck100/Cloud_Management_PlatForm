package services_Implementation;


import print.printServiceGrpc.printServiceImplBase;
import io.grpc.stub.StreamObserver;
import services_Implementation.CloudService;
import services_Implementation.CloudService.Employee;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import print.*;

public class printService extends printServiceImplBase {

/* ----------------------- Still needs to create the record with add, remove or alter data before being able to print it -------------*/	
	//Server Streaming API. Prints all record into cloud + storage. 
	@Override
	public void print(printRequest request, StreamObserver<printResponse> responseObserver) {
		
		boolean print = request.getPrint();
		Scanner sc;
		int counter = 0; // variable i will be used to track the lines from the csv file and check how many data we have
		String st;
	
	try {
		sc = new Scanner(new FileReader("employees_data.csv"));

		
		//when a print is requested, "print" is set to true and a loop which goes through the entire list in the cloud
		//returns each record individually with a pause of a second
		if(print) {
						
			 if(sc.hasNextLine()) { //if file is not empty
						
				//Starting with the header
				responseObserver.onNext(printResponse.newBuilder()
						.setPrinting("Printing data from your cloud... \n"
								+ "EmpNo, Date Of Birth, First Name, Last Name, Gender, Hire Date")
						.build());
				
				
				
				// goes through the entire list in the cloud
				while (sc.hasNextLine())  //if it has one more line in the csv file, it keeps running
				{
					st = sc.nextLine(); //set value of "st" to next line
					String[] data = st.split(",");
					//this will generate a response for each data in the cloud
					printResponse response = printResponse.newBuilder()
							.setPrinting("["+data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5]+"]")
							.build(); 
					responseObserver.onNext(response); //send response to client
					
					counter++; //keeps track of how many data is in the csv file
					
					Thread.sleep(1000L); //sleeps before each response
				}
				sc.close();  //closes the scanner
			 } else {
				 responseObserver.onNext(printResponse.newBuilder()
							.setPrinting("Your cloud is empty!").build());
				responseObserver.onCompleted(); //close response when it finishes
			 }
				
				
				
			}
		}catch (InterruptedException | FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //put the thread to sleep for 1 second. It needs try and catch
			responseObserver.onNext(printResponse.newBuilder()
					.setPrinting("Your cloud has " + counter + " records.\nEnd of Streaming...").build());
			responseObserver.onCompleted(); //close response when it finishes
			
		}
		
	

	
	private String readFile() throws FileNotFoundException {
		
		Scanner sc = new Scanner(new FileReader("employees_data.csv"));
		String printString = "EmpNo - Date Of Birth - First Name - Last Name - Gender - Hire Date \n";
//		sc.nextLine();

		int counter = 0; // variable i will be used to track the lines to be copied from the csv file
		String st;
		while (sc.hasNextLine())  //returns a boolean value
		{
			st = sc.nextLine();
			String[] data = st.split(",");
			printString += data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5] + "\n";
			counter++;
		}
		sc.close();  //closes the scanner
		
		return printString + "\nWith " + counter + " files";
		}
		
	public Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/capacity.properties")) {

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
