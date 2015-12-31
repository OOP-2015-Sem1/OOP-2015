package view.signup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import model.users.Credential;
import model.utils.StudentClass;
import model.utils.Subject;
import services.signup.TeacherFieldsGetter;

public class TeacherSignupForm extends Form {
	JPanel teacherFields = new JPanel(new GridLayout(1, 2));
	JPanel subjectsPane = new JPanel(new BorderLayout());
	JPanel classesPane = new JPanel(new BorderLayout());
	JList<String> classesList = new JList<>(StudentClass.table.getTypes());
	JList<String> subjectsList = new JList<>(Subject.table.getTypes());
	
	public TeacherSignupForm() {
		
		setSubjectPane();
		setClassPane();
		
		teacherFields.add(classesPane);
		teacherFields.add(subjectsPane);
		
		add(teacherFields,BorderLayout.SOUTH);
	}
	
	private void setSubjectPane() {
		
		subjectsList.setVisibleRowCount(4); 
		subjectsList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scroll = new JScrollPane(subjectsList);
		JLabel subjects = new JLabel("Subjects");
		
		subjectsPane.add(subjects,BorderLayout.NORTH);
		subjectsPane.add(scroll,BorderLayout.SOUTH);
	}
	
	private void setClassPane() {

		classesList.setVisibleRowCount(4); 
		classesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		
		JScrollPane scroll = new JScrollPane(classesList);
		JLabel classes = new JLabel("Classes");
		
		classesPane.add(classes,BorderLayout.NORTH);
		classesPane.add(scroll,BorderLayout.SOUTH);
	}
	
	public String[] getSelectedClasses() {
		Object[] lis = classesList.getSelectedValuesList().toArray();
		String[] classes = new String[lis.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = (String) lis[i];
		}
		return classes;
	}
	public String[] getSelectedSubjects(){
		Object[] lis = subjectsList.getSelectedValuesList().toArray();
		String[] subjects = new String[lis.length];
		for (int i = 0; i < subjects.length; i++) {
			subjects[i] = (String) lis[i];
		}
		return subjects;
	}
	
}
