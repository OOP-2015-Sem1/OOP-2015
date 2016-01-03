package controller.studentpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.table;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import org.jooq.Record2;
import org.jooq.Result;

import services.student.StudentFieldsGetter;

public class AbsenceFieldsGetter extends StudentFieldsGetter{
	
	public AbsenceFieldsGetter(JComboBox subjectBox, JTextArea textArea, String student) {
		super(subjectBox, textArea, student);
	}

	public String[] getFields() {
		String subject = subjectBox.getSelectedItem().toString();
		
		 Result<Record2<Object, Object>> result = db.create.select(field("motivated"), field("date"))
		.from(table("Absence"))
		.where(field("student").equal(inline(student)))
		.and(field("subject").equal(inline(subject)))
		.fetch();
		 
		String[] absences = new String[result.size()];
		int i = 0;
		String motivatedState;
		for (Record2<Object, Object> row : result) {
			
			if (row.getValue("motivated") .equals("true")) 
				motivatedState = "motivated";
			else 
				motivatedState = "unmotivated";
			
			absences[i++] =  row.getValue("date") + " / " + motivatedState ;
		}
		
		return absences;
	}

}
