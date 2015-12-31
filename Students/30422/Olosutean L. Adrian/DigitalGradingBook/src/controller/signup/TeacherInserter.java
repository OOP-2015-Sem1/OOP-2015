package controller.signup;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import org.jooq.InsertValuesStep2;
import org.jooq.Record;

import controller.DatabaseController;
import model.users.Credential;

public interface TeacherInserter extends ClassIdGetter{
	
	public default void insertTeacher(Credential cred,String[] classes, String[] subjects) {
		db.create.insertInto(table("Teacher"),
				field("username"),
				field("password"),
				field("firstName"),
				field("lastName"),
				field("phoneNumber"),
				field("email"))
			.values(cred.username,
					cred.password,
					cred.firstName,
					cred.lastName,
					cred.phoneNumber,
					cred.email )
			.execute();
		
		insertCourse(cred.username, classes);
		insertStaff(cred.username,subjects);
	}
	
	public default void insertCourse(String username, String[] classes){
		
		for (int i = 0; i < classes.length; i++) {
			db.create.insertInto(table("Course"),
					field("teacher"),
					field("class_id"))
				.values(username,getClassId(classes[i]))
				.execute();
		}
	}
	
	 public default void insertStaff(String username,String[] subjects) {
		
		for (int i = 0; i < subjects.length; i++) {
			db.create.insertInto(table("Staff"),
					field("teacher_username"),
					field("subject_name"))
				.values(username, subjects[i])
				.execute();
		}
	}
}
