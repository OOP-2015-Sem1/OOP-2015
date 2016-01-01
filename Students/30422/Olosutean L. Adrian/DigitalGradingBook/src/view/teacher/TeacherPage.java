package view.teacher;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import controller.teacherpage.ClassesSetter;
import controller.teacherpage.StudentUsernameGetter;
import controller.teacherpage.AbsencesGetter;
import controller.teacherpage.StudentsSetter;
import controller.teacherpage.TeacherSubjectsGetter;
import model.users.Teacher;
import services.teacher.AbsenceValidater;
import services.teacher.MarkValidater;
import services.teacher.MotivaterHandler;
import view.ComponentSizer;

public class TeacherPage extends JFrame implements TeacherSubjectsGetter,AbsencesGetter,StudentUsernameGetter,ComponentSizer{
	private Teacher teacher;
	private JComboBox subjectsBox ;
	private JComboBox classesBox  = new JComboBox<>();
	private JComboBox studentsBox = new JComboBox<>();
	private JComboBox actionTypeBox = new JComboBox<>(new String[]{"Add absence","Add mark", "Motivate absence"});
	private JPanel actionPane = new JPanel();
	private JComboBox unmotivatedAbsencesBox = new JComboBox<>();
	
	public TeacherPage(Teacher teacher) {
		super("Teacher Page");
		this.teacher = teacher;
		setLayout(new BorderLayout());
		addSelectersPanel();
		addActionPanel();
		
		setSize(350,350);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void addSelectersPanel() {
		
		JPanel selectersPanel = new JPanel();
		selectersPanel.setLayout(new BoxLayout(selectersPanel, BoxLayout.Y_AXIS));
		
		subjectsBox = new JComboBox<>(getSubjects(teacher.getCredential().username));
		DefaultComboBoxModel model = new DefaultComboBoxModel(new String[]{""} );
		classesBox.setModel(model);
		studentsBox.setModel(model);
		
		ArrayList<JComponent> boxes = sizeBoxes();
		ArrayList<JComponent> labels = sizeLabels();
		
		Iterator<JComponent> labelsIterator = labels.iterator();
		Iterator<JComponent> boxesIterator =  boxes.iterator();
		
		while (labelsIterator.hasNext() && boxesIterator.hasNext()) {
			selectersPanel.add(labelsIterator.next());
			selectersPanel.add(boxesIterator.next());
			selectersPanel.add(Box.createVerticalStrut(10));
		}
		
		add(selectersPanel,BorderLayout.CENTER);
		
	}
	private ArrayList<JComponent> sizeLabels() {
		ArrayList<JComponent> components = new ArrayList<>();
		components.add(new JLabel("Select subject"));
		components.add(new JLabel("Select class"));
		components.add(new JLabel("Select student"));
		components.add(new JLabel("Select action"));
		sizeComponents(components);
		return components;
	}
	
	private ArrayList<JComponent> sizeBoxes() {
		ArrayList<JComponent> components = new ArrayList<>();
		components.add(subjectsBox);
		components.add(classesBox);
		components.add(studentsBox);
		components.add(actionTypeBox);
		addBoxesActionListeners();
		sizeComponents(components);
		return components;
	}
	
	private void addBoxesActionListeners() {
		subjectsBox.addActionListener(new ClassesSetter(subjectsBox, classesBox, teacher.getCredential().username));
		classesBox.addActionListener(new StudentsSetter(classesBox, studentsBox));
		actionTypeBox.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String actionType = actionTypeBox.getSelectedItem().toString();
				if (actionType .equals("Motivate absence")) {
					setUnMotivatedAbsencesBox();
				}
				CardLayout cards = (CardLayout) actionPane.getLayout();
				cards.show(actionPane, actionType);
			}
		});
		
	}
	
	private void addActionPanel() {
		actionPane.setLayout(new CardLayout());
		
		JPanel absenceMotivationPanel = makeAbsenceMotivationPanel();
		JPanel absenceAdderPanel = makeAbsenceAdderPanel();
		JPanel markAdderPanel = makeMarkAdderPanel();
		
		actionPane.add(new JPanel(),"Empty");
		actionPane.add(absenceMotivationPanel, "Motivate absence");
		actionPane.add(absenceAdderPanel, "Add absence");
		actionPane.add(markAdderPanel, "Add mark");
		
		CardLayout cards = (CardLayout) actionPane.getLayout();
		cards.show(actionPane, "Empty");
		add(actionPane,BorderLayout.SOUTH);
	}
	
	private JPanel makeAbsenceMotivationPanel() {
		JPanel absenceMotivationPanel = new JPanel(new BorderLayout());
		
		JButton motivateButton = new JButton("Motivate");
		motivateButton.addActionListener(new MotivaterHandler(studentsBox,subjectsBox,unmotivatedAbsencesBox));
		
		absenceMotivationPanel.add(unmotivatedAbsencesBox,BorderLayout.CENTER);
		absenceMotivationPanel.add(motivateButton, BorderLayout.SOUTH);
		
		return absenceMotivationPanel;
		
	}
	
	private void setUnMotivatedAbsencesBox() {
		String studentFullName = (String) studentsBox.getSelectedItem();
		String studentUserName = getStudentUsername(studentFullName);
		String subject = (String) subjectsBox.getSelectedItem();
		
		String[] absences = getUnmotivatedAbsences(studentUserName, subject);
		DefaultComboBoxModel model = new DefaultComboBoxModel(absences);
		unmotivatedAbsencesBox.setModel(model);
	}

	private JPanel makeAbsenceAdderPanel() {
		JPanel absenceAdderPanel = setAdderPanel("Add absence", null);
		return absenceAdderPanel;
	}
	private JPanel makeMarkAdderPanel() {
		JPanel markAdderPanel;
		JComboBox markBox = new JComboBox(new String[]{"1","2","3","4","5","6","7","8","9","10"});
		markAdderPanel = setAdderPanel("Add mark",markBox);
		return markAdderPanel;
	}
	
	private JPanel setAdderPanel(String adderButtonText,JComboBox additionalBox) {
		JPanel adderPanel = new JPanel(new BorderLayout());
		JButton addButton = new JButton(adderButtonText);
		addButton.setMaximumSize(new Dimension(150,20));
		JLabel validValue = new JLabel("");
		
		if (adderButtonText .equals("Add absence")) {
			addButton.addActionListener(new AbsenceValidater(studentsBox,subjectsBox,validValue));
		}
		else if (adderButtonText .equals("Add mark")){
			adderPanel.add(additionalBox,BorderLayout.NORTH);
			addButton.addActionListener(new MarkValidater(studentsBox,subjectsBox,additionalBox,validValue));
		}
		
		adderPanel.add(addButton, BorderLayout.CENTER);
		adderPanel.add(validValue, BorderLayout.SOUTH);
		return adderPanel;
	}
	
}
