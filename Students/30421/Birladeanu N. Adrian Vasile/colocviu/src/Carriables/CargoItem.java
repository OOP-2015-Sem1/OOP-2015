package Carriables;

import java.util.Random;

public class CargoItem implements Carriable {

	private static int numberOfTypes=-1;
	private Random random=new Random();
	private final String name=new String(""+(++numberOfTypes));
	private final int profit=random.nextInt(50);
	
	public int  getProfit(){
		return this.profit;
	}
	
	public static int getNumberOfTypes(){
		return CargoItem.numberOfTypes;
	}
	
	public String getName(){
		return this.name;
	}
}
