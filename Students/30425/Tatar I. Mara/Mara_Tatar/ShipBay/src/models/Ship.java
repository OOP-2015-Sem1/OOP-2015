package models;

import java.util.UUID;

public class Ship {
	private final String ID=UUID.randomUUID().toString();

	
	public String getIDName(){
		  return ID;
		 }
	
}
