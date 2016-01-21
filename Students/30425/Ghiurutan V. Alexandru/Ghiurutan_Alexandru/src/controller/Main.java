package controller;

import java.util.Collections;

import bay.*;
import utilities.CargoType;

public class Main {
	public static void main(String[] args) {
		ShipBay shipBay = new ShipBay();
		Ship ship1 = new Ship(CargoType.PASSENGER);
		Ship ship2 = new Ship(CargoType.CARGO);
		Compartment c1 = new Compartment(CargoType.PASSENGER);
		Compartment c2 = new Compartment(CargoType.PASSENGER);
		Compartment c3 = new Compartment(CargoType.CARGO);
		Compartment c4 = new Compartment(CargoType.CARGO);

	}
}
