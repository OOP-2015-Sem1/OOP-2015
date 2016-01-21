import java.util.ArrayList;

public class Wagon {
	public String wagonType = null;
	public int wagonProfit;
	private int i =0;
	Wagon(String type, int nr){
		wagonType = type;
		if(type == "cargo"){
			CargoItem cargo = new CargoItem();
			wagonProfit = nr * cargo.cargoItemProfit;
		}
		else if(type == "passangers"){
			Passanger pas = new Passanger("ion");
			wagonProfit = 100*100;
		}
	}
}
