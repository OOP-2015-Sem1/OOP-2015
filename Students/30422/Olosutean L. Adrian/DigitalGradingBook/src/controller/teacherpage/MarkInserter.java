package controller.teacherpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import java.util.Date;

import controller.DatabaseController;

public interface MarkInserter extends StudentUsernameGetter{
	public default boolean isValidMark(String student,String subject,String grading) {
		String username = getStudentUsername(student);
		try{
			
			db.create.insertInto(table("Mark"))
	        .set(field("student"), username)
	        .set(field("grading"), grading)
	        .set(field("subject"), subject)
	        .set(field("date"), new Date())
	        .execute();
			
		}
		catch(Exception e){
			return false;
		}
		return true;
		}
}
