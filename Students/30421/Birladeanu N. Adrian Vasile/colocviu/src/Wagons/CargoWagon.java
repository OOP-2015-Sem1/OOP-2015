package Wagons;

import java.util.Random;
import Carriables.CargoItem;

public class CargoWagon extends Wagon {

	//private String cargoType;
	private int numberOfCargos;
	private CargoItem cargoItem;
	
	public CargoWagon(){
		Random random=new Random();
		cargoItem=new CargoItem();
		numberOfCargos=random.nextInt(1000);
	}
	
	public int getTotalProfit() {
		int unitProfit=cargoItem.getProfit();
		return this.numberOfCargos*unitProfit;
	}

}
