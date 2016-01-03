package services.student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import controller.DatabaseController;

public abstract class StudentFieldsGetter implements ActionListener,DatabaseController{
	
	protected JComboBox subjectBox;
	protected JTextArea textArea;
	protected String student;
	
	public StudentFieldsGetter(JComboBox subjectBox, JTextArea textArea,String student) {
		this.subjectBox = subjectBox;
		this.textArea = textArea;
		this.student = student;
	}
	
	public void actionPerformed(ActionEvent e) {
		String[] fields = getFields();
		String textToDisplay = "";
		for (int i = 0; i < fields.length; i++) {
			textToDisplay += fields[i] + "\n";
		}
		textArea.setText(textToDisplay);
		
	}
	
	public abstract String[] getFields();
}
