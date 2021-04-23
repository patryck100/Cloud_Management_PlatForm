package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import CloudManagment.AddRequest;
import CloudManagment.CloudServiceGrpc;
import CloudManagment.RemoveRequest;
import CloudManagment.ResponseMessage;
import client.ClientCMP;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import loginCMP.LoginServiceGrpc;
import loginCMP.LogoutRequest;
import loginCMP.LogoutResponse;
import print.printRequest;
import print.printResponse;
import print.printServiceGrpc;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;

public class Cloud_Managment_Platform_GUI extends JFrame {

	private JTextField fName_add = null;
	private JTextField lName = null;
	private JTextField dateOfBirth;
	private JTextField hireDate;
	private JTextField empNumber_remove = null;
	private JTextField fName_remove = null;
	private JComboBox gender;
	private JTextArea response;
	
	
	//Object of classes
	private ClientCMP client = new ClientCMP();
	private Main_Cloud_Managment_Platform_GUI loginGUI = new Main_Cloud_Managment_Platform_GUI();
	private List <AddRequest> requests = new ArrayList<>();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cloud_Managment_Platform_GUI frame = new Cloud_Managment_Platform_GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cloud_Managment_Platform_GUI() {
		
		getContentPane().setBackground(new Color(25, 25, 112));
		setBounds(100, 100, 635, 430);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 192, 566, 171);
		getContentPane().add(scrollPane);
		
		response = new JTextArea();
		scrollPane.setViewportView(response);
		response.setAutoscrolls(false);
		response.setEditable(false);
		response.setWrapStyleWord(true);
		response.setBackground(new Color(248, 248, 255));
		response.setLineWrap(true);

		
		JLabel lblNewLabel = new JLabel("OUTPUT");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(272, 173, 81, 16);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("ADD EMPLOYEE");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setForeground(new Color(152, 251, 152));
		lblNewLabel_1.setBounds(242, 6, 143, 16);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("REMOVE EMPLOYEE");
		lblNewLabel_1_1.setForeground(new Color(240, 128, 128));
		lblNewLabel_1_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(227, 105, 177, 16);
		getContentPane().add(lblNewLabel_1_1);
		
