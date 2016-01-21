package com.pack.main;

public class MainClass {

	public static void main(String[] args) {
		Airport airport=new Airport();
		airport.sortByName();
		airport.printPlanes();
		airport.sortByProfit();
		airport.printPlanes();
		
	}

}
