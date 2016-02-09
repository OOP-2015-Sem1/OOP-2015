package GUI;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

public class StudentPage {
	JFrame studFrame;
	JTextArea textarea;

	public StudentPage(String s) {
		studFrame = new JFrame();
		studFrame.setSize(1000, 1000);
		studFrame.setLayout(null);
		textarea = new JTextArea();
		textarea.setSize(800, 800);
		// 1. get a connection to db
		try {
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool", "root", "");
			// 2/ create statement
			Statement myStmt = myConn.createStatement();
			// 3. execute sql query
			ResultSet myRs = myStmt.executeQuery("SELECT" 
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
			        +"(`student`.`studentname` = '"+s+"')");
			
			
			
			
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
			JPanel panel = new JPanel();
			JTable table = new JTable(data, column);
			JScrollPane jsp = new JScrollPane(table);
			panel.setLayout(new BorderLayout());
			panel.add(jsp, BorderLayout.CENTER);
			studFrame.setContentPane(panel);
			studFrame.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
		studFrame.add(textarea);

		studFrame.setVisible(true);
	}
}
