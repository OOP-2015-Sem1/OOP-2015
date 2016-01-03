package model.utils;


import services.utils.InvalidTypeException;

public class Subject{
	public static LookupTable table;
	private String name;
	
	public Subject(String name){
		
		this.name = name;
		
	}
	
	public String getName() {
		return name;
	}
}
