# Cloud_Managment_PlatForm
 
•	“Cloud_Managment_Platform” is a JAVA project developed on Eclipse IDE, which the main goal is to simulate a Management of a Cloud Environment. 
•	This Distributed System project makes use of Protocol Buffers and GRpc to allow communication between Servers, Services and Clients. 
•	The services are named, registered and discovered using Java implementation of multi-cast DNS (JMDNS)
•	The services are:
 -	Login & Logout: Identifies client against database information trough username and password, can allow or denies access to database located in the server side. Makes use of Unary Streaming API (one request / one or zero response).
 -	Cloud service: Allows the client to Insert or Remove one or more data from the database. The data for the Cloud_Managment_Platform app is represented by an Employee list of records stored in a CSV file within the project. It uses Java File Reader and Writer to manipulate data in the CSV file. Makes use of Client (one or many requests / one or zero response) and Bi-directional Streaming API (One or many requests / Zero, one or many responses).
 -	Print service: It outputs a representation from the database, alongside with the amount of data stored in there. It makes use of Server streaming API (client makes one request / receives zero, one or many responses).

