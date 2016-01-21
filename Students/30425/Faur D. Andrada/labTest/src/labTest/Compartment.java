package labTest;

import java.util.Scanner;

public interface Compartment {

	@SuppressWarnings("resource")
	public static String getID() {
		System.out.print("the ID is: ");
		Scanner bucky = new Scanner(System.in);
		String id = bucky.nextLine();
		return id;
	}

	public static int generateProfit(int price, int size) {
		return price * size;
	}
}
