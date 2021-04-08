package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Cursor;
import java.awt.Color;
import javax.swing.JPasswordField;

public class Cloud_Managment_Platform_app {

	private JFrame frame;
	private JTextField LoginText;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cloud_Managment_Platform_app window = new Cloud_Managment_Platform_app();
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
	public Cloud_Managment_Platform_app() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setForeground(Color.WHITE);
		frame.setBackground(new Color(0, 0, 51));
		frame.getContentPane().setBackground(new Color(51, 102, 204));
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//----------------------------- Login button ----------------------------
		JButton LoginButton = new JButton("Login");
		LoginButton.setBackground(Color.LIGHT_GRAY);
		LoginButton.setForeground(Color.BLACK);
		LoginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); //when hover the "Login" button, it changes the cursor to a hand cursor
		
		//When clicked on the "Login" button...
		LoginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(LoginButton, "Hello");
			}
		});
		
		
		LoginButton.setBounds(123, 161, 97, 29);
		frame.getContentPane().add(LoginButton);
		
		JLabel lblNewLabel = new JLabel("Login:");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(85, 84, 61, 16);
		frame.getContentPane().add(lblNewLabel);
		
		LoginText = new JTextField();
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
		ClearButton.setForeground(Color.BLACK);
		ClearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		ClearButton.setBounds(221, 161, 81, 29);
		frame.getContentPane().add(ClearButton);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(172, 111, 130, 26);
		frame.getContentPane().add(passwordField);
	}
}
