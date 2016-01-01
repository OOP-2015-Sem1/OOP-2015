package controller.studentpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.jooq.impl.DSL.inline;
import java.io.ObjectInputStream.GetField;

import javax.swing.JComboBox;
import javax.swing.JTextArea;

import org.jooq.Record2;
import org.jooq.Result;

import services.student.StudentFieldsGetter;

public class MarkFieldsGetter extends StudentFieldsGetter{

	public MarkFieldsGetter(JComboBox subjectBox, JTextArea textArea, String student) {
		super(subjectBox, textArea, student);
		
	}

	
	public String[] getFields() {
		
		String[] marks;
		String subject = subjectBox.getSelectedItem().toString();
		
		 Result<Record2<Object, Object>> result = db.create.select(field("grading"), field("date"))
		.from(table("Mark"))
		.where(field("student").equal(inline(student)))
		.and(field("subject").equal(inline(subject)))
		.fetch();
		 
		marks = new String[result.size()];
		int i = 0;
		for (Record2<Object, Object> row : result) {
			marks[i++] = row.getValue("grading") + " / " +row.getValue("date");
		}
		
		return marks;
	}


	
	

}
