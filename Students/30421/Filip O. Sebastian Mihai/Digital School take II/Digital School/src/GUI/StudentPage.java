package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import Brain.Main;

public class StudentPage {
	JFrame studFrame;
	JTextArea textArea;
	Connection myConn= Main.getConnection();
	public StudentPage(String studentName) {
		studFrame = new JFrame();
		studFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		studFrame.setSize(1000, 1000);
		studFrame.setLayout(null);
		textArea = new JTextArea();
		textArea.setSize(800, 800);
		// 1. get a connection to db
		try {
			//Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
			// 2/ create statement
			Statement myStmt = myConn.createStatement();
			// 3. execute sql query
			ResultSet myRs = myStmt.executeQuery("select subject.subjectname, grades.Grade, grades.Absence from "
					+ "grades join subject on (grades.subjectId = subject.idsubject) join student on (student.idstudent = grades.studentIdGrades)"
					+ "where student.studentname = '"+studentName+"'");
			
			
			
			
			ResultSetMetaData rsmt = myRs.getMetaData();
			int columnNumber = rsmt.getColumnCount();
			Vector column = new Vector(columnNumber);
			for (int i = 1; i <= columnNumber; i++) {
				column.add(rsmt.getColumnName(i));
			}
			Vector data = new Vector();
			Vector row = new Vector();
			// 4. process result set
			while (myRs.next()) {
				row = new Vector(columnNumber);
				for (int i = 1; i <= columnNumber; i++) {
					row.add(myRs.getString(i));
				}
				data.add(row);
			}
			JButton back = new JButton("back");
			back.setSize(back.getPreferredSize());
			back.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					studFrame.dispose();
					new TypeOfLogIn();
				}
				
			});
			JPanel panel = new JPanel();
			JTable table = new JTable(data, column);
			JScrollPane jsp = new JScrollPane(table);
			panel.setLayout(new BorderLayout());
			panel.add(jsp, BorderLayout.CENTER);
			panel.add(back, BorderLayout.SOUTH);
			studFrame.setContentPane(panel);
			studFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		studFrame.add(textArea);

		studFrame.setVisible(true);
	}
}
