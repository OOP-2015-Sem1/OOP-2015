package model.wagon;

public class PassangerWagon extends Wagon {
	private static final int PROFIT_PER_PASSANGER = 100;
	private static final int MAX_PASSANGERS = 100;

	@Override
	protected boolean canAdd() {
		return super.getCarriables().size() < MAX_PASSANGERS;
	}
	
	@Override
	public int getProfit() {
		int profit = getCarriables().size() * PROFIT_PER_PASSANGER;
		return profit;
	}
}
