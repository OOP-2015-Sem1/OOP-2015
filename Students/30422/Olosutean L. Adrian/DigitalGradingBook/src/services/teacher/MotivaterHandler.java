package services.teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import controller.teacherpage.AbsenceMotivater;
import controller.teacherpage.StudentUsernameGetter;
import model.studentfields.Absence;
import model.users.Credential;
import model.users.Student;

public class MotivaterHandler implements ActionListener,AbsenceMotivater,StudentUsernameGetter{

	JComboBox studentBox;
	JComboBox subjectBox;
	JComboBox dateBox;
	
	public MotivaterHandler(JComboBox studentBox, JComboBox subjectBox, JComboBox dateBox) {
		this.studentBox = studentBox;
		this.subjectBox = subjectBox;
		this.dateBox = dateBox;
	}
	public void actionPerformed(ActionEvent e) {
		String date = dateBox.getSelectedItem().toString();
		String studentFullName = studentBox.getSelectedItem().toString();
		String username = getStudentUsername(studentFullName);
		String subject = subjectBox.getSelectedItem().toString();
		/*
		Credential cred = new Credential();
		String studentFullName = studentBox.getSelectedItem().toString();
		cred.username = getStudentUsername(studentFullName);
		String subject = subjectBox.getSelectedItem().toString();
		Student student = new Student(cred);
		Absence absence = new Absence(subject, date, student);
		motivateAbsence(absence);
		*/
		Credential cred = new Credential();
		cred.username = username;
		Student student = new Student(cred);
		Absence absence = new Absence(subject, date, student);
		motivateAbsence(absence);
		
		
	}

}
