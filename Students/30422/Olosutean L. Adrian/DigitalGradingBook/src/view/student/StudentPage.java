package view.student;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import controller.studentpage.AbsenceFieldsGetter;
import controller.studentpage.MarkFieldsGetter;
import controller.studentpage.StudentSubjectsGetter;
import model.users.Student;
import view.ComponentSizer;

public class StudentPage extends JFrame implements StudentSubjectsGetter, ComponentSizer{
	
	private Student student;
	private JComboBox subjectsBox ;
	
	public StudentPage(Student student) {
		this.student = student;
		JPanel mainPanel;
		mainPanel = new JPanel(new BorderLayout());
		
		setSelectersPanel();
		setfieldsPanel();
		
		setSize(350,350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	private void setSelectersPanel() {
		JPanel selectersPanel = new JPanel();
		selectersPanel.setLayout(new BoxLayout(selectersPanel, BoxLayout.Y_AXIS));
		
		ArrayList<JComponent> selecters = new ArrayList<>();
		
		JLabel selectSubjectLabel = new JLabel("Select Subject");
		selecters.add(selectSubjectLabel);
		
		String studentUsername = student.getCredential().username;
		subjectsBox = new JComboBox<>(getStudentSubjects(studentUsername));
		selecters.add(subjectsBox);
		sizeComponents(selecters);
		
		selectersPanel.add(selectSubjectLabel);
		selectersPanel.add(subjectsBox);
		add(selectersPanel,BorderLayout.NORTH);
		
	}
	
	
	private void setfieldsPanel() {
		JPanel fieldsPanel = new JPanel(new GridLayout(1, 2));
		
		JPanel marksPanel = makeFieldsPanel("Marks");
		
		JPanel absencesPanel = makeFieldsPanel("Absences");
		
		fieldsPanel.add(marksPanel);
		fieldsPanel.add(absencesPanel);
		add(fieldsPanel);
	}
	
	private JPanel makeFieldsPanel(String fieldName) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JLabel fieldlabel = new JLabel(fieldName);
		
		JTextArea area = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(area); 
		area.setEditable(false);
		
		if (fieldName .equals("Marks")) 
			subjectsBox.addActionListener(new MarkFieldsGetter(subjectsBox, area, student.getCredential().username));
		else
			subjectsBox.addActionListener(new AbsenceFieldsGetter(subjectsBox, area, student.getCredential().username));
		
		panel.add(fieldlabel);
		panel.add(scrollPane);
		return panel;
	}
	
	
}
