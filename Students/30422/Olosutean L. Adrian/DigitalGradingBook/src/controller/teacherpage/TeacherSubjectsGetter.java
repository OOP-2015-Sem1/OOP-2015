package controller.teacherpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import controller.DatabaseController;

public interface TeacherSubjectsGetter extends DatabaseController{
	public default String[] getSubjects(String username){
		String[] result = db.create.select()
			.from(table("Staff"))
			.where(field("teacher_username").equal(inline(username)))
			.fetchArray(field("subject_name"),String.class);
		
		return result;
	}
}
