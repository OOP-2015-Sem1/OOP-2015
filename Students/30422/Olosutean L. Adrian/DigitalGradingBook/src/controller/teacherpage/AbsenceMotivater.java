package controller.teacherpage;

import controller.DatabaseController;
import model.studentfields.Absence;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;

public interface AbsenceMotivater extends DatabaseController{
	public default void motivateAbsence(Absence absence) {
		String date = absence.getDate();
		String student = absence.getStudent().getCredential().username;
		String subject = absence.getSubject();

		Record row = db.create.select(field("absence_id"))
			.from(table("Absence"))
			.where(field("student").equal(inline(student)))
			.and(field("date").equal(inline(date)))
			.and(field("subject").equal(inline(subject)))
			.fetchOne();
		
		int id = (int) row.getValue("absence_id");
		
		update(id);	  		
	}
	
	public default void update(int id){
		
		db.create.update(table("Absence"))
		  .set(field("motivated"), 1)
		  .where(field("absence_id").equal(id))
		  .execute();
		
	}
}
