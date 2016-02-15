package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import Brain.Main;

public class TeacherPage {
	JFrame teacherFrame;
	JButton classroomButton[] = new JButton[13];
	JPanel panelForClassroom;

	JPanel panelForGrades = new JPanel();
	int rowx = 0, columny = 0;
	String getTextFromTableCopy;
	Connection myConn = Main.getConnection();
	String initialGrades; 
	String initialAbsences; 
	String teacherSubject;
	String studentSubject;
	String StudentID;
	public TeacherPage(String teacherName) {
		int[] classArray = new int[100];

		teacherFrame = new JFrame();
		teacherFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		teacherFrame.setSize(1000, 1000);
		teacherFrame.setLayout(new BorderLayout(2, 1));

		panelForClassroom = new JPanel();
		panelForClassroom.setSize(1000, 1000);
		panelForClassroom.setLayout(new GridLayout(12, 1));
		panelForClassroom.setVisible(true);
		panelForGrades.setSize(500, 1000);
		try {
			Statement myStmt = myConn.createStatement();
			ResultSet myRs = myStmt.executeQuery(
					"select teacher.subjectid, classgrade from classroom join teacherclassroom on (teacherclassroom.classid = classroom.idclassroom) join teacher on (teacher.idteacher = teacherclassroom.teacherid)"
							+ "where teachername = '" + teacherName + "'");

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
				teacherSubject = myRs.getString("teacher.subjectid");
				classroomButton[i] = new JButton("clasa a " + myRs.getString("classgrade") + "-a");
				classArray[i] = Integer.valueOf(myRs.getString("classgrade"));
				classroomButton[i].setSize(classroomButton[i].getPreferredSize());
				classroomButton[i].setVisible(true);
				panelForClassroom.add(classroomButton[i]);
				int j = i;
				classroomButton[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						try {
							// Connection myConn =
							// DriverManager.getConnection("jdbc:mysql://localhost:3306/DigitalSchool",
							// "root", "");
							// 2/ create statement
							Statement myStmt = myConn.createStatement();
							// 3. execute sql query
							ResultSet myRs = myStmt
									.executeQuery("select student.studentname, grades.Grade, grades.Absence from "
											+ "grades join subject on (grades.subjectId = subject.idsubject) join student on (student.idstudent = grades.studentIdGrades)"
											+ "where" + "(`student`.`classid` = '" + classArray[j] + "')"
													+ "and grades.subjectId = '"+teacherSubject+"'"
															+ "order by student.studentname");
											//+ "group by student.studentname");
							ResultSetMetaData rsmt = myRs.getMetaData();
							int columnNumber = rsmt.getColumnCount();
							Vector column = new Vector(columnNumber);
							for (int i = 1; i <= columnNumber; i++) {
								column.add(rsmt.getColumnName(i));
							}
							Vector data = new Vector();
							Vector row = new Vector();
							String grade = null, absence = null;
							// 4. process result set
							while (myRs.next()) {
								row = new Vector(columnNumber);
								for (int i = 1; i <= columnNumber; i++) {
									row.add(myRs.getString(i));
									grade = myRs.getString("grades.Grade");
									absence = myRs.getString("grades.Absence");
								}
								data.add(row);
							}
							JButton saveButtonGrades = new JButton("Save Grades");
							String gradeCopy = grade;
							String absenceCopy = absence;
							JButton backButton = new JButton("back");
							backButton.setSize(backButton.getPreferredSize());
							backButton.addActionListener(new ActionListener() {

								@Override
								public void actionPerformed(ActionEvent e) {
									// TODO Auto-generated method stub
									teacherFrame.dispose();
									new TeacherPage(teacherName);
								}

							});
							JTable table = new JTable(data, column);
							JScrollPane jsp = new JScrollPane(table);
							table.addMouseListener(new MouseListener() {
								@Override
								public void mouseClicked(MouseEvent event) {
									// TODO Auto-generated method stub
									// Object getTextFromTableCopy;
									if (event.getClickCount() == 1) {
										String getTextFromTable;
										Point point = event.getPoint();
										rowx = table.rowAtPoint(point);
										columny = table.columnAtPoint(point);
										System.out.println(table.getModel().getValueAt(rowx, columny));
										getTextFromTable = (String) table.getModel().getValueAt(rowx, columny);
										getTextFromTableCopy = getTextFromTable;
										try {
											ResultSet myRs = myStmt
													.executeQuery("select studentIdGrades, grades.subjectId,grades.Grade, grades.Absence from "
															+ "grades join subject on (grades.subjectId = subject.idsubject) join student on (student.idstudent = grades.studentIdGrades)"
															+ "where" + "(`student`.`classid` = '" + classArray[j] + "')"
															+ "and student.studentname = '"+(String) table.getModel().getValueAt(rowx, 0)+"'"
															+"and grades.subjectId='"+teacherSubject+"'");
															//+ "group by student.studentname");
											while (myRs.next()){
												initialGrades = myRs.getString("grades.Grade");
												initialAbsences = myRs.getString("grades.Absence");
												studentSubject = myRs.getString("grades.subjectId");
												StudentID = myRs.getString("studentIdGrades");
											}
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
								}

								@Override
								public void mouseEntered(MouseEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void mouseExited(MouseEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void mousePressed(MouseEvent e) {
									// TODO Auto-generated method stub

								}

								@Override
								public void mouseReleased(MouseEvent e) {
									// TODO Auto-generated method stub

								}
							});
							panelForGrades.setLayout(new BorderLayout());
							panelForGrades.add(jsp, BorderLayout.CENTER);
							panelForGrades.add(backButton, BorderLayout.SOUTH);
							saveButtonGrades.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									String updateStringGrade = "Update grades join student on (student.idstudent = grades.studentIdGrades) SET "
											+ "grades.Grade='" + getTextFromTableCopy + "' " + "WHERE grades.Grade = '"
											+ initialGrades
											+ "' and grades.subjectId='"+teacherSubject+"'"
											+" and student.classid = '" + classArray[j] + "'"
											+ "and student.studentname = '"+(String) table.getModel().getValueAt(rowx, 0) + "'";
									
									System.out.println(getTextFromTableCopy+" ; "+initialGrades+" ; "+teacherSubject+" ; "+StudentID);
									try {
										int updateCount = myStmt.executeUpdate(updateStringGrade);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}

							});
							JButton saveButtonAbsences = new JButton("Save Absences");
							saveButtonAbsences.addActionListener(new ActionListener() {
								@Override
									
								public void actionPerformed(ActionEvent e) {
									String updateStringAbsence = "Update grades join student on (student.idstudent = grades.studentIdGrades) SET "
											+ "grades.Absence='" + getTextFromTableCopy + "' " + "WHERE grades.Absence = '"
											+ initialAbsences + "' and student.classid = '" + classArray[j] + "'"
											+ "and student.studentname = '"
											+ (String) table.getModel().getValueAt(rowx, 0) + "'"
													+ "and grades.subjectId='"+teacherSubject+"'";
									try {
										int updateCountSecond = myStmt.executeUpdate(updateStringAbsence);
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
								}
							});
							panelForGrades.add(saveButtonAbsences, BorderLayout.EAST);
							panelForGrades.add(saveButtonGrades, BorderLayout.WEST);
							teacherFrame.setContentPane(panelForGrades);
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
		teacherFrame.add(panelForClassroom);
		teacherFrame.add(panelForGrades);
		teacherFrame.setVisible(true);
	}
}
