package controller.teacherpage;
import javax.swing.JComboBox;
import org.jooq.Result;
import org.jooq.Record2;
import org.jooq.SelectField;

import services.teacher.FieldsSetter;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

public class StudentsSetter extends FieldsSetter{

	public StudentsSetter(JComboBox selectedBox, JComboBox modifiedBox) {
		super(selectedBox, modifiedBox);
		
	}

	public String[] getNewFields(String selectedField) {
		
		Result<Record2<Object, Object>> result = db.create.select(field("firstName"), field("lastName"))
			.from(table("Student"))
			.naturalJoin(table("Class"))
			.where(field("class_name").equal(selectedField))
			.fetch();
		
		String[] students = new String[result.size()];
		int i = 0;
		for (Record2 record : result) {
			students[i++] = (String)record.getValue("firstName") +" "+ (String)record.getValue("lastName");
		}
		return students;
	}
	
	

	

}
