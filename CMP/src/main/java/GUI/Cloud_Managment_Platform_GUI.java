package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.ClientCMP;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Cloud_Managment_Platform_GUI extends JFrame {

	private JTextField fName_add;
	private JTextField lName;
	private JTextField empNumber_add;
	private JTextField dateOfBirth;
	private JTextField hireDate;
	private JTextField empNumber_remove;
	private JTextField fName_remove;
	private JComboBox gender;
	private ClientCMP client = new ClientCMP();
	
	
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
		
		JTextArea response = new JTextArea();
		response.setBackground(new Color(248, 248, 255));
		response.setEditable(false);
		response.setLineWrap(true);
		response.setBounds(33, 192, 566, 171);
		getContentPane().add(response);
		
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
		
		/*------------------ ADD new Employee Button ------------*/
		JButton addButton = new JButton("ADD");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			}
		});
		
		
		
		
		
		addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		addButton.setForeground(new Color(152, 251, 152));
		addButton.setBackground(new Color(0, 0, 0));
		addButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		addButton.setBounds(512, 40, 117, 42);
		getContentPane().add(addButton);
		
		JButton removeButton = new JButton("REMOVE");
		removeButton.setBackground(new Color(0, 0, 0));
		removeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		removeButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		removeButton.setForeground(new Color(240, 128, 128));
		removeButton.setBounds(512, 138, 117, 42);
		getContentPane().add(removeButton);
		
		
		/*------------------------ Logout Button ----------------------*/
		JButton logoutButton = new JButton("LOGOUT");
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main_Cloud_Managment_Platform_GUI loginGUI = new Main_Cloud_Managment_Platform_GUI();
				loginGUI.main(null);
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        dispose();
			}
		});
		logoutButton.setBackground(new Color(0, 0, 0));
		logoutButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		logoutButton.setForeground(new Color(255, 215, 0));
		logoutButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		logoutButton.setBounds(512, 369, 117, 29);
		getContentPane().add(logoutButton);
		
		JButton printButton = new JButton("SHOW MY DATABASE");
		printButton.setBackground(Color.WHITE);
		printButton.setForeground(new Color(0, 0, 0));
		printButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		printButton.setBounds(10, 369, 229, 29);
		getContentPane().add(printButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(407, 105, 104, 12);
		getContentPane().add(separator);
		
		JLabel lblNewLabel_2 = new JLabel("Emp Number:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_2.setForeground(new Color(144, 238, 144));
		lblNewLabel_2.setBounds(16, 30, 81, 16);
		getContentPane().add(lblNewLabel_2);
		
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
		
		empNumber_add = new JTextField();
		empNumber_add.setBackground(new Color(248, 248, 255));
		empNumber_add.setColumns(10);
		empNumber_add.setBounds(109, 24, 130, 26);
		getContentPane().add(empNumber_add);
		
		JLabel lblNewLabel_2_1 = new JLabel("Date Of Birth:");
		lblNewLabel_2_1.setForeground(new Color(144, 238, 144));
		lblNewLabel_2_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_2_1.setBounds(285, 26, 89, 16);
		getContentPane().add(lblNewLabel_2_1);
		
		dateOfBirth = new JTextField();
		dateOfBirth.setBackground(new Color(248, 248, 255));
		dateOfBirth.setText("(yyyy-mm-dd)");
		dateOfBirth.setColumns(10);
		dateOfBirth.setBounds(378, 20, 130, 26);
		getContentPane().add(dateOfBirth);
		
		JLabel lblNewLabel_3_2 = new JLabel("Hiring Date:");
		lblNewLabel_3_2.setForeground(new Color(144, 238, 144));
		lblNewLabel_3_2.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_2.setBounds(285, 50, 81, 16);
		getContentPane().add(lblNewLabel_3_2);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("Gender:");
		lblNewLabel_3_1_1.setForeground(new Color(144, 238, 144));
		lblNewLabel_3_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_3_1_1.setBounds(285, 75, 81, 16);
		getContentPane().add(lblNewLabel_3_1_1);
		
		hireDate = new JTextField();
		hireDate.setBackground(new Color(248, 248, 255));
		hireDate.setText("(yyyy-mm-dd)");
		hireDate.setColumns(10);
		hireDate.setBounds(378, 48, 130, 26);
		getContentPane().add(hireDate);
		
		gender = new JComboBox();
		gender.setBackground(new Color(255, 255, 255));
		gender.setForeground(new Color(0, 0, 0));
		gender.setBounds(427, 74, 81, 27);
		gender.setModel(new DefaultComboBoxModel(new String[] {"","M", "F"}));
		getContentPane().add(gender);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Emp Number:");
		lblNewLabel_2_1_1.setForeground(new Color(240, 128, 128));
		lblNewLabel_2_1_1.setFont(new Font("Arial", Font.PLAIN, 13));
		lblNewLabel_2_1_1.setBounds(16, 132, 89, 16);
		getContentPane().add(lblNewLabel_2_1_1);
		
		empNumber_remove = new JTextField();
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
				empNumber_add.setText(null);
				fName_add.setText(null);
				lName.setText(null);
				dateOfBirth.setText(null);
				hireDate.setText(null);
				gender.setSelectedItem(null);
				fName_add.setText(null);
				fName_add.setText(null);
			}
		});
		clearButton.setForeground(new Color(255, 215, 0));
		clearButton.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		clearButton.setBounds(512, 93, 117, 36);
		getContentPane().add(clearButton);
	}
}
