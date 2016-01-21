
public class CargoItem {

	public String cargoItemName ;
	public int cargoItemProfit;
	private int i =0;
	CargoItem(){
		
		cargoItemName = "wagon"+i;
		i++;
		cargoItemProfit = 200;
	}
}
