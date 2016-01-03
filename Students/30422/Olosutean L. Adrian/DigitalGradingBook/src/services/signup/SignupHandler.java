package services.signup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.signup.StudentInserter;
import controller.signup.TeacherInserter;
import controller.signup.UserFinder;
import model.users.Credential;
import view.signup.SignUpPage;

public class SignupHandler implements ActionListener,UserFinder,StudentInserter,TeacherInserter{
	
	SignUpPage page;
	Credential creds;
	
	public SignupHandler(SignUpPage page) {
		this.page = page;
	}
	
	public void actionPerformed(ActionEvent e) {
		Authenthication_I page = this.page;
		creds = page.getCredential();
		String userType = page.getUserType();
		
		if (userIsFound(creds.username, userType)) {
			page.setState("User is already taken");
		}
		else 
		{
			if (userType .equals("Student"))
				registerStudent();
				
			else if(userType .equals("Teacher"))
				registerTeacher();
				
			
		}
		
	}
	
	public void registerStudent(){
		page.setState("Student registered");
		StudentFieldsGetter getter = this.page;
		String clas = getter.getStudentClass();
		
		insertStudent(creds, clas);
	}
	
	
	public void registerTeacher(){
		page.setState("Teacher Registered");
		TeacherFieldsGetter getter = this.page;
		String[] classes = getter.getClasses();
		String[] subjects = getter.getSubjects();
		
		insertTeacher(creds, classes, subjects);
	}
			
}
