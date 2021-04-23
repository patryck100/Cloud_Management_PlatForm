package services_Implementation;

import java.awt.HeadlessException;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import com.opencsv.CSVWriter;
import CloudManagment.*;
import CloudManagment.CloudServiceGrpc.CloudServiceImplBase;
import io.grpc.stub.StreamObserver;


public class CloudService extends CloudServiceImplBase{


	
	//CLIENT STREAMING API
	//Client sends one or more requests to insert Employees into a record database.
	//Application validates input from user, update database and return response to the user
	@Override
	public StreamObserver<AddRequest> add(StreamObserver<ResponseMessage> responseObserver) {

		// Starts by creating a listener which will keep waiting for new requests until
		// the last request send a "onComplete"
		StreamObserver<AddRequest> requestObserver = new StreamObserver<AddRequest>() {

			// As it is a Client streaming, after each request is validated, the response
			// is accumulated in a String variable called "response"
			String response = "";

			// When the size() function is called, it creates a new database if it does not
			// exist,
			// make a list of all employee numbers already used and returns the size of the
			// database
			int size = size();

			@Override
			public void onNext(AddRequest value) {
				if (value.getDateOfBirth().matches(regex) && isValid(value.getFirstName())) { // BIRTHDAY VALIDATION
					// if date of birth is higher than 1950-01-01 and today's date is bigger or
					// equals to (date of birth + 18)
					try {
						if (over18(value.getDateOfBirth()) && aboveRetirement(value.getDateOfBirth())) {
							// Keeps track of the size of the list even when adding multiple employees (streaming).
							size++;

							/*------- GENERATES NEXT EMPLOYEE NUMBER BY VALIDATING IT AGAINST THE LIST OF EMPLOYEE NUMBERS ALREADY USED ----*/
							int nextEmpNo = 1;
							while (listOfEmpNo.contains(nextEmpNo)) { // increase the empNo if it already exists in the
																		// list
								nextEmpNo++;
							}
							// After figuring out next empNo, add it to the list of employee numbers already
							// existent
							listOfEmpNo.add(nextEmpNo);

							// Add employee to the list (which will later appended to the database)
							employees.add(new Employee(nextEmpNo, value.getDateOfBirth(), value.getFirstName(),
									value.getLastName(), value.getGender(), value.getHireDate()));

							// Append response to later be sent back to the client
							response += "Employee: " + employees.get(employees.size() - 1) + " added successfully"
									+ "\n";

						} else { // If input is invalid

							if (!over18(value.getDateOfBirth())) {
								response += "Employee: " + value + " has invalid Date of Birth: "
										+ value.getDateOfBirth() + " (below the employment age)" + "\n";
							} else {
								response += "Employee: " + value + " has invalid Date of Birth: "
										+ value.getDateOfBirth() + " (above retirement age)" + "\n";
							}
						}

					} catch (HeadlessException | ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else { // Invalid data
					response += "Invalid data! Please make sure to fill all information correctly before attempting insertion!"
							+ "\n";
				}

			} // End of "onNext"

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
			} // End of "onError"

			@Override
			public void onCompleted() { // When client request "onComplete", it responds with the last message and
										// update database

				// To represent a small local database, this function overwrites whatever is in
				// the list into a CSV file database inside the project folder
				try {
					writeFile(true);

					ResponseMessage message = ResponseMessage.newBuilder()
							.setResponse(response + "The Employee record was updated! New size: " + (size) + "\n")
							.build();

					responseObserver.onNext(message);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// Complete Streaming
				responseObserver.onCompleted();
			}

		};
		// While the Client is sending "onNext" requests, it keeps the recursion loop
		return requestObserver;
	}
	
	
	// Bi Directional API. Client request to remove a sequence of Employee records, the
	// server will validate information and return the data which was removed
	@Override
	public StreamObserver<RemoveRequest> remove(StreamObserver<ResponseMessage> responseObserver) {

		StreamObserver<RemoveRequest> requestObserver = new StreamObserver<RemoveRequest>() {

			String response = "";
			
			//Only overwrites the employee file if the employee record is found in the database
			boolean found = false;

			@Override
			public void onNext(RemoveRequest value) {
				if (size() > 0) { // if size of the database is less or equal to 0 it does not run the code.
									// Instead send a message informing invalidation
					try {// if input from user is not empty...
						if (value.getEmpNo() > 0 && !value.getFirstName().equals(null)) { 
							
							//there are case of deleting many employees in the same steam, 
							//the "read" boolean helps to avoid duplication
							if (read == false) {
								readFile(); // calls function to collect data from database and save on the "Employee"
											// list to validate user's input
							}
							Employee copy; //Creates a copy of the employee data
							for (int i = 0; i < employees.size(); i++) { // goes through the whole array
								copy = employees.get(i); // use the copy to compare input from user to database
								if (copy.getEmpNo() == value.getEmpNo()
										&& copy.getFirstName().equals(value.getFirstName())) { // if employee data
																								// matches with database
									// Keeps track of the employees who are being removed
									response = response + "Employee: " + employees.get(i) + " removed successfuly"
											+ "\n";

									ResponseMessage message = ResponseMessage.newBuilder()
											.setResponse("Removing: " + employees.get(i).getFirstName()).build();

									responseObserver.onNext(message); // send a message to the client for each employee
																		// found who is being removed
									
									
									employees.remove(i); // removes employee from the list
									
									//Inform the lisfOfEmp that certain employee number is available to be used now
									for (int j = 0; j<listOfEmpNo.size() ; j++) {
										if(listOfEmpNo.get(j) == value.getEmpNo()) {
											//remove employee number from list, and make it available to use again
											//when the "add" function is called
											listOfEmpNo.remove(j); 
										}
									}
									found = true; //when "found" is true it means the employee list needs to be updated (overwritten) 

								}
							}
							if (found != true) { // if input from user does not match with database, send a message
													// "employee not found"
								ResponseMessage message = ResponseMessage.newBuilder()
										.setResponse("Employee: " + value.getFirstName() + " not Found!").build();

								responseObserver.onNext(message); // send a message to the client informing that
																	// employee was not removed
							}

						} else { //if any field is empty
							response += "Can not remove empty field! Please enter employee number and name before requesting to remove!\n";
							ResponseMessage message = ResponseMessage.newBuilder().setResponse(response).build();

							responseObserver.onNext(message);
						}
					} catch (HeadlessException | FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else { //if database is empty
					response += "Cannot remove Employee: " + value.getFirstName()
							+ ", because the database is empty!\n";
					ResponseMessage message = ResponseMessage.newBuilder().setResponse(response).build();

					responseObserver.onNext(message);
					responseObserver.onCompleted();
				}

			}// End of "onNext"

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}//End of "onError"

			@Override
			public void onCompleted() { //When client completes sending requests
				
				//To represent a small local database, this function overwrites whatever
				//is in the list into a CSV file database inside the project folder
				try {
					if(found == true) { //only overwrites if the employee is found and removed from employee list
						writeFile(false);
					}
					ResponseMessage message = ResponseMessage.newBuilder()
							.setResponse(response + "The Employee record was updated! New size: " + employees.size())
							.build();
					
					//Sends last response to the client informing final response and now size of the database
					responseObserver.onNext(message);
					
				} catch (IOException e) { //catch error if occurs while overwriting database
					e.printStackTrace();
				}
				//This avoid duplication when client decides to add a new employee in a streaming after 
				//removing one. It clears both employee's list and numbers in order to start again
				//and set "read" back to false
				if (read == true) {
					employees.clear();
					listOfEmpNo.clear();
					read = false;
				}
				//finally, when everything is done, it completes sending response and close the streaming
				responseObserver.onCompleted();
				
			} //end of "onComplete"
			
		};
		
		//while the request is "onNext", keeps the recursion
		return requestObserver;
		
	}// End of remove function
	
	
	
	/* --------------------------------------------------------- DATA MANIPULATION IMPLEMENTATION  --------------------------------------------------*/
	
	//This will store each of the employees records into an Array list
	private List <Employee> employees = new ArrayList<>();
	private List <Integer> listOfEmpNo = new ArrayList<>();
	
	
	/*------------------------------- Input Validation Methods-------------------------------------*/
	
	private boolean read = false; //used by the remove method. AVOID DUPLICATION ON LIST OF EMPLOYEES
	
	
	private static String formatOfDate = "yyyy-MM-dd"; //Format of the date must be the same as in the CSV file
	private static DateFormat df = new SimpleDateFormat(formatOfDate); 
		
	
	//creates two variables with today's date. Variable "cal" will be used to calculate date of birth + 18 and compare if it is bigger or equals to today's date
	private static Calendar today = Calendar.getInstance(); 
	private static Calendar cal = Calendar.getInstance();
	
	// Returns True if Employee's over 18 years old
	private boolean over18(String dateOfBirth) throws ParseException {
			cal.setTime(df.parse(dateOfBirth)); 
		    cal.add(Calendar.YEAR, 18); //calculates the birthday + 18.
		    if(today.compareTo(cal) >= 0) { //Compares if Employee's date of birth + 18 is bigger than today's date
		    	return true; // if true it means the Employee is over 18
		    } else {
		    	return false; //else returns false
		    }
		}
		
	// Returns true if Employee's birthday is later than "1950-01-01"
	private boolean aboveRetirement(String dateOfBirth) {
	    if(dateOfBirth.compareTo("1950-01-01") >= 0) {
		   	return true;
	   } else {
	    	return false;
	    }
	}
	
	private String regex = "(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]$)"; // Regular expression to validate the date of birth
	//first parentheses representing 4 digits for year(yyyy) - second parentheses representing first digit could be 0 or not and number 1 to 9
	// or 1 and second digit being 0, 1 or 2 (mm)- and finally last parentheses representing day of the month which can be started either with or
	// not with 0 followed by number 1 to 9 or for a number starting with 1 or 2 followed by 0 to 9 or lastly 3 followed by 0 or 1 (dd).
		
	
	//Validates if First Name has only letters
	private boolean isValid(String first_name) {
			 if(first_name.matches("[a-zA-Z]+")) { //check if string first name matches criteria of being only letters from 'A' to 'Z'
				 return true;
			 } else if(first_name.equals("") || first_name.equals(null)){ //check if first name is empty
				 System.out.println("Employee with invalid input! First name cannot be empty");
				 return false;
			 } else {
				 System.out.println("Employee with invalid First name!");
				 return false;
			 }
		 }

	
	/*------------------------------------- READING FROM DATABASE ---------------------------------*/
	
	//Returns the number of records from database ( represented by the file "Employees_data.csv")
	//or create a new file if the file does not exist
	public int size() {
		int size = 0;
		Scanner sc;
		String st;
		
		try {
			File file = new File("Employees_data.csv");
			if(!file.exists()) { //IF THERE IS NO SUCH A FILE, IT WILL GENERATES ONE
			file.createNewFile();
			}
			sc = new Scanner(new FileReader(file)); //new instance of the scanner will read the file "Employees_data.csv"
			
		while (sc.hasNextLine())  //check if has next line
		{	//jumps to next line and increase size
			st = sc.nextLine(); 
			
			//Collects number of each employee and add it into a list
			//so then it can be validated in order to GENERATE THE NEXT EMPLOYEE NUMBER AUTOMATICALLY
			listOfEmpNo.add(Integer.parseInt(st.split(",")[0])); 
			size++; 
		}
		sc.close();  //closes the scanner
		
		//error handling
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found!");
		} 
		//returns size of the database or 0 if there is no data into the file
		return size;
		
	}// end of size() function
	
		
		
	//Reads the list of employees from the database
	private void readFile() throws FileNotFoundException {
			
			//Access the database (represented by the Employee CSV file)
			Scanner sc = new Scanner(new File("employees_data.csv"));

			//"exist" is used to validate data from file. By keeping the connection to the channel the
			//employee list was accumulating repeated records. The solution was to create a boolean to
			//validate if employee already exist or not.
			boolean exist = false;
			
			String st; //used to store value of each row from the csv file
			while (sc.hasNextLine()) // returns a boolean value
			{
				st = sc.nextLine();
				String[] data = st.split(",");
				
				//By creating a copy of the Employee record before inserting into the "employee" list
				//and validating it using the "exist" boolean, it avoid repeated data
				Employee copy = new Employee(Integer.parseInt(data[0]), data[1], data[2], data[3], data[4], data[5]);
				for(int i = 0; i < employees.size(); i++) {
					if(employees.get(i).empNo == copy.getEmpNo() && employees.get(i).getFirstName().equalsIgnoreCase(copy.getFirstName())) {
						exist = true;
					}
				}
				if(exist != true) { //If employee is not already in the list , add it.
					employees.add(copy);
				}
				
				//visual representation of the list while reading it
				//System.out.println(data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5]);

			}
			sc.close(); // closes the scanner
			
			read = true; //In order to avoid repeated data, set boolean "read" to true

	} //End of readFile function

	
	/*---------------------- WRITING INTO THE DATABASE ------------ */
	
	//Writes into the database (represented by the "employees_data.csv" file). If parameter "append" is 
	//true, the file will be appended, otherwise it overwrites
	public void writeFile(boolean append) throws IOException {
		
		List<String[]> data = new ArrayList<>();
		
		// Using insertion sort algorithm solved the problem with the Employee number, which was not following a sequence but only number of employees.
		//So before writing into the database, it calls the sorting algorithms and always write it in the correct order
		insertionSort(employees); 
		
		
		//Copy list of employees into an array of Strings
		for(int i = 0; i< employees.size(); i++) {
			String [] employeeData = {Integer.toString(employees.get(i).getEmpNo()), employees.get(i).getDateOfBirth(), employees.get(i).getFirstName()
					, employees.get(i).getLastName(), employees.get(i).getGender(), employees.get(i).getHireDate()};
			
			data.add(employeeData); //add each record from "employee" list into an array list called "data"
		}//end of for loop
		
		//Checking if it is collecting the data from ArrayList
//		System.out.println(data.size());
					
		//WRITING DATA FROM EMPLOYEE (OBJECT) ARRAY LIST INTO CSV FILE
		//it takes too parameters, address where it will be saved and a boolean which is set to true if wants just to append data into the file
		//or false to overwrite and generate a new file
		try (CSVWriter writer = new CSVWriter(new FileWriter("Employees_data.csv", append), ',',
                CSVWriter.NO_QUOTE_CHARACTER, //solved problem with double quote character when overwriting the file
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) { 
                  
			//Write all items from the data onto CSV file
			writer.writeAll(data);
			
        } 
	}// end of writeFile 

	/*------------------------ SORTING EMPLOYEES LIST ---------------*/
	
	//Sort the list of employees by "Employee number"
	static void insertionSort (List<Employee> employees) { //performance is O(N2) and best performance O(N)
		int j = 1; //this will be the auto-movel starting from 1
		while (j < employees.size()) {
			int i = j; //keeps track of the element which will be checked
			while (i > 0 && employees.get(i).getEmpNo() < employees.get(i-1).getEmpNo() ) { // if the previous element is bigger than the current
				//swap the elements
				Employee copy = employees.get(i); 
				employees.set(i, employees.get(i-1)); 
				employees.set(i-1, copy); 
				i--; // keeps decreasing until satisfy the while loop
				
			}
			j++; //goes up to the size of the array
		}
	} //End of insertion sort
	

	
	/* ---------- RETURNS THE PROPERTIES OF THE SERVICE (USED TO REGISTER SERVICES, JMDNS - SERVER SIDE) -------------*/
		
	public Properties getProperties() {

		Properties prop = null;

		try (InputStream input = new FileInputStream("src/main/resources/cloud.properties")) {

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
	
	/* ------------------------ COMPARABLE EMPLOYEE CLASS, USED TO STORE EACH RECORD -------------------- */
	
		public class Employee implements Comparable<Object>{

			//data members
			private int empNo;
			private String dateOfBirth;
			private String firstName;
			private String lastName;
			private String gender;
			private String hireDate;

			// ---------- Constructor --------
			public Employee(int empNo, String dateOfBirth, String firstName, String lastName, String gender, String hireDate)
			{
				this.empNo = empNo;
				this.dateOfBirth = dateOfBirth;
				this.firstName = firstName;
				this.lastName = lastName;
				this.gender = gender;
				this.hireDate = hireDate;
			}

			/* ------- Getters and Setters -------*/
			
			public int getEmpNo() {
				return empNo;
			}

			public void setEmpNo(int empNo) {
				this.empNo = empNo;
			}

			public String getDateOfBirth() {
				return dateOfBirth;
			}

			public void setDateOfBirth(String dateOfBirth) {
				this.dateOfBirth = dateOfBirth;
			}

			public String getFirstName() {
				return firstName;
			}

			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}

			public String getLastName() {
				return lastName;
			}

			public void setLastName(String lastName) {
				this.lastName = lastName;
			}

			public String getGender() {
				return gender;
			}

			public void setGender(String gender) {
				this.gender = gender;
			}

			public String getHireDate() {
				return hireDate;
			}

			public void setHireDate(String hireDate) {
				this.hireDate = hireDate;
			}

			// The employee objects can be compared when sorting/searching
			// NOTE: this will only allow comparisons based on the firstName
			public int compareTo(Object obj) {
				Employee emp = (Employee)obj;
				return firstName.compareTo(emp.getFirstName());
			}

			// return a String containing the employee details
			@Override
			public String toString()
			{
				return empNo+" "+dateOfBirth+" "+firstName+" "+lastName+" "+gender +" "+ hireDate;
			}
		}
	
		
	
}
