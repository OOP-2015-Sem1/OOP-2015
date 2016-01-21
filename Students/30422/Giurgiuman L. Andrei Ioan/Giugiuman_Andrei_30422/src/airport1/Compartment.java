package airport1;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Compartment<Carriable> {
	private final String ID;
	ArrayList<Carriable> carriables;
	private static final int PASSANGERPRICE = 100;
	private static final int MAX_NR_PASSANGERS = 100;
	private int profitPerComp;

	public Compartment() {
		ID = UUID.randomUUID().toString();
		carriables = new ArrayList<Carriable>();
		int m = Math.abs(new Random().nextInt(MAX_NR_PASSANGERS));
		for (int i = 0; i < m; i++) {
			carriables.add(new Passanger());

		}
	}

	private void computeProfit() {

	}

	public int getProfitPerComp() {
		return profitPerComp;
	}

	public String getID() {
		return ID;
	}

}
