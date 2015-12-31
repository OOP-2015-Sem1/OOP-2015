package services.teacher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.teacherpage.MarkInserter;

public class MarkValidater implements ActionListener,MarkInserter{

	JComboBox studentBox;
	JComboBox subjectBox;
	JComboBox markBox;
	JLabel validMark;
	
	public MarkValidater(JComboBox studentBox,JComboBox subjectBox,JComboBox markBox, JLabel validMark) {
		this.studentBox = studentBox;
		this.subjectBox = subjectBox;
		this.validMark = validMark;
		this.markBox = markBox;
	}
	public void actionPerformed(ActionEvent e) {
		String student = studentBox.getSelectedItem().toString();
		String subject = subjectBox.getSelectedItem().toString();
		String grading = markBox.getSelectedItem().toString();
		if(isValidMark(student, subject,grading))
			validMark.setText("Mark submitted");
		else
			validMark.setText("Mark already exists");
			
	}

}
