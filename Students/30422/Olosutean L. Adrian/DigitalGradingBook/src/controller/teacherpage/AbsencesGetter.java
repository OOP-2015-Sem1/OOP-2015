package controller.teacherpage;

import controller.DatabaseController;
import model.studentfields.Absence;
import org.jooq.Record;
import org.jooq.Result;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;


public interface AbsencesGetter extends StudentUsernameGetter{
	
	public default String[] getUnmotivatedAbsences(String studentUsername, String subject){
		String[] result = null;
		
		result = db.create.select()
				.from(table("Absence"))
	    		.where(field("motivated").equal("0"))
	    		.and(field("student").equal(studentUsername))
	    		.fetchArray(field("date"),String.class);

    	return result;
	}
	
}
