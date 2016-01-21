package labTest;

import java.util.Scanner;

public interface Carriable {

	@SuppressWarnings("resource")
	public static String getName() {
		System.out.print("the name is: ");
		Scanner bucky = new Scanner(System.in);
		String name = bucky.nextLine();
		return name;

	}

}