		/*------------------ ADD new Employee Button, Client Streaming API------------*/
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// If any of the ADD Employee fields is empty, show message Dialog!
				if (dateOfBirth.getText().equals(null) || fName_add.getText().equals(null)
						|| lName.getText().equals(null) || gender.getSelectedItem().toString().equals(null) 
						|| hireDate.getText().equals(null)) {
					
					JOptionPane.showMessageDialog(addButton, "Please, enter all Employee data before sending request");
					
				} else {
					
					// Blocks the channel and waits for the response from the server when client send "onComplete"
					CountDownLatch latch = new CountDownLatch(1);
					CloudServiceGrpc.CloudServiceStub CloudClient = loginGUI.getCloudClient();
					StreamObserver<AddRequest> requestObserver = CloudClient.add(new StreamObserver<ResponseMessage>() {

						@Override
						public void onNext(ResponseMessage value) {
							// TODO Auto-generated method stub
							System.out.println(value.getResponse());
							response.append(value.getResponse()); // print response on the text area

						}

						@Override
						public void onError(Throwable t) {
							t.printStackTrace();

						}

						@Override
						public void onCompleted() {
							// the server is done sending us data
							// onCompleted will be called right after onNext();
							System.out.println("Server has completed sending messages");

							// Client finished sending requests, so it count down waiting for the server's
							// response
							latch.countDown();
						}

					}); // end of "requestObserver"
					
					AddRequest request;
					Scanner sc = new Scanner(System.in);
					String birthDay = dateOfBirth.getText();
					String firstName = fName_add.getText();
					String lastName = lName.getText();
					String Gender = gender.getSelectedItem().toString();
					String hDate = hireDate.getText();

					// Set values to the the request
					request = AddRequest.newBuilder().setDateOfBirth(birthDay).setFirstName(firstName)
							.setLastName(lastName).setGender(Gender).setHireDate(hDate).build();

					int repeat = JOptionPane.showConfirmDialog(null,
							"Press \"Confirm\" if your would like to add one more Employee!\n"
									+ "Otherwise operation ends and employee(s) will be added automatically");
					
					
					if (repeat == JOptionPane.YES_OPTION) {
						response.append("Employee: " + firstName + " is being processed...\n");
						
						// It will save each request in an ArrayList
						requests.add(request);
						// set all text from add method to null every time a new employee is added
						clearAdd();
						
						JOptionPane.showMessageDialog(addButton,
								"Please, enter new employee and press \"ADD\" again to complete operation");

					} else {
						
						requests.add(request);
						// At the end, when the Client answer he does not want to send any more request, it collects all requests
						// added before and send it one by one.
						for (int i = 0; i< requests.size(); i++) {
							requestObserver.onNext(requests.get(i));
						}
						
						//Once it is done sending requests, it tells the server that it has completed
						requestObserver.onCompleted();
						
						// set all text from add method to null
						clearAdd();
						requests.clear();
						
						//Show message to the user
						JOptionPane.showMessageDialog(addButton, "Alright, the employee list was updated!");
					}

					// This is used to wait for the server's response. Otherwise the channel closes
					// and there is no time to get response
					try {
						latch.await(3L, TimeUnit.SECONDS);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
		
		
		
		
		
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setForeground(new Color(152, 251, 152));
		addButton.setBackground(new Color(0, 0, 0));
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		addButton.setBounds(512, 40, 117, 42);
		getContentPane().add(addButton);
		
		/*----------------------- Remove Employee Button, BiDirectional Streaming API --------------------*/
		
		JButton removeButton = new JButton("REMOVE");
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// If any of the Remove Employee fields is empty, show message Dialog!
				if (empNumber_remove.getText().equals(null) || fName_remove.getText().equals(null)) {

					JOptionPane.showMessageDialog(addButton,
							"Please, specify Employee number and First name to remove the Employee record");

				} else {

					// Blocks the channel and waits for the response from the server when client
					// send "onComplete"
					CountDownLatch latch = new CountDownLatch(1);
					CloudServiceGrpc.CloudServiceStub CloudClient = loginGUI.getCloudClient();

					StreamObserver<RemoveRequest> requestObserver = CloudClient
							.remove(new StreamObserver<ResponseMessage>() {

								@Override
								public void onNext(ResponseMessage value) {
									// TODO Auto-generated method stub
									System.out.println(value.getResponse());
									appendResponse(value.getResponse() + "\n");
									clearRemove();
								}

								@Override
								public void onError(Throwable t) {
									t.printStackTrace();

								}

								@Override
								public void onCompleted() {
									// the server is done sending us data
									// onCompleted will be called right after onNext();
									System.out.println("Server has completed sending messages");

									// Client finished sending requests, so it count down waiting for the server's
									// response
									latch.countDown();
								}

							}); // end of "requestObserver"

					RemoveRequest request;
					Scanner sc = new Scanner(System.in);
					int empNumber = Integer.parseInt(empNumber_remove.getText());
					String firstName = fName_remove.getText();

					// Set values to the the request
					request = RemoveRequest.newBuilder().setEmpNo(empNumber).setFirstName(firstName).build();

					// Send each request one by one
					requestObserver.onNext(request);

					// Tell the server that the client has completed sending requests
					requestObserver.onCompleted();

					// This is used to wait for the server's response. Otherwise the channel closes
					// and there is no time to get response
					try {
						latch.await(3L, TimeUnit.SECONDS);
					} catch (InterruptedException ex) {
						// TODO Auto-generated catch block
						ex.printStackTrace();
					}
				}

			}
		});
		removeButton.setBackground(new Color(0, 0, 0));
		removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		removeButton.setForeground(new Color(240, 128, 128));
		removeButton.setBounds(512, 138, 117, 42);
		getContentPane().add(removeButton);
		
		
		/*------------------------ Logout Button, Unary API ----------------------*/
		JButton logoutButton = new JButton("LOGOUT");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginServiceGrpc.LoginServiceBlockingStub LoginClient = loginGUI.getLoginClient();
				
				String username = JOptionPane.showInputDialog(logoutButton, "Please confirm username: " );
				
				LogoutRequest request = LogoutRequest.newBuilder().setUsername(username).build();
				
				LogoutResponse response = LoginClient.logout(request);
				JOptionPane.showMessageDialog(logoutButton, response.getResponseMessage());
				if(response.getResponseCode() == 0) {
					loginGUI.frame.setVisible(true);
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        dispose();	
				}
				
				
			}
		});
		logoutButton.setBackground(new Color(0, 0, 0));
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutButton.setForeground(new Color(255, 215, 0));
		logoutButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		logoutButton.setBounds(512, 369, 117, 29);
		getContentPane().add(logoutButton);
		
		/*------------- PRINT DATABASE WHEN CLICKED ON "SHOW MY DATABASE", Server Streaming API -----------------*/
		
		JButton printButton = new JButton("SHOW MY DATABASE");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printServiceGrpc.printServiceBlockingStub PrintClient = loginGUI.getPrintClient();
				
				// Client request
				printRequest print = printRequest.newBuilder().setPrint(true).build();
				
