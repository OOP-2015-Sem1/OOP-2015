import java.util.UUID;

public class CargoItem implements Carriable{

	private String name;
	private int profitCargoItem;
	public CargoItem(String name, int profit){
		this.setName(name);
		this.setProfitCargoItem(profit);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getProfitCargoItem() {
		return this.profitCargoItem;
	}
	public void setProfitCargoItem(int profit) {
		this.profitCargoItem = profit;
	}
}
