
public abstract class Compartment {
	protected String ID;
	protected int profit;

	public Compartment(String ID, int profit) {
		this.ID = ID;
		this.profit = profit;
	}

	public abstract void computeProfit();
}
