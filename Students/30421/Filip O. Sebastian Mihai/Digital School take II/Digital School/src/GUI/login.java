package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Brain.Main;

public class LogIn {
	public JFrame loginPanel;
	JTextField fieldForEnteringData;
	JPasswordField passwordfield;
	JLabel labelForData;
	JButton logInButton;

	Connection myConn = Main.getConnection();
	String typeOfUser2 = new String();

	public LogIn(String typeOfUser) {
		typeOfUser2 = typeOfUser;
		loginPanel = new JFrame();
		loginPanel.setSize(1000, 1000);
		loginPanel.setBackground(Color.white);
		loginPanel.setLayout(null);

		labelForData = new JLabel("Enter Name");
		labelForData.setLocation(280, 300);
		labelForData.setSize(labelForData.getPreferredSize());
		loginPanel.add(labelForData);

		fieldForEnteringData = new JTextField();
		fieldForEnteringData.setColumns(25);
		fieldForEnteringData.setSize(fieldForEnteringData.getPreferredSize());
		fieldForEnteringData.setToolTipText("Enter Name");
		fieldForEnteringData.setLocation(400, 300);
		loginPanel.add(fieldForEnteringData);
		fieldForEnteringData.setVisible(true);

		labelForData = new JLabel("Enter Password");
		labelForData.setLocation(280, 400);
		labelForData.setSize(labelForData.getPreferredSize());
		labelForData.setVisible(true);
		loginPanel.add(labelForData);

		passwordfield = new JPasswordField();
		passwordfield.setColumns(25);
		passwordfield.setSize(passwordfield.getPreferredSize());
		passwordfield.setToolTipText("Enter Password");
		passwordfield.setLocation(400, 400);
		loginPanel.add(passwordfield);
		passwordfield.setVisible(true);

		logInButton = new JButton("Log In");
		logInButton.setSize(logInButton.getPreferredSize());
		logInButton.setLocation(500, 450);
		logInButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (fieldForEnteringData.getText().length() == 0) // Checking for empty field
					JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
				else if (passwordfield.getPassword().length == 0) // Checking
																	// for empty
																	// field
					JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
				else {
					String user = fieldForEnteringData.getText(); // Collecting the input
					char[] pass = passwordfield.getPassword(); // Collecting the
																// input
					String pwd = String.copyValueOf(pass); // converting from
															// array to string
					if (validate_login(user, pwd)) {
						JOptionPane.showMessageDialog(null, "Correct Login Credentials");
						loginPanel.dispose();
					} else
						JOptionPane.showMessageDialog(null, "Incorrect Login Credentials");
				}
			}
		});
		loginPanel.add(logInButton);
		loginPanel.setVisible(true);
	}

	private boolean validate_login(String username, String password) {
		try {
			// 1. get a connection to db
			// Connection myConn =
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool",
			// "root", "");
			PreparedStatement pst= myConn.prepareStatement("Select * from student where studentname=? and studentpassword=?");;
			if (typeOfUser2 == "student")
				pst = myConn.prepareStatement("Select * from student where studentname=? and studentpassword=?");
			if (typeOfUser2 == "teacher")
				pst = myConn.prepareStatement("Select * from teacher where teachername=? and teacherpassword=?");
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				if (typeOfUser2 == "student")
					new StudentPage(username);
				if (typeOfUser2 == "teacher")
					new TeacherPage(username);
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
