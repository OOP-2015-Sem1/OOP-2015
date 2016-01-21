package labTest;

import java.util.Scanner;

public class CargoItem implements Carriable {
	final String name = Carriable.getName();
	final int profit = getProfit();
	private static Scanner bucky;

	public static int getProfit() {
		System.out.print("the name is: ");
		bucky = new Scanner(System.in);
		int profit = bucky.nextInt();
		return profit;

	}

}
