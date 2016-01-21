package test;

import java.util.*;

public class Ship {
	private int toalProfit;
	Ship(){
		int r =new Random().nextInt(25);
		for(int i=0;i<r;i++){
			Compartment comp = new Compartment();
			toalProfit+=comp.getPrice();
		}
		System.out.println(r);
	}
	public int getTotalProfit(){
		return toalProfit;
	}
	public static void main(String[] args) {
		Ship ship = new Ship();
		System.out.print("the profit on a ship is");
		System.out.print(ship.getTotalProfit());

	}
}
