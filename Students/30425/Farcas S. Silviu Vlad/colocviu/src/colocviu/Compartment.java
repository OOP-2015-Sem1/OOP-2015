package colocviu;

import java.util.*;

public class Compartment {
	
	private final String  ID = UUID.randomUUID().toString();
	private LinkedList<Carriable> carriables;
	public LinkedList<Carriable> getCarriables(){
		return carriables;
	}
	public int getProfit(){
		return 0;
	}
}
