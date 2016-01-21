package trains.components;

import java.util.LinkedHashSet;
import java.util.UUID;

public class Train{
	
	private final String ID;
	private LinkedHashSet <PassengerWagon>passWagons;
	private LinkedHashSet <CargoWagon>cargoWagons;
	private final int CARGO = 1;
	private int trainType;
	private int nrOfWagons;
	
	public Train() {
		ID = UUID.randomUUID().toString();
		trainType = (int) (Math.random() * 2);
		nrOfWagons = (int) (Math.random() * 15);
		
		
		
		addWagons();
		
	}
	
	private void addWagons() {
		
		WagonModel w;
		
		for(int i = 0; i < nrOfWagons; i++) {
			if(trainType == CARGO) {
				w =  new CargoWagon();
				cargoWagons.add((CargoWagon)(w));
			}
			else {
				w = new PassengerWagon();
				passWagons.add((PassengerWagon)(w));
			}
		}
		

	}
	
	public int compute() {
		
		return 0;
		
	}
	

}
