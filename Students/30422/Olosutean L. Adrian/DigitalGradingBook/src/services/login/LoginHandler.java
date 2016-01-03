package services.login;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import java.awt.event.ActionEvent;
import model.users.*;
import model.utils.StudentClass;
import model.utils.Subject;

import java.awt.event.ActionListener;

import org.jooq.Record;
import org.jooq.Result;

import view.*;
import view.student.StudentPage;
import view.teacher.TeacherPage;
import controller.login.LoginDatabaseController;
import services.signup.Authenthication_I;
import services.signup.TeacherFieldsGetter;

public class LoginHandler implements ActionListener , LoginDatabaseController{
	
	private Authenthication_I page;
	
	public LoginHandler(Authenthication_I loginPage) {
		this.page = loginPage;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		String userType = page.getUserType();
		Credential credential = page.getCredential();
		boolean loginState = false;
		try{
			if (userType.equals("Student") && isValidStudent(credential)){
					initiateStudentPage(credential);
			}
				
			else if(userType.equals("Teacher") && isValidTeacher(credential)){
					initiateTeacherPage(credential);
					
			}
			else{
				page.setState("Invalid username or password");
			}
			
		}
		catch(Exception exception){
			exception.printStackTrace();
			//page.setState("Invalid username or password");
		}
		
	}
	
	public void initiateStudentPage(Credential credential) {
		Student student = new Student(credential);
		StudentPage spage = new StudentPage(student);
	}
	
	public void initiateTeacherPage(Credential credential) {
		Teacher teacher = new Teacher(credential);
		TeacherPage tpage = new TeacherPage(teacher);
	}
	
}
