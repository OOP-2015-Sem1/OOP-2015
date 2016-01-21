import java.util.ArrayList;

public abstract class Wagon<E> {
	public final int ID;
	protected ArrayList<E> carriables;
	private int profit;

	public Wagon(int ID) {
		this.ID = ID;
	}

	public abstract void computeProfit();

	public ArrayList<E> getCollection() {
		return carriables;
	}

	public void setCollection(ArrayList<E> carrriables) {
		this.carriables = carriables;
	}

	public int getProfit() {
		return profit;
	}

	public void setProfit(int profit) {
		this.profit = profit;
	}

	public abstract void addCarriables();

}
