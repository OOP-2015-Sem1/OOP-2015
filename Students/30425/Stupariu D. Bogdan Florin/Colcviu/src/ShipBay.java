import java.util.ArrayList;

public class ShipBay {

	private ArrayList<Ship> allShips = new ArrayList<Ship>(); 
	
	public void receiveShip(Ship ship){
		allShips.add(ship);
	}
	
	public void departShip(Ship ship){
		allShips.remove(ship);
	}
	
	public boolean checkForShip(Ship ship){
		
		for(int i = 0;i<=allShips.size();i++){
			Ship tempship = allShips.get(i);
			
		}
		return false;
	}

}
