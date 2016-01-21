package colocviu;

import java.util.ArrayList;

public class MainClass {

	public static void main(String[] args) {

		ShipBay shipBay = new ShipBay();

		Ship ship1 = new PeopleShip();
		Ship ship2 = new CargoShip();

		Compartment<Passanger> passangerCompartment = new Compartment<Passanger>();
		Compartment<CargoItem> cargoCompartment = new Compartment<CargoItem>();

		Passanger passanger1 = new Passanger();
		Passanger passanger2 = new Passanger();
		Passanger passanger3 = new Passanger();
		Passanger passanger4 = new Passanger();
		// passanger1.printName();
		ArrayList<Passanger> passangerList = new ArrayList<Passanger>(passangerCompartment.TOTAL_NR_OF_PASSANGERS);

		passangerList.add(passanger1);
		passangerList.add(passanger2);
		passangerList.add(passanger3);
		passangerList.add(passanger4);

		CargoItem item1 = new CargoItem();
		CargoItem item2 = new CargoItem();
		CargoItem item3 = new CargoItem();
		CargoItem item4 = new CargoItem();

		System.out.println("CargoItem profit ");
		item1.printProfit();

		ArrayList<CargoItem> cargoItemList = new ArrayList<CargoItem>();
		cargoItemList.add(item1);
		cargoItemList.add(item2);
		cargoItemList.add(item3);
		cargoItemList.add(item4);

		passangerCompartment.addToCompartment(passangerList);

		System.out.println("\nPassanger profit ");
		passangerCompartment.passangerProfit();

		cargoCompartment.addToCompartment(cargoItemList);

		System.out.println("\nCargo profit ");
		cargoCompartment.cargoProfit();

		ship1.loadCompartments(passangerCompartment);
		System.out.println("The name of ship1 is ");
		ship1.showName();
		ship1.showNumberOfCompartments();

	}
}
