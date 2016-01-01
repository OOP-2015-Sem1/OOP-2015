package controller.studentpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import static org.jooq.impl.DSL.inline;
import controller.DatabaseController;

public interface StudentSubjectsGetter extends DatabaseController{
	public default String[] getStudentSubjects(String student){
		Result<Record1<Object>> result = db.create.select(field("SubjectSpecialization.subject"))
			.from(table("Student"))
			.naturalJoin(table("Class"))
			.naturalJoin(table("Specialization"))
			.naturalJoin(table("SubjectSpecialization"))
			.where(field("Student.username").equal(inline(student)))
			.fetch();
		String[] subjects = new String[result.size()];
		int i = 0;
		for (Record1<Object> record : result) {
			subjects[i++] = (String) record.getValue("SubjectSpecialization.subject");
		}
		return subjects;
	}
}
