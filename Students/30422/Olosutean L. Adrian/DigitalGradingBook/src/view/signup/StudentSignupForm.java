package view.signup;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.utils.StudentClass;
import services.signup.StudentFieldsGetter;


public class StudentSignupForm extends Form{
	private JLabel classLabel = new JLabel("Class");
	private JComboBox<String> classBox = new JComboBox<>(StudentClass.table.getTypes());
	
	public StudentSignupForm() {
		
		JPanel classOptionPane = new JPanel(new FlowLayout());
		classOptionPane.add(classLabel);
		classOptionPane.add(classBox);
		add(classOptionPane,BorderLayout.SOUTH);
		
	}
	
	public String getStudentClass() {
		return classBox.getSelectedItem().toString();
	}
}
