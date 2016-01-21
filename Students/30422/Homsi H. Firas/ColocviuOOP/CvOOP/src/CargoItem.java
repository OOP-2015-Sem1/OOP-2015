
public class CargoItem {

	public final int profit;
	public final String NAME;
	public boolean isCarriable = true;
	
	public CargoItem(String name, int profit, boolean isCarriable){
		
		this.NAME = name;
		this.profit = profit;
		this.isCarriable = isCarriable;
	}
	
	public String getCargoName(){
		
		return this.NAME;
	}
	
	public Boolean isCarriable(){
		
		return isCarriable;
	}
	
	public int getProfit(){
		return profit;
	}
	
	
	/*public int getCargoPrice(int itemPrice, Compartment compartment){
		return itemPrice*compartment.getNrOfComps();
	}*/
}
