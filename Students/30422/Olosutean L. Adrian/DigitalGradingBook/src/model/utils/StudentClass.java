package model.utils;

import java.time.Year;
import java.util.ArrayList;

import model.users.Student;
import services.utils.InvalidTypeException;

public class StudentClass{
	public static LookupTable table;
	private ArrayList<Student> students;
	private Specialization specialization;
	private int currentYear;
	private String name;
	
	public StudentClass(String name) {
		this.name = name;
	}
	
	public StudentClass(Specialization specialization, int currentYear , String name) throws InvalidTypeException {
		this.specialization = specialization;
		this.currentYear = currentYear;

		if(table.isValidType(name))
			this.name = name;
		
		else 
			throw new InvalidTypeException();
	}
	
	public String getName() {
		return name;
	}
	
	
}
