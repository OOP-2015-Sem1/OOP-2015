package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class TypeOfLogIn {
	JFrame loginFrame;
	JButton butonForStudent;
	JButton butonForTeacher;

	public TypeOfLogIn(){
		
	loginFrame= new JFrame();
	loginFrame.setLayout(null);
	loginFrame.setSize(1000, 1000);
	butonForStudent = new JButton("Log in as a student");
	butonForStudent.setSize(butonForStudent.getPreferredSize());
	butonForStudent.setLocation(450, 400);
	butonForStudent.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new LogIn("student");
			loginFrame.dispose();
		}
		
	});
	butonForStudent.setVisible(true);
	loginFrame.add(butonForStudent);
	
	
	butonForTeacher = new JButton("Log in as a teacher");
	butonForTeacher.setSize(butonForTeacher.getPreferredSize());
	butonForTeacher.setLocation(450, 500);
	butonForTeacher.addActionListener(new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			new LogIn("teacher");
			loginFrame.dispose();
		}
		
	});
	butonForTeacher.setVisible(true);
	loginFrame.add(butonForTeacher);
	loginFrame.setVisible(true);
	}
}
