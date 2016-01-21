package com.pack.main;

import java.util.ArrayList;
import com.pack.airport.Plane;

public class Airport {
	private ArrayList<Plane> listOfPlanes;

	public Airport() {
		listOfPlanes = new ArrayList<Plane>();
		receivePlane();
		receivePlane();
		receivePlane();

	}

	public void receivePlane() {
		listOfPlanes.add(new Plane());
	}

	public boolean findPlane(Plane plane) {
		if (listOfPlanes.contains(plane)) {
			return true;
		} else {
			return false;
		}
	}

	public void departPlane(Plane plane) {
		if (findPlane(plane)) {
			listOfPlanes.remove(plane);
		} else {
			System.out.println("Plane not in station!");
		}
	}

	public void sortByName() {
		for (int i = 0; i < listOfPlanes.size() - 1; i++) {
			for (int j = i + 1; j < listOfPlanes.size(); j++) {
				Plane plane1, plane2;
				plane1 = listOfPlanes.get(j);
				plane2 = listOfPlanes.get(i);
				if (plane1.getName().compareTo(plane2.getName()) == 0) {
					Plane aux;
					aux = plane1;
					plane1 = plane2;
					plane2 = aux;
				}
			}
		}
	}

	public void sortByProfit() {
		for (int i = 0; i < listOfPlanes.size() - 1; i++) {
			for (int j = i + 1; j < listOfPlanes.size(); j++) {
				Plane plane1, plane2;
				plane1 = listOfPlanes.get(j);
				plane2 = listOfPlanes.get(i);
				if (plane1.getProfit() > plane2.getProfit()) {
					Plane aux;
					aux = plane1;
					plane1 = plane2;
					plane2 = aux;
				}
			}
		}
	}

	public void printPlanes() {
		for (int i = 0; i < listOfPlanes.size(); i++) {
			Plane plane1 = listOfPlanes.get(i);
			System.out.printf("%s: %d, %d.\n", plane1.getName(), plane1.getNrOfComps(), plane1.getProfit());
		}
	}

}
