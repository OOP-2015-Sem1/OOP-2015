package model.utils;

public class LookupTable {
	private final String[] types;
	
	public LookupTable(String[] types) {
		this.types = types;
	}
	
	public boolean isValidType(String name){
		for(String item : types){
			if(name.equals(item))
				return true;
		}
		return false;
	}
	
	public String[] getTypes() {
		return types;
	}
}
