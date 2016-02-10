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

public class TeacherPage {
	JFrame teacherFrame;
	JButton buton[]= new JButton[13];
	JPanel panel;

	JPanel panel1 = new JPanel();
	public TeacherPage(String s){
		int[] classArray = new int[100];
		
		teacherFrame = new JFrame();
		teacherFrame.setSize(1000,1000);
		teacherFrame.setLayout(new BorderLayout(2,1));
		
		panel = new JPanel();
		panel.setSize(500,1000);
		panel.setLayout(new GridLayout(12,1));
		panel.setVisible(true);
		panel1.setSize(500,1000);
		//panel1.setBackground(Color.black);
		int n = 0;
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
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
								Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
								// 2/ create statement
								Statement myStmt = myConn.createStatement();
								// 3. execute sql query
								ResultSet myRs = myStmt.executeQuery("SELECT"
										+"`student`.`studentname` AS `studentname`,"
								        +"`matematica`.`notematematica` AS `notematematica`,"
								        +"`biologie`.`notebiologie` AS `notebiologie`,"
								        +"`chimie`.`notechimie` AS `notechimie`,"
								        +"`educatiefizica`.`noteeducatiefizica` AS `noteeducatiefizica`,"
								        +"`fizica`.`notefizica` AS `notefizica`,"
								        +"`geografie`.`notegeografie` AS `notegeografie`,"
								        +"`istorie`.`noteistorie` AS `noteistorie`,"
								        +"`psihologie`.`notepsihologie` AS `notepsihologie`,"
								        +"`romana`.`noteromana` AS `noteromana`"
								    +"FROM"
								        +"(((((((((`student`"
								        +"JOIN `matematica` ON ((`student`.`idstudent` = `matematica`.`idstudent`)))"
								        +"JOIN `biologie` ON ((`student`.`idstudent` = `biologie`.`studentidbiologie`)))"
								        +"JOIN `chimie` ON ((`student`.`idstudent` = `chimie`.`studentidchimie`)))"
								        +"JOIN `educatiefizica` ON ((`student`.`idstudent` = `educatiefizica`.`studentideducatiefizica`)))"
								        +"JOIN `fizica` ON ((`student`.`idstudent` = `fizica`.`studentidfizica`)))"
								        +"JOIN `geografie` ON ((`student`.`idstudent` = `geografie`.`studentidgeografie`)))"
								        +"JOIN `istorie` ON ((`student`.`idstudent` = `istorie`.`studentidistorie`)))"
								        +"JOIN `psihologie` ON ((`student`.`idstudent` = `psihologie`.`studentidpsihologie`)))"
								        +"JOIN `romana` ON ((`student`.`idstudent` = `romana`.`studentid`)))"

								    	+"WHERE"
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
