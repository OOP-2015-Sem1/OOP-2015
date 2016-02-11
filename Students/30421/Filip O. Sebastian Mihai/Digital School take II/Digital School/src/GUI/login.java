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

import Brain.main;

public class LogIn {
	public JFrame loginPanel;
	JTextField field;
	JPasswordField passwordfield;
	JLabel label;
	JButton buton;

	Connection myConn = main.getConnection();
	String typeOfUser2 = new String();

	public LogIn(String typeOfUser) {
		typeOfUser2 = typeOfUser;
		loginPanel = new JFrame();
		loginPanel.setSize(1000, 1000);
		loginPanel.setBackground(Color.white);
		loginPanel.setLayout(null);

		label = new JLabel("Enter Name");
		label.setLocation(280, 300);
		label.setSize(label.getPreferredSize());
		loginPanel.add(label);

		field = new JTextField();
		field.setColumns(25);
		field.setSize(field.getPreferredSize());
		field.setToolTipText("Enter Name");
		field.setLocation(400, 300);
		loginPanel.add(field);
		field.setVisible(true);

		label = new JLabel("Enter Password");
		label.setLocation(280, 400);
		label.setSize(label.getPreferredSize());
		label.setVisible(true);
		loginPanel.add(label);

		passwordfield = new JPasswordField();
		passwordfield.setColumns(25);
		passwordfield.setSize(passwordfield.getPreferredSize());
		passwordfield.setToolTipText("Enter Password");
		passwordfield.setLocation(400, 400);
		loginPanel.add(passwordfield);
		passwordfield.setVisible(true);

		buton = new JButton("Log In");
		buton.setSize(buton.getPreferredSize());
		buton.setLocation(500, 450);
		buton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (field.getText().length() == 0) // Checking for empty field
					JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
				else if (passwordfield.getPassword().length == 0) // Checking
																	// for empty
																	// field
					JOptionPane.showMessageDialog(null, "Empty fields detected ! Please fill up all fields");
				else {
					String user = field.getText(); // Collecting the input
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
		loginPanel.add(buton);
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
