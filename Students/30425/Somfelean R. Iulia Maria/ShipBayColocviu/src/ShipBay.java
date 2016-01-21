import java.awt.List;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ShipBay {

	private Set ships = new HashSet<Ship>();
	
	public ShipBay(){
		System.out.println("shipbay");
	}
	public void receiveShip(Ship newShip){
		
		ships.add(newShip);
	}
	
	public void departsShip(Ship newShip){
		ships.remove(newShip);
	}
	
	public boolean shipIsInBay(Ship newShip){
		
		return ships.contains(newShip);
	}
}
