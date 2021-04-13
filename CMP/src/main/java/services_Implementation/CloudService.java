package services_Implementation;

import java.awt.HeadlessException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

import com.opencsv.CSVWriter;

import CloudManagment.*;
import CloudManagment.CloudServiceGrpc.CloudServiceImplBase;
import io.grpc.stub.StreamObserver;

//This will be the implementation of the Add, remove and Alter data from the cloud
public class CloudService extends CloudServiceImplBase{

	
	
	
	
	//Client Streaming
	@Override
	public StreamObserver<AddRequest> add(StreamObserver<ResponseMessage> responseObserver) {
		
		StreamObserver<AddRequest> requestObserver = new StreamObserver<AddRequest>() {

			@Override
			public void onNext(AddRequest value) {
				if(value.getDateOfBirth().matches(regex) && isValid(value.getFirstName())) { //  BIRTHDAY VALIDATION
					//if date of birth is higher than 1950-01-01 and today's date is bigger or equals to (date of birth + 18)
							try {
								if (over18(value.getDateOfBirth()) && aboveRetirement(value.getDateOfBirth())) {
									//This will set automatically the next employee number according to the size of the list
									int nextID = employees.size()+1; 
									employees.add(new Employee(nextID, value.getDateOfBirth(), value.getFirstName(), value.getLastName(), value.getGender(), value.getHireDate()));
									JOptionPane.showMessageDialog(null, "Employee: " + employees.get(size()-1) + " added successfully");
								} else
									try {
										if (!over18(value.getDateOfBirth())) {
											JOptionPane.showMessageDialog(null, "Invalid Date of Birth: " + value.getDateOfBirth() + " (below the employment age)");
										} else {
											JOptionPane.showMessageDialog(null, "Invalid Date of Birth: " + value.getDateOfBirth() + " (above retirement age)");
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
						JOptionPane.showMessageDialog(null, "Invalid data! Employee was not added to the list!");
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
					writeFile();
					responseObserver.onNext(ResponseMessage.newBuilder()
							.setResponse("The Employee record was updated! New size: " + employees.size())
							.build());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				responseObserver.onCompleted();
			}
			
		};
		
		return requestObserver;
	}
	
	

	@Override
	public StreamObserver<RemoveRequest> remove(StreamObserver<ResponseMessage> responseObserver) {
		// TODO Auto-generated method stub
		return super.remove(responseObserver);
	}
	
	
	
	/* ------------- Data insertion Implementation -----------------*/
	
	//This will store each of the employees records into a list
	private List <Employee> employees = new ArrayList<>();
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
	
	//first parentheses representing 4 digits for year(yyyy) - second parentheses representing first digit could be 0 or not and number 1 to 9
	// or 1 and second digit being 0, 1 or 2 (mm)- and finally last parentheses representing day of the month which can be started either with or
	// not with 0 followed by number 1 to 9 or for a number starting with 1 or 2 followed by 0 to 9 or lastly 3 followed by 0 or 1 (dd).
	private String regex = "(\\d{4})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]$)"; // Regular expression to validate the date of birth
		
	//For this part I created a 
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

	//This will return the size of the list
	public int size() {
		return employees.size();
	}
		
	// Comparable Employee Record
	public class Employee implements Comparable<Object>{

		private int empNo;
		private String dateOfBirth;
		private String firstName;
		private String lastName;
		private String gender;
		private String hireDate;

		// constructor
		public Employee(int empNo, String dateOfBirth, String firstName, String lastName, String gender, String hireDate)
		{
			this.empNo = empNo;
			this.dateOfBirth = dateOfBirth;
			this.firstName = firstName;
			this.lastName = lastName;
			this.gender = gender;
			this.hireDate = hireDate;
		}

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
	
	public void writeFile() throws IOException {
		
		List<String[]> data = new ArrayList<>();
		
		for(int i = 0; i< size(); i++) {
			data.add(i, employees.get(i).toString().split(" "));
		}
		//Checking if it is collecting the data from ArrayList
		System.out.println(data.size());
					
		//WRITING DATA FROM EMPLOYEE (OBJECT) ARRAY LIST INTO CSV FILE
		try (CSVWriter writer = new CSVWriter(new FileWriter("Employees_data.csv"))) {
                  
			//Write all items from the data onto CSV file
			writer.writeAll(data);
				
        }		
	}
	
	
}
