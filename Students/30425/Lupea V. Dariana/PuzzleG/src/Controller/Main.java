package Controller;

import java.util.Scanner;
import View.Puzzle;

public class Main {

	private Scanner name;
	private String userName;

	public void userName() {
		name = new Scanner(System.in);
		System.out.println("Give your name and press Enter:");
		setUserName(name.nextLine());
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public static void main(String args[]) {

		Main m = new Main();
		m.userName();
		new Puzzle("Images/bear");

	}

}