//				PrintClient.print(print).forEachRemaining(printResponse ->{
//					System.out.println(printResponse.getPrinting()); //print response on the text area
//				});
				
				new Thread()
				{
				    public void run() {
				    	try {
							Iterator<printResponse> responces = PrintClient.print(print);

							// This loop will print each of the responses while the server is still sending
							// it
							while (responces.hasNext()) {
								printResponse temp = responces.next();
								System.out.println(temp.getPrinting()); 
								//appendResponse(temp.getPrinting() + "\n"); //print response on the text area
								response.append(temp.getPrinting() + "\n");
								
							} // end of while loop

						} catch (StatusRuntimeException e) {
							e.printStackTrace();
						}
				    	
				    }
				}.start();
				
				
			}
		});
		printButton.setBackground(Color.WHITE);
		printButton.setForeground(new Color(0, 0, 0));
		printButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		printButton.setBounds(10, 369, 229, 29);
		getContentPane().add(printButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(407, 105, 104, 12);
		getContentPane().add(separator);
		
		JLabel lblNewLabel_3 = new JLabel("First Name:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(new Color(144, 238, 144));
		lblNewLabel_3.setBounds(16, 54, 81, 16);
		getContentPane().add(lblNewLabel_3);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(6, 105, 209, 12);
		getContentPane().add(separator_1);
		
		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(383, 6, 246, 12);
		getContentPane().add(separator_2);
		
		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(6, 6, 224, 12);
		getContentPane().add(separator_3);
		
		JLabel lblNewLabel_3_1 = new JLabel("Last Name:");
		lblNewLabel_3_1.setForeground(new Color(144, 238, 144));
		lblNewLabel_3_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_1.setBounds(16, 79, 81, 16);
		getContentPane().add(lblNewLabel_3_1);
		
		fName_add = new JTextField();
		fName_add.setBackground(new Color(248, 248, 255));
		fName_add.setBounds(109, 48, 130, 26);
		getContentPane().add(fName_add);
		fName_add.setColumns(10);
		
		lName = new JTextField();
		lName.setBackground(new Color(248, 248, 255));
		lName.setColumns(10);
		lName.setBounds(109, 73, 130, 26);
		getContentPane().add(lName);
		
		JLabel lblNewLabel_2_1 = new JLabel("Date Of Birth:");
		lblNewLabel_2_1.setForeground(new Color(144, 238, 144));
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_2_1.setBounds(10, 26, 89, 16);
		getContentPane().add(lblNewLabel_2_1);
		
		dateOfBirth = new JTextField();
		dateOfBirth.setBackground(new Color(248, 248, 255));
		dateOfBirth.setText("(yyyy-mm-dd)");
		dateOfBirth.setColumns(10);
		dateOfBirth.setBounds(109, 20, 130, 26);
		getContentPane().add(dateOfBirth);
		
		JLabel lblNewLabel_3_2 = new JLabel("Hiring Date:");
		lblNewLabel_3_2.setForeground(new Color(144, 238, 144));
		lblNewLabel_3_2.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_2.setBounds(252, 36, 81, 16);
		getContentPane().add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Gender:");
		lblNewLabel_3_1_1.setForeground(new Color(144, 238, 144));
		lblNewLabel_3_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_1_1.setBounds(252, 61, 81, 16);
		getContentPane().add(lblNewLabel_3_1_1);
		
		hireDate = new JTextField();
		hireDate.setBackground(new Color(248, 248, 255));
		hireDate.setText("(yyyy-mm-dd)");
		hireDate.setColumns(10);
		hireDate.setBounds(345, 34, 130, 26);
		getContentPane().add(hireDate);
		
		gender = new JComboBox();
		gender.setBackground(new Color(255, 255, 255));
		gender.setForeground(new Color(0, 0, 0));
		gender.setBounds(394, 60, 81, 27);
		gender.setModel(new DefaultComboBoxModel(new String[] {"","M", "F"}));
		getContentPane().add(gender);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Emp Number:");
		lblNewLabel_2_1_1.setForeground(new Color(240, 128, 128));
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_2_1_1.setBounds(16, 132, 89, 16);
		getContentPane().add(lblNewLabel_2_1_1);
		
		empNumber_remove = new JTextField();
		empNumber_remove.addKeyListener(new KeyAdapter() {
		
		/*------------- WHEN TYPED ON "EMPLOYEE NUMBER" TEXTFIELD ------------*/
			@Override
			public void keyTyped(KeyEvent evt) {
				//Consume any character different from numbers
				if (!Character.isDigit(evt.getKeyChar())){
		            evt.consume();
		        }
			}
		});
		empNumber_remove.setBackground(new Color(248, 248, 255));
		empNumber_remove.setColumns(10);
		empNumber_remove.setBounds(109, 126, 130, 26);
		getContentPane().add(empNumber_remove);
		
		JLabel lblNewLabel_3_2_1 = new JLabel("First Name:");
		lblNewLabel_3_2_1.setForeground(new Color(240, 128, 128));
		lblNewLabel_3_2_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_2_1.setBounds(16, 156, 81, 16);
		getContentPane().add(lblNewLabel_3_2_1);
		
		fName_remove = new JTextField();
		fName_remove.setBackground(new Color(248, 248, 255));
		fName_remove.setColumns(10);
		fName_remove.setBounds(109, 154, 130, 26);
		getContentPane().add(fName_remove);
		
		
		/*-------------------- Clear Button --------------------*/
		JButton clearButton = new JButton("CLEAR");
		clearButton.setBackground(new Color(0, 0, 0));
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //when clicked on "CLEAR", it set all fields to null
				clearAll();
			}
		});
		clearButton.setForeground(new Color(255, 215, 0));
		clearButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		clearButton.setBounds(512, 93, 117, 36);
		getContentPane().add(clearButton);
	}
	
	public void appendResponse(String line) {
		 response.append(line);	
	}
	
	
	//Clear fields in the add area
	public void clearAdd() {
		
		fName_add.setText(null);
		lName.setText(null);
		dateOfBirth.setText(null);
		hireDate.setText(null);
		gender.setSelectedItem(null);
	}
	
	//Clear fields in the remove area
	public void clearRemove() {
		empNumber_remove.setText(null);
		fName_remove.setText(null);
	}
	
	// set all text from add method to null every time a new employee is added
	public void clearAll() {
		fName_add.setText(null);
		lName.setText(null);
		dateOfBirth.setText(null);
		hireDate.setText(null);
		gender.setSelectedItem(null);
		empNumber_remove.setText(null);
		fName_remove.setText(null);
		response.setText(null);
	}
	
}
