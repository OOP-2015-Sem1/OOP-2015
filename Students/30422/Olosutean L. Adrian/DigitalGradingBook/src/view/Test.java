package view;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import controller.login.LoginDatabaseController;
import controller.teacherpage.AbsenceMotivater;
import controller.teacherpage.AbsencesGetter;
import controller.teacherpage.StudentUsernameGetter;
import model.users.Credential;

public class Test implements StudentUsernameGetter{
	
	
	public Test() {
		String fullname = "Andreea Pop";
		String username = getStudentUsername(fullname);
		System.out.println(username);
	}
	public static void main(String[] args) {
		new Test();
		
		
	}
}
