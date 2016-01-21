package Carriables;

import java.util.Random;

public class Passenger implements Carriable {

	Random random=new Random();
	int passengerNumber=0;
	private final String name=new String(""+(++passengerNumber));
	
	public String getName(){
		return this.name;
	}
}
