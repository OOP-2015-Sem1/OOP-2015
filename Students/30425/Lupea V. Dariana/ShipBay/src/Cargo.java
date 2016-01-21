import java.util.ArrayList;
import java.util.List;

public class Cargo extends Compartment {

	CargoItem c = new CargoItem();
	public int cargoProfit;

	List<Carriable> carriableCargo = new ArrayList<Carriable>();

	public int calculateCargoProfit(ArrayList<Carriable> carriableCargo) {

		this.carriableCargo = carriableCargo;
		// int cargoProfit = (carriableCargo.size() - 1) * c.cargoProfit();
		return cargoProfit;
	}

}
