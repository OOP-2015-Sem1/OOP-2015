package view.signup;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.users.Credential;
import services.signup.Authenthication_I;
import services.signup.SignupHandler;
import services.signup.StudentFieldsGetter;
import services.signup.TeacherFieldsGetter;
import view.MainPage;

public class SignUpPage extends JPanel implements Authenthication_I, StudentFieldsGetter,TeacherFieldsGetter{
	
	private JComboBox userType = new JComboBox(new String[]{"Teacher","Student"});
	private CardLayout cards = new CardLayout();
	private JPanel mainSignupPanel = new JPanel(new BorderLayout());
	private JLabel signupState = new JLabel("");
	
	private StudentSignupForm studentForm = new StudentSignupForm();
	private TeacherSignupForm teacherForm = new TeacherSignupForm();
	
	private JPanel formPane = new JPanel();
	private JButton backToLogin = new JButton("Back to login");
	
	public SignUpPage() {
		setTopPane();
		setFormPane();
		setBottomPane();
		
		add(mainSignupPanel);
		
	}
	
	private void setTopPane() {
		JLabel select = new JLabel("I am a ");
		
		JPanel topPane = new JPanel(new FlowLayout());
		topPane.add(select);
		topPane.add(userType);
		
		userType.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				cards.show(formPane, userType.getSelectedItem().toString());
			}
		});
		mainSignupPanel.add(topPane,BorderLayout.NORTH);
		
	}
	
	private void setFormPane() {
		formPane.setLayout(cards);
		
		formPane.add(studentForm, "Student");
		formPane.add(teacherForm, "Teacher");
		cards.show(formPane, "Teacher");
		mainSignupPanel.add(formPane);
		
	}

	private void setBottomPane(){
		JPanel bottom = new JPanel(new BorderLayout());
		JButton register = new JButton("Register");
		register.addActionListener(new SignupHandler(this));
		
		bottom.add(signupState,BorderLayout.NORTH);

		bottom.add(register,BorderLayout.CENTER);

		bottom.add(backToLogin, BorderLayout.SOUTH);
		backToLogin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				MainPage.cards.show(MainPage.mainPanel, "LoginPage");
			}
		});
		mainSignupPanel.add(bottom,BorderLayout.SOUTH);
	}	
	
	public String getUserType() {
		return userType.getSelectedItem().toString();
	}

	public Credential getCredential() {
		if (getUserType() .equals("Student")) 
			return studentForm.getCredential();
		else
			return teacherForm.getCredential();
	}
	
	public void setState(String state) {
		signupState.setText(state);
	}

	public String[] getClasses() {
		return teacherForm.getSelectedClasses();
	}

	public String[] getSubjects() {
		return teacherForm.getSelectedSubjects();
	}

	
	public String getStudentClass() {
		return studentForm.getStudentClass();
	}

}
