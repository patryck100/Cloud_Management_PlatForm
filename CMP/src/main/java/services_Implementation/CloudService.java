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
	@Override
	public StreamObserver<AddRequest> add(StreamObserver<ResponseMessage> responseObserver) {
		
		StreamObserver<AddRequest> requestObserver = new StreamObserver<AddRequest>() {
			
			String nextID;
			String response = "";
			int size = size();
			@Override
			public void onNext(AddRequest value) {
				if(value.getDateOfBirth().matches(regex) && isValid(value.getFirstName())) { //  BIRTHDAY VALIDATION
					//if date of birth is higher than 1950-01-01 and today's date is bigger or equals to (date of birth + 18)
							try {
								if (over18(value.getDateOfBirth()) && aboveRetirement(value.getDateOfBirth())) {
									//This will set automatically the next employee number according to the size of the list
									size++;
									nextID = ""+(size);									 
									employees.add(new Employee(nextID, value.getDateOfBirth(), value.getFirstName(), value.getLastName(), value.getGender(), value.getHireDate()));
									response += "Employee: " + employees.get(counter) + " added successfully" + "\n";
									counter++; //keeps track of the Employee record
								} else
									try {
										if (!over18(value.getDateOfBirth())) {
											response += "Employee: " + value + " has invalid Date of Birth: " + value.getDateOfBirth() + " (below the employment age)"+ "\n";
										} else {
											response += "Employee: " + value + " has invalid Date of Birth: " + value.getDateOfBirth() + " (above retirement age)"+ "\n";
										}
									} catch (HeadlessException | ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
							} catch (HeadlessException | ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}else {
						response += "Invalid data! Record: " + value + " was not added to the list!"+ "\n";
					}
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
//				JOptionPane.showMessageDialog(null, "The Employee record was updated! New size: " + employees.size());
				
				
				//To represent a small local database, this function overwrites whatever is in the list into a CSV file database inside the project folder
				try {
					writeFile(true);
					
					ResponseMessage message = ResponseMessage.newBuilder()
							.setResponse(response + "The Employee record was updated! New size: " + (size))
							.build();

					responseObserver.onNext(message);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				responseObserver.onCompleted();
			}
			
		};
		
		return requestObserver;
	}
	
	
	//Bi Directional API. Client request to remove a sequence of data and the server will validate information and return the data which was removed
	@Override
	public StreamObserver<RemoveRequest> remove(StreamObserver<ResponseMessage> responseObserver) {
		
		StreamObserver<RemoveRequest> requestObserver = new StreamObserver<RemoveRequest>() {
			
			String response = "";
			boolean found = false;
			@Override
			public void onNext(RemoveRequest value) {
				if(size() > 0) { //if size of the database is less or equal to 0 it does not run the code. Instead send a message informing invalidation
							try {
								if (!value.getEmpNo().equalsIgnoreCase("") || !value.getFirstName().equalsIgnoreCase("")) { //if input from user is not empty...
									if (read == false) {
									readFile(); //calls function to collect data from database and save on the "Employee" list to validate user's input
									}
							    	Employee copy;							    	
									for (int i = 0; i < employees.size(); i++) { // goes through the whole array
										copy = employees.get(i); //use a copy to compare input from user to database
										if(copy.getEmpNo().equalsIgnoreCase(value.getEmpNo()) && copy.getFirstName().equalsIgnoreCase(value.getFirstName())) { //if employee data matches with database
											//Keeps track of the employees who are being removed
											response = response + "Employee: "+ employees.get(i) + " removed successfuly"+"\n";
											
											ResponseMessage message = ResponseMessage.newBuilder()
													.setResponse("Removing: " + employees.get(i).getFirstName())
													.build();

											responseObserver.onNext(message); //send a message to the client for each employee found who is being removed
											
											employees.remove(i); //removes employee from the list

											found = true;
											
										} 
									}
									if(found != true) { //if input from user does not match with database, send a message "employee not found"
										ResponseMessage message = ResponseMessage.newBuilder()
												.setResponse("Employee: " + value.getFirstName() + " not Found!")
												.build();

										responseObserver.onNext(message); //send a message to the client informing that employee was not removed
									}

									
								} else {
									response += "Can not remove empty field! Please enter employee number and name before requesting to remove!\n";
									ResponseMessage message = ResponseMessage.newBuilder()
										.setResponse(response)
										.build();

									responseObserver.onNext(message);
								}
							} catch (HeadlessException | FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					}else {
						response += "Cannot remove Employee: " + value.getFirstName() + ", because the database is empty!\n";
						ResponseMessage message = ResponseMessage.newBuilder()
								.setResponse(response)
								.build();

						responseObserver.onNext(message);
						responseObserver.onCompleted();
					}
				
			}

			@Override
			public void onError(Throwable t) {
				t.printStackTrace();
				
			}

			@Override
			public void onCompleted() {
//				JOptionPane.showMessageDialog(null, "The Employee record was updated! New size: " + employees.size());
				
				
				//To represent a small local database, this function overwrites whatever is in the list into a CSV file database inside the project folder
				try {
					if(found == true) {
						writeFile(false);
					}
					ResponseMessage message = ResponseMessage.newBuilder()
							.setResponse(response + "The Employee record was updated! New size: " + (employees.size()))
							.build();

					responseObserver.onNext(message);
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				responseObserver.onCompleted();
			}
			
		};
		
		return requestObserver;
	}
	
	
	
	/* --------------------------------------------------------- DATA MANIPULATION IMPLEMENTATION  --------------------------------------------------*/
	
	//This will store each of the employees records into an Array list
	private List <Employee> employees = new ArrayList<>();
	
	
	/*------------------------------- Input Validation Methods-------------------------------------*/
	
	private int counter = 0;
	private boolean read = false; //used by the remove method. Avoid duplication on list of employees
	
	
	private static String formatOfDate = "yyyy-MM-dd"; //Format of the date must be the same as in the CSV file
	private static DateFormat df = new SimpleDateFormat(formatOfDate); 
		
	
	//creates two variables with today's date. variable cal will be used to calculate date of birth + 18 and compare if it is bigger or equals to today's date
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

	
	/*------------------------------------- Reading from database ---------------------------------*/
	
	//Returns the number of records from database ( represented by the file "Employees_data.csv")
	public int size() {
		int size = 0;
		Scanner sc;
		
		try {
			File file = new File("Employees_data.csv");
			if(!file.exists()) { //If there is no such a file, it generates one
			file.createNewFile();
			}
			sc = new Scanner(new FileReader(file)); //new instance of the scanner will read the file "Employees_data.csv"

		while (sc.hasNextLine())  //check if has next line
		{	//jumps to next line and increase counter size
			sc.nextLine(); 
			size++; 
		}
		sc.close();  //closes the scanner
		
		//error handling
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("File not found!");
		} 
		//returns size of the database or 0 if there is no file
		return size;
		
	}// end of size() function
		
		
	//Reads the list of employees from the database
	private void readFile() throws FileNotFoundException {

			Scanner sc = new Scanner(new File("employees_data.csv"));
//		String printString = "EmpNo - Date Of Birth - First Name - Last Name - Gender - Hire Date \n";
//		sc.nextLine();
			
			boolean exist = false;
			String st;
			while (sc.hasNextLine()) // returns a boolean value
			{
				st = sc.nextLine();
				String[] data = st.split(",");
//				if(!employees.contains(data[0])) {
//				employees.add(new Employee(data[0].substring(1, (data[0].length()-1)).replaceAll("\\\\", ""), data[1].substring(1, (data[1].length()-1)).replaceAll("\\\\", ""),
//						data[2].substring(1, (data[2].length()-1)).replaceAll("\\\\", ""), data[3].substring(1, (data[3].length()-1)).replaceAll("\\\\", ""), data[4].substring(1, (data[4].length()-1)).replaceAll("\\\\", ""),
//						data[5].substring(1, (data[5].length()-1)).replaceAll("\\\\", "")));
				Employee copy = new Employee(data[0], data[1], data[2], data[3], data[4], data[5]);
				for(int i = 0; i < employees.size(); i++) {
//					if(employees.get(i).empNo.equalsIgnoreCase(copy.getEmpNo()) && employees.get(i).getFirstName().equals(copy.getFirstName())) {
					if(employees.get(i).compareTo(copy) == 0) {
						exist = true;
					}
				}
				if(exist != true) {
					employees.add(copy);
				}
				
//				employees.add(new Employee(data[0], data[1], data[2], data[3], data[4], data[5]));
//				}
//			printString += data[0] + ", " + data[1] + ", " + data[2] + ", " + data[3] + ", " + data[4] + ", " + data[5] + "\n";
				System.out.println(data[0] + data[1] + data[2] + data[3] + data[4] + data[5]);
//			counter++;
			}
			sc.close(); // closes the scanner
			
			read = true;
//		return printString + "\nWith " + counter + " files";
//		}
	}

	
	/*---------------------- WRITING INTO THE DATABASE ------------ */
	
	//Writes into the database (represented by the "employees_data.csv" file). If parameter is true, the file will be appended, otherwise it overwrites
	public void writeFile(boolean append) throws IOException {
		
		List<String[]> data = new ArrayList<>();
		
		// Using insertion sort algorithm solved the problem with the Employee number, which was not following a sequence but only number of employees.
		//So before writing into the database, it calls the sorting algorithms and always write it in the correct order
		insertionSort(employees); 
		
		//Copy list of employees into an array of Strings
		for(int i = 0; i< employees.size(); i++) {
			String [] employeeData = {};
			data.add(i, employees.get(i).toString().split(" "));
		}//end of for loop
		
		//Checking if it is collecting the data from ArrayList
//		System.out.println(data.size());
					
		//WRITING DATA FROM EMPLOYEE (OBJECT) ARRAY LIST INTO CSV FILE
		//it takes too parameters, address where it will be saved and a boolean which is set to true if wants just to append data to the file
		//or false to overwrite and generate a new file
		try (CSVWriter writer = new CSVWriter(new FileWriter("Employees_data.csv", append), ',',
                CSVWriter.NO_QUOTE_CHARACTER, //solve problem with double quote character when overwriting the file
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END)) { 
                  
			//Write all items from the data onto CSV file
			writer.writeAll(data);
			
        } 
	}// end of writeFile 
	
	//Sort the list of employees by the "empNo"
	static void insertionSort (List<Employee> employees) { //performance is O(N2) and best performance O(N)
		int j = 1; //this will be the auto-movel starting from 1
		while (j < employees.size()) {
			int i = j; //keeps track of the element which will be checked
			while (i > 0 && employees.get(i).getEmpNo().compareTo(employees.get(i-1).getEmpNo()) < 0) { // if the previous element is bigger than the current
				//swap the elements
				Employee copy = employees.get(i); 
				employees.set(i, employees.get(i-1)); 
				employees.set(i-1, copy); 
				i--; // keeps decreasing until satisfy the while loop
				
			}
			j++; //goes up to the size of the array
		}
	}
	

	
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

			private String empNo;
			private String dateOfBirth;
			private String firstName;
			private String lastName;
			private String gender;
			private String hireDate;

			// constructor
			public Employee(String empNo, String dateOfBirth, String firstName, String lastName, String gender, String hireDate)
			{
				this.empNo = empNo;
				this.dateOfBirth = dateOfBirth;
				this.firstName = firstName;
				this.lastName = lastName;
				this.gender = gender;
				this.hireDate = hireDate;
			}

			public String getEmpNo() {
				return empNo;
			}

			public void setEmpNo(String empNo) {
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

			// so the employee objects can be compared when sorting/searching
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
