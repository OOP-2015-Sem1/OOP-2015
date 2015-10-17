package main;

import main.Polynomial;
import main.Functions;
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Polynomial P1 = new Polynomial(5);
		P1.initArray();
		for(int i = 0; i<5; i++){
			P1.updatePol(i, i);
		}
		for (int i=0;i<5;i++){
			System.out.println(P1.getPol()[i]);
		}
		
		Polynomial P2 = new Polynomial(5);
		P2.initArray();
		for(int i = 0; i<5; i++){
			P2.updatePol(i, 2);
		}
		for (int i=0;i<5;i++){
			System.out.println(P2.getPol()[i]);
		}
	}



}
