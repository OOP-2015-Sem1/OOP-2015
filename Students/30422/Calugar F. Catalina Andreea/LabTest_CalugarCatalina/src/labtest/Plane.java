package labtest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Plane {

	private List<Compartment> compartments;
	private List<CargoCompartment> compCargo;
	private List<PassengerCompartment> passComp;
	private int total;

	private CargoCompartment cargos;
	private PassengerCompartment passengers;

	public Plane(String planeName) {
		
		compartments = new ArrayList<Compartment>();
		cargos = new CargoCompartment();
		passengers = new PassengerCompartment();
		compartments.addAll(compCargo);
		compartments.addAll(passComp);
	}

	public void computeTotalProfit() {
		total = cargos.computeProfitCargo() + passengers.computeProfitPassenger();
	}
}
