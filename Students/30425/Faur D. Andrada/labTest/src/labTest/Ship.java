package labTest;

import java.util.Scanner;

public interface Ship {
	@SuppressWarnings("resource")
	public static String getShipName() {
		System.out.print("the name is: ");
		Scanner bucky = new Scanner(System.in);
		String name = bucky.nextLine();
		return name;
	}

	/*
	 * @SuppressWarnings("resource") public static String getShipType(){
	 * System.out.print("press 'p'for passenger or 'c' for cargo item: ");
	 * Scanner bucky = new Scanner(System.in); String name = bucky.nextLine();
	 * if (name=="c"){ return "CargoItem"; }else if (name=="p"){ return
	 * "Passenger"; }else{ return null; }
	 * 
	 * }
	 * 
	 */

}
