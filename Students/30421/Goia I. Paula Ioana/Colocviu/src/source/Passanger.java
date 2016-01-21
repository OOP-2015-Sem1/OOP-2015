package source;

import java.util.UUID;

public class Passanger implements Carriable{
	
	

	public final String name;
	
	public Passanger(){
		this.name=UUID.randomUUID().toString();
	}
	
	public String getName() {
		return name;
		
	}
}
