package controller.teacherpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import java.util.Date;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import controller.DatabaseController;

public interface AbsenceInserter extends StudentUsernameGetter{
	public default boolean isValidAbsence(String student,String subject) {
	String username = getStudentUsername(student);
	try{
		
		db.create.insertInto(table("Absence"))
        .set(field("student"), username)
        .set(field("subject"), subject)
        .set(field("date"), new Date())
        .set(field("motivated"), 0)
        .execute();
		
	}
	catch(Exception e){
		return false;
	}
	return true;
	}

}
