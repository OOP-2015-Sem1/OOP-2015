package models;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class ShipBay {
	private final String ID=UUID.randomUUID().toString();
	public static int noOfShips;//witch every ship that docks,we add 1,and substract 1 when it leaves
	public ShipBay(){
		Set<Ship> s=new HashSet<Ship>();
	}
	
	public void receive(Ship ship){
	//	s.add(ship);
		noOfShips+=1;
	}
	
	public void depart(Ship ship){
	//	s.remove(ship);
		noOfShips-=1;
	}
	public boolean checkIfShipInBay(Ship ship){
		boolean ok=false;
	//	Iterator<s> it=new Iterator<s>() 
		//	while(it.hasNext()){
		//		if (Ship.getIDname().ship.equals(Ship.getIDname().it)){
					ok=true;
		
			//	}
		//		it.next();
				
	
			return ok;
	}

}

