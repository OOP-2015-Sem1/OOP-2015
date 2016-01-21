package trainItems;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Wagon {
	private String name;
	private ArrayList<Carriable> carriables;

	public Wagon() {
		setCarriables(new ArrayList<Carriable>());
		System.out.println("Creating wagon...");
		name = new String(UUID.randomUUID().toString());
		Random random = new Random();
		int r = random.nextInt();
		addCarriables(r, 10);
	}

	private void addCarriables(int r, int nrOfElementsToAdd) {
		System.out.println("Adding carriables...");
		for (int i = 0; i < nrOfElementsToAdd; i++) {
			if (r % 2 == 0) {
				getCarriables().add(new Passenger());
			} else {
				getCarriables().add(new CargoItem());
			}
		}
	}

	public ArrayList<Carriable> getCarriables(){
		return carriables;
	}

	public void setCarriables(ArrayList<Carriable> carriables) {
		this.carriables = carriables;
	}

}
