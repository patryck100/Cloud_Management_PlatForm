# Cloud_Managment_PlatForm
 
•	“Cloud_Managment_Platform” is a JAVA project developed on Eclipse IDE, which the main goal is to simulate a Management of a Cloud Environment. 
•	This Distributed System project makes use of Protocol Buffers and GRpc to allow communication between Servers, Services and Clients. 
•	The services are named, registered and discovered using Java implementation of multi-cast DNS (JMDNS)
•	The services are:
 -	Login & Logout: Identifies client against database information trough username and password, can allow or denies access to database located in the server side. Makes use of Unary Streaming API (one request / one or zero response).
 -	Cloud service: Allows the client to Insert or Remove one or more data from the database. The data for the Cloud_Managment_Platform app is represented by an Employee list of records stored in a CSV file within the project. It uses Java File Reader and Writer to manipulate data in the CSV file. Makes use of Client (one or many requests / one or zero response) and Bi-directional Streaming API (One or many requests / Zero, one or many responses).
 -	Print service: It outputs a representation from the database, alongside with the amount of data stored in there. It makes use of Server streaming API (client makes one request / receives zero, one or many responses).

For a better visualization of the code, a Graphical user interface (GUI) was generated using Java Application Window, located in the package "GUI".
To access the GUI a connection to the server is REQUIRED, for that:
 - Open the java project: CMP [Cloud_Managment_PlatForm] > src/main/java > server > "ServerCMP.java"
 - Run the "ServerCMP.java"
 - Then go to: CMP [Cloud_Managment_PlatForm] > src/main/java > GUI > "Main_Cloud_Managment_Platform_GUI.java"
 - Run the "Main_Cloud_Managment_Platform_GUI.java"
 
 - "CLEAR" button clears the username and password fields
 - "LOGIN" button allows access to the next graphical interface.
 - Use the username and password below to continue:
User Name: Patryck
Password: test

 - You can click on "SHOW MY DATABASE" button to visualize any Employee in your database!
 - "ADD" button allows you to add a new Employee to the employee records located in the server. It requires
the user to fufill the text fields in the "ADD EMPLOYEE" side. The date of birth must be over 1950-01-01 to be valid and follow
the (yyyy-mm-dd) regex. Employee also must be 18+ to be hired, otherwise it gets invalid response validation from the server.
 - "REMOVE" button requires the Employee number and First name to allow you to remove a certain existent Employee. 
 - "LOGOUT" button requires a confirmation of your username (Patryck), disconnects and bring you back to the login page
 - "CLEAR" button clears all the fields, including the text area where the messages are displayed.
