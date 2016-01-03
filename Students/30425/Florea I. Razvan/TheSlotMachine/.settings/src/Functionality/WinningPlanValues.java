package Functionality;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WinningPlanValues {

	private Scanner input;
	private int[][] winPlan = new int[8][5];

	public WinningPlanValues() {

		openFile();
		readFile();
		closeFile();

	}

	private void openFile() {

		try {
			input = new Scanner(new File("WinningPlan.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("File was not found !");
		}

	}

	private int[][] readFile() {

		while (input.hasNext()) {

			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 5; j++) {
					winPlan[i][j] = input.nextInt();
				}
			}
		}

		return winPlan;
	}

	private void closeFile() {
		input.close();
	}

	public int[][] getWinPlan() {
		return winPlan;
	}

	public void setWinPlan(int[][] winPlan) {
		this.winPlan = winPlan;
	}
}
