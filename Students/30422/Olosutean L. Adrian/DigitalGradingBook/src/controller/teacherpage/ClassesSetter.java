package controller.teacherpage;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

import javax.swing.JComboBox;

import controller.DatabaseController;
import services.teacher.FieldsSetter;

public class ClassesSetter extends FieldsSetter{

	private String teacherUsername;
	
	public ClassesSetter(JComboBox selectedBox, JComboBox modifiedBox, String teacherUsername) {
		super(selectedBox, modifiedBox);
		
		this.teacherUsername = teacherUsername;
	}
	
	public String[] getNewFields(String selectedField) {
		
		String[] result = db.create.select(field("Class.class_name"))
					.from(table("Teacher"))
					.join(table("Course"))
						.on(field("Teacher.username").equal(field("Course.teacher")))
					.naturalJoin(table("Class"))
					.naturalJoin(table("Specialization"))
					.naturalJoin(table("SubjectSpecialization"))
					.where(field("SubjectSpecialization.subject").equal(selectedField))
					.and(field("Teacher.username").equal(teacherUsername))
					.fetchArray(field("Class.class_name"),String.class);
		
		return result;
	}

}
