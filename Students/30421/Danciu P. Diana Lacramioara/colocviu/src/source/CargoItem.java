package source;

import java.util.*;

public class CargoItem implements ICarriable {

	private final int MAX_VALUE = 999999;
	private Random randObj = new Random();
	public final String NAME = null;
	public final int PROFIT = randObj.nextInt(MAX_VALUE) + 1; // so I wouldn't
																// have 0
	public List<ICarriable> carriableCargo = new ArrayList<ICarriable>();

	public int cargoItemProfit(List<ICarriable> carriable) {
		int profit;

		profit = carriable.size() * PROFIT;

		return profit;
	}
}