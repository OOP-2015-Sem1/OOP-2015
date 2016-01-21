import java.util.*;
public class Bay {

	Set<Ship> ships=new HashSet();
	public void addShip(String type){
		ships.add(new Ship(type));
	}
	
	public void removeShip(Ship ship){
		ships.remove(ship);
	}
	
	public boolean checkIfShipInTheBay(Ship ship){
		for(Ship i:ships){
			if (i.equals(ship)) return true;
		}
		return false;
	}
	
	public Set<Ship> sortedByName(){
		int n=ships.size();
		Iterator it;
		for (Ship i:ships){
			for (Ship j:ships){
				 
				
			}
			}
		
		return ships;
	}
	
	
    public Set<Ship> sortedByProfit(){
		
		return ships;
	}
	
    public void seeShips(){
    	for (Ship i:ships){
    		System.out.println("Name: "+i.name+";"+ "NrOfCompartments: "+ i.compartments.size()+";"+ "Profit: "+i.profit+".");
    	}
    }
}
