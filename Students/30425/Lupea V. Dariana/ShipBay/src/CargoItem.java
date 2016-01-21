
public class CargoItem extends Cargo implements Carriable {
	private final String name = "Item";
	private final int cargoProfit = 50;
	
	public CargoItem(){
		
	}

	public int getCargoProfit() {
		return cargoProfit;
	}
	
	/*public String getName() {
		return name;
	}
	public int getProfit() {
		return profit;
	}
	
	public void setName(String newName){
		name = newName;
	}
	
	public void setProfit(int newProfit){
		profit = newProfit;
	}*/

}
