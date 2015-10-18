package main;

import main.Polynomial;
import main.Functions;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Polynomial P1 = new Polynomial(5);
		P1.initArray();
		for (int i = 0; i < 5; i++) {
			P1.updatePol(i, i);
		}
		System.out.println("First Pol:");
		for (int i = 0; i < 5; i++) {
			System.out.println(P1.getPol()[i]);
		}

		Polynomial P2 = new Polynomial(6);
		P2.initArray();
		for (int i = 0; i < 6; i++) {
			P2.updatePol(i, 2);
		}
		System.out.println("Second Pol:");
		for (int i = 0; i < 6; i++) {
			System.out.println(P2.getPol()[i]);
		}
		System.out.println("ADD:");
		Polynomial P3 = Functions.ADD(P1, P2);

		for (int i = 0; i < 6; i++) {
			System.out.println(P3.getPol()[i]);
		}
	}

}
