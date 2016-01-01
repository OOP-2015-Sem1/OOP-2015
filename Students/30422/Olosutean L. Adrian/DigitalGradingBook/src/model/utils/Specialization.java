package model.utils;

import java.util.ArrayList;

import services.utils.InvalidTypeException;

public class Specialization{
	
	public static LookupTable table;
	
	private ArrayList<Subject> subjects = new ArrayList<>();
	
	private String name;
	
	public Specialization(String name) throws InvalidTypeException {
		
		if(table.isValidType(name))
			this.name = name;
		
		else 
			throw new InvalidTypeException();
		
	}
	
	public String getName() {
		return name;
	}
	
	
}
