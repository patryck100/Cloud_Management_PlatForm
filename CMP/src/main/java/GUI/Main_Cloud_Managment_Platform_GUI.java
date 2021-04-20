package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import client.ClientCMP;
import loginCMP.LoginResponse;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Main_Cloud_Managment_Platform_GUI {

	private JFrame frame;
	private JTextField LoginText;
	private JPasswordField passwordField;
	private ClientCMP client = new ClientCMP();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Cloud_Managment_Platform_GUI window = new Main_Cloud_Managment_Platform_GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main_Cloud_Managment_Platform_GUI() {
		
		System.out.println("Discovering services, please wait...");
		
		//Makes a connection to the Login Channel
		client.run();
		client.loginChannel();
		client.printChannel();
		client.cloudChannel();
			
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBackground(new Color(0, 0, 51));
		frame.getContentPane().setBackground(new Color(25, 25, 112));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//----------------------------- Login button ----------------------------
		JButton LoginButton = new JButton("Login");
		LoginButton.setBackground(Color.LIGHT_GRAY);
		LoginButton.setForeground(new Color(144, 238, 144));
		LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //when hover the "Login" button, it changes the cursor to a hand cursor
		
		//When clicked on the "Login" button...
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginResponse response = client.login(LoginText.getText(), String.valueOf(passwordField.getPassword()));
				JOptionPane.showMessageDialog(LoginButton, response.getResponseMessage());

				if (response.getResponseCode() == 1) {
					Cloud_Managment_Platform_GUI nextGUI = new Cloud_Managment_Platform_GUI();
					nextGUI.setVisible(true);
					nextGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        frame.dispose();
					
					
				}
			}
		});
		
		
		LoginButton.setBounds(123, 161, 97, 29);
		frame.getContentPane().add(LoginButton);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(85, 84, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		LoginText = new JTextField();
		LoginText.setBackground(new Color(248, 248, 255));
		LoginText.setBounds(172, 79, 130, 26);
		frame.getContentPane().add(LoginText);
		LoginText.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(85, 116, 75, 16);
		frame.getContentPane().add(lblPassword);
		
		
		
		// -------------------------- Clear Button --------------------------
		JButton ClearButton = new JButton("Clear");
		//When clicked on "Clear" button, it set Login and password fields to null, in other words it clear the fields
		ClearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
				LoginText.setText(null);
				passwordField.setText(null);
			}
		});
		
		
		ClearButton.setBackground(Color.LIGHT_GRAY);
		ClearButton.setForeground(new Color(244, 164, 96));
		ClearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ClearButton.setBounds(221, 161, 81, 29);
		frame.getContentPane().add(ClearButton);
		
		passwordField = new JPasswordField();
		passwordField.setBackground(new Color(248, 248, 255));
		passwordField.setBounds(172, 111, 130, 26);
		frame.getContentPane().add(passwordField);
	}
}
