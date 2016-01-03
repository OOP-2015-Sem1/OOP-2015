package controller.teacherpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;

import controller.DatabaseController;

public interface StudentUsernameGetter extends DatabaseController{
	public default String getStudentUsername(String studentFullName) {
		String[] fullName = studentFullName.split(" ");
		String firstName = fullName[0];
		String lastName = fullName[1];
		
		String sql = db.create.select(field("username"))
    			.from(table("Student"))
    			.where(field("firstName").equal(inline(firstName)))
    			.and(field("lastName").equal(inline(lastName)))
    			.getSQL();
		Record row = db.create.fetchOne(sql);
		
    	return (String) row.getValue("username");
	}
}
