package alexas.model;

//this should actually be in a package like Controller || Controller&&Viwe
public class Airport {
	public static void main(String[] args) {

		/*
		 * from my understanding of the problem: I will have an airplane which
		 * contains compartments well, it actually contains
		 * CargoItemCompartiments and and PassengerComp CargoItemComp can hold
		 * cargoItem stuff and PessangerComp can hold Passengers, so all
		 * carriables
		 */

		AirPlane<Compartment<CargoItem>> plane = new AirPlane<>();

		for (int i = 0; i < 10; i++) {
			CargoItemComp cgcomp = new CargoItemComp();
			for (int j = 0; j < 5; j++) {
				CargoItem cgs = new CargoItem();
				cgcomp.add(cgs);
			}
			plane.add(cgcomp);
		}
		System.out.println(plane.dispayText());
		
		
		AirPlane<Compartment<Passenger>> plane2 = new AirPlane<>();

		for (int i = 0; i < 10; i++) {
			PassengerComp pcomp = new PassengerComp();
			for (int j = 0; j < 5; j++) {
				Passenger pgs = new Passenger();
				pcomp.add(pgs);
			}
			plane2.add(pcomp);
		}
		System.out.println(plane2.dispayText());
		
		
		
	}

}
