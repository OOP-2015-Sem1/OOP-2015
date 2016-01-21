package models;

import java.util.LinkedHashSet;

public class Ship {
	private static String name;
    private static Compartment comp;
	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Ship.name = name;
	}
	public LinkedHashSet<Compartment> compartments;
	
	public Ship(Compartment p, String name){
      		Ship.name=name;
      		Ship.comp=p;
	}

	public static Compartment getComp() {
		return comp;
	}

	public static void setComp(Compartment comp) {
		Ship.comp = comp;
	}
	

}
