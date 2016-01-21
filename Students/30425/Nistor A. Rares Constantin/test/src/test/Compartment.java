package test;

import java.util.*;

public class Compartment {
	static final int passengerPrice = 100;
	static final int ticket = 100;
	final String ID = UUID.randomUUID().toString();
	ArrayList<Carriable> z = new ArrayList<Carriable>();
	//int r = new Random().nextInt(1);
	int r=1;
	int nrOfStuffInCompartment = 0;
	int w;

	Compartment() {

		for (int i = 0; i < new Random().nextInt(100); i++) {
			if (r == 0) {
				CargoItem c = new CargoItem();
				z.add(c);
				nrOfStuffInCompartment++;
			} else if (r == 1) {
				Passengers p = new Passengers();
				z.add(p);
				nrOfStuffInCompartment++;
			}
		}
	}

	public int getPrice() {
		int price = 0;
		for (int i = 1; i <= nrOfStuffInCompartment; i++) {
			if (r == 1)
				price += 100;
			// else if(r==0)
			// price=price+((CargoItem) z.get(i)).getProfit();
		}
		return price;
	}

	String getID() {
		return ID;
	}

	
}
