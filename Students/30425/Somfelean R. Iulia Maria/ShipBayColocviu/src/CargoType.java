import java.util.UUID;

public class CargoType extends Compartment {

	private CargoItem ct;
	public CargoType(){
		
		super(false, true);
		ct = new CargoItem(UUID.randomUUID().toString(), (int)Math.random() * 100000);
	}
	
	private void computeProfit() {

		int profit = ct.getProfitCargoItem() * getCarriables().size();
		setProfit(profit);
	}

	@Override
	public void addCarriable(Carriable c) {
		// TODO Auto-generated method stub
		getCarriables().add(c);
	}
	
}
