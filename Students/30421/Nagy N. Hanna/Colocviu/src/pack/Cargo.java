package pack;

import java.util.ArrayList;

public class Cargo extends Wagon{

	
	private CargoItem cargoItem;
	public Cargo(CargoItem cargoItem){
		this.cargoItem= cargoItem;
}
	
	public int computeTotalProfit(ArrayList<Carriable> objects, int price) {
		// TODO Auto-generated method stub
		 
		return cargoItem.getProfit() * objects.size();
	}
}
