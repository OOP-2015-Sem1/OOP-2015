package Wagons;

import java.util.UUID;

public abstract class Wagon {

	//protected int type;
	protected final UUID id=UUID.randomUUID();
	
	public abstract int getTotalProfit();
	
	
}
