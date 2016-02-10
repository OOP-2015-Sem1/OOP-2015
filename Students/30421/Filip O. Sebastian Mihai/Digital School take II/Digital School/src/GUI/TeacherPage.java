package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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

import Brain.main;

public class TeacherPage {
	JFrame teacherFrame;
	JButton buton[]= new JButton[13];
	JPanel panel;

	JPanel panel1 = new JPanel();
	Connection myConn= main.getConnection();
	public TeacherPage(String s){
		int[] classArray = new int[100];
		
		teacherFrame = new JFrame();
		teacherFrame.setSize(1000,1000);
		teacherFrame.setLayout(new BorderLayout(2,1));
		
		panel = new JPanel();
		panel.setSize(1000,1000);
		panel.setLayout(new GridLayout(12,1));
		panel.setVisible(true);
		panel1.setSize(500,1000);
		//panel1.setBackground(Color.black);
		int n = 0;
		try {
			//Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
			// 2/ create statement
			Statement myStmt = myConn.createStatement();
			// 3. execute sql query
			ResultSet myRs = myStmt.executeQuery("select classgrade from classroom join teacherclassroom on (teacherclassroom.classid = classroom.idclassroom) join teacher on (teacher.idteacher = teacherclassroom.teacherid)"
					+ "where teachername = '"+s+"'");
		
			ResultSetMetaData rsmt = myRs.getMetaData();
			int columnNumber = rsmt.getColumnCount();
			Vector column = new Vector(columnNumber);
			for (int i = 1; i <= columnNumber; i++) {
				column.add(rsmt.getColumnName(i));
			}
			Vector data = new Vector();
			Vector row = new Vector();
			int i = 0;
			// 4. process result set
			while (myRs.next()) {
					buton[i]=new JButton("clasa a "+myRs.getString("classgrade")+"-a");
					classArray[i]=Integer.valueOf(myRs.getString("classgrade"));
					buton[i].setSize(buton[i].getPreferredSize());
					buton[i].setVisible(true);
					panel.add(buton[i]);
					int j=i;
					buton[i].addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							try {
								//Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
								// 2/ create statement
								Statement myStmt = myConn.createStatement();
								// 3. execute sql query
								ResultSet myRs = myStmt.executeQuery("select student.studentname, grades.Grade, grades.Absence from "
										+ "grades join subject on (grades.subjectId = subject.idsubject) join student on (student.idstudent = grades.studentIdGrades)"
										+ "where"
								    	+"(`student`.`classid` = '"+classArray[j]+"')"
										+"group by student.studentname");
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
								JButton back1 = new JButton("SAVE");
								JButton back = new JButton("back");
								back.setSize(back.getPreferredSize());
								back.addActionListener(new ActionListener(){

									@Override
									public void actionPerformed(ActionEvent e) {
										// TODO Auto-generated method stub
										teacherFrame.dispose();
										new TeacherPage(s);
									}
									
								});
								JTable table = new JTable(data, column);
								JScrollPane jsp = new JScrollPane(table);
								panel1.setLayout(new BorderLayout());
								panel1.add(jsp, BorderLayout.CENTER);
								panel1.add(back, BorderLayout.SOUTH);
								panel1.add(back1, BorderLayout.NORTH);
								teacherFrame.setContentPane(panel1);
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
						
					});
					i++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		teacherFrame.add(panel);
		teacherFrame.add(panel1);
		teacherFrame.setVisible(true);
	}
}
