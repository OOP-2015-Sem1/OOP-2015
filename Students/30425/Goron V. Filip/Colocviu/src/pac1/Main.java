package pac1;

public class Main {
	public static void main(String[] args) {

		ShipBay Constanta = new ShipBay();

		Constanta.receiveShip("Titanic");
		Constanta.receiveShip("Mary");

		Constanta.Ships[0].addComp(true);
		Constanta.Ships[1].addComp(true);

		Constanta.Ships[0].Compartments[0].addCarriable("John");
		Constanta.Ships[0].Compartments[0].addCarriable("Mary");
		Constanta.Ships[1].Compartments[0].addCarriable("Frank");

		Constanta.sortOutput(true);

	}

}
