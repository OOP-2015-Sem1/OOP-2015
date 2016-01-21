package colocviu;

import java.util.*;

public class ShipBay {

	private Set<Ship> ships = new HashSet<>();
	
	public void receive (Ship ship){
		ships.add(ship);
	}
	
	public void remove (Ship ship){
		ships.remove(ship);
	}
	
	public boolean checkShip(Ship ship){
		return ships.contains(ship);
	}
	
	public void summary(Ship ship){
		System.out.println(ship.getName());
		System.out.println(ship.getCompartments().size());
		System.out.println(ship.getProfit());
	}
	
	public void situationByName(){
		LinkedList<Ship> shipsList = new LinkedList<Ship>(ships);
		for(int i=0;i<ships.size()-1;i++){
			for(int j=i+1;j<ships.size();j++){
				if(shipsList.get(i).getName().compareTo(shipsList.get(j).getName())>0){
					Ship aux=shipsList.get(i);
					shipsList.set(i, shipsList.get(j));
					shipsList.set(j, aux);
				}
			}
		}
	}
	
	public void situationByProfit(){
		LinkedList<Ship> shipsList = new LinkedList<Ship>(ships);
		for(int i=0;i<ships.size()-1;i++){
			for(int j=i+1;j<ships.size();j++){
				if(shipsList.get(i).getProfit()>shipsList.get(j).getProfit()){
					Ship aux=shipsList.get(i);
					shipsList.set(i, shipsList.get(j));
					shipsList.set(j, aux);
				}
			}
		}
	}
}
