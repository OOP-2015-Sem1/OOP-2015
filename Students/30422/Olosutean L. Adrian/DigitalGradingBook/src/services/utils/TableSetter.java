package services.utils;

import java.sql.SQLException;

import controller.Database;
import controller.TypeGetter;
import model.utils.StudentClass;
import model.utils.LookupTable;
import model.utils.Specialization;
import model.utils.Subject;

public class TableSetter implements TypeGetter{
    
	public void setTable(String table){
		switch (table) {
		case "Subject":
			setSubjectTable();
			break;
			
		case "Specialization":
			setSpecializationTable();
			break;
			
		case "Class":
			setClassTable();
			break;
		}
			
	}
	
	private void setSpecializationTable(){
		String[] specializations = getTypes("specialization","Specialization");
		Specialization.table = new LookupTable(specializations);
	}
	
	private void setSubjectTable(){
		String[] subjects = getTypes("subjectName","Subject");
		Subject.table = new LookupTable(subjects);

	}
	
	private void setClassTable(){
		String[] classes = getTypes("class_name", "Class");
		StudentClass.table = new LookupTable(classes);
	}
	
}
