package pack;

import java.util.ArrayList;
import java.util.UUID;

public abstract class Wagon {

	
	
	private final UUID id = UUID.randomUUID();
	//Carriable[] carriableObjects;
	protected ArrayList<Carriable> carriableObjects = new ArrayList<Carriable>();
	
	
	
	public abstract int computeTotalProfit(ArrayList<Carriable> objects, int price );
	
	
}
