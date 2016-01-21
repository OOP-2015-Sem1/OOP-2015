package col;

public class CargoItem implements Carriable{
	private String name;
	private int profit;
	private int items;
	public void setCargoName(String y){
		this.name=y;
	}
	public String getCargoName(){
		return name;
	}
	public void setProfit(int x){
		this.profit=x;
	}
	public int getProfit(){
		return profit;
	}
	public void setItemsNr(int w){
		this.items=w;
	}
	public int getItemsNr(){
		return items;
	}

}
