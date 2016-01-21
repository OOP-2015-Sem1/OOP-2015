package colocviu;

import java.util.Random;

public class CargoWagon extends Wagon {
	
	public CargoItem[] cItem= new CargoItem[100];

	public void addCargo(String name){
		Random randomGenerator= new Random();
		int profitPerItem=randomGenerator.nextInt(100);
		
	}
	
	@Override
	public void calculateProfit() {
		
		
	}
}
