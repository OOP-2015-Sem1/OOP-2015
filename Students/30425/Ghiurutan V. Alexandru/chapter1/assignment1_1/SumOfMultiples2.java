package chapter1.assignment1_1;

import javax.swing.JOptionPane;

/**
 * 
 * @author Alexandru
 *
 */
public class SumOfMultiples2 {
	public int findLastMul(int bound, int n) {
		int i;
		for (i = bound; i > 0; i--) {
			if (i % 3 == 0) {
				return i;
			}
		}
		return 0;
	}

	public int applyGauss(int n) {
		return (n * (n + 1) / 2);
	}

	public int multiple(int bound, int nr) {
		int n = (findLastMul(bound, nr) / nr);
		return (applyGauss(n) * nr);
	}

	public int readInput() {
		int number;
		while (true) {
			String s = (String) JOptionPane.showInputDialog(null, "Give the bound:");
			try {
				number = Integer.parseInt(s);
				break;// Breaks if it reads the correct bound
			} catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(null, "Enter a valid integer number!");
			} finally {

			}
		}
		return number;
	}

	public int compute(int bound) {
		return (multiple(bound, 3) + multiple(bound, 5) - multiple(bound, 15));
	}

	public static void main(String[] args) {
		SumOfMultiples2 s = new SumOfMultiples2();
		int bound = s.readInput();
		System.out.println("The sum of multiples of 3 and 5 below " + bound + " is " + s.compute(bound) + ".");
	}
}
