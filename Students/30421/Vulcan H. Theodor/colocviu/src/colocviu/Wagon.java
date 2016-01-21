package colocviu;

import java.util.Random;
import java.util.UUID;

public abstract class Wagon {

	public final String ID= UUID.randomUUID().toString();
	public int profit= 0;
	
	public abstract void calculateProfit();
	
	
	
	
}
