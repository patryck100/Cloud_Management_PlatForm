package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestMethods  {


	public static void main(String[] args) throws UnknownHostException {
		
		/*-------------THIS IS A TESTING CLASS CREATED TO CALL METHODS FROM THE CLIENT CLASS AND VALIDATE DATA.----------*/
		//For more details over each function, please have a look at the ClientCMP.java first
		
		
		
		//SERVER MUST BE RUNNING TO TEST EACH METHOD
		
		ClientCMP client = new ClientCMP();
		
		/*THE RUN METHOD FIRST DISCOVER EACH SERVICE IN THE SERVER, GET THE HOST,
		 * PORT NUMBER AND FINALLY START A CHANNEL FOR EACH SERVICE-*/
		//client.run();
		
		/* BECAUSE THE JMDN TAKES A WHILE TO DISCOVER ALL THE 3 SERVICES, I ALSO CREATED A SEPARATED METHOD
		 * FOR EACH. WHICH CAN BE CALLED INDIVIDUALLY, MAKING LIFE EASIER WHEN IT COMES TO TESTING*/

		/*-------------------- LOGIN AND LOGOUT SERVICE (UNARY API)------------------*/
		//REQUIRES A CONNECTION TO THE LOGIN CANNEL
		client.loginChannel();
		client.login("Patryck", "test");
		client.logout("Patryck");
		
		
		/*-------------------- PRINTING SERVICE (SERVER STREAMING API) ------------------*/
		//The print service prints each data from database individually with
		//a interval of a second. IT REQUIRES A CONNECTION TO THE "PRINT CHANNEL"
		client.printChannel();
		client.print();
		
		/*---------- CLOUD SERVICE (CLIENT STREAMING AND BIDIRECTIONAL STREAMING API) -----*/
		//IT REQUIRES A CONNECTION TO THE CLOUD CHANNEL 
		
		/*Insert data function takes the number of employees records you want to insert in the database
		*as parameter. It will request a series of questions about the employee, which will be validated
		*in the server side. For example:
		*-> Birth date and Hire date must be in the format (yyyy-mm-dd)
		*-> Employee Birthday must be over "1950-01-01" (Retirement age)
		*-> Employee also need to be older than 18 compared to the Hire Date
		*-> Employee's name only accepts letters*/
		client.cloudChannel();
		client.InsertData(1);
		client.removeData(1);

	}
	

}
