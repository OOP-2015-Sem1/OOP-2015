package services.teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.teacherpage.AbsenceInserter;

public class AbsenceValidater implements ActionListener,AbsenceInserter{

	JComboBox studentBox;
	JComboBox subjectBox;
	JLabel validAbsence;
	
	public AbsenceValidater(JComboBox studentBox,JComboBox subjectBox, JLabel validAbsence) {
		this.studentBox = studentBox;
		this.subjectBox = subjectBox;
		this.validAbsence = validAbsence;
	}
	public void actionPerformed(ActionEvent e) {
		String student = studentBox.getSelectedItem().toString();
		String subject = subjectBox.getSelectedItem().toString();
		if(isValidAbsence(student, subject))
			validAbsence.setText("Absence submitted");
		else
			validAbsence.setText("Absence already exists");
			
	}

}
