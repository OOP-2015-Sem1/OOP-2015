package chapter1.assignment1_1;

import javax.swing.JOptionPane;

public class Twist {
	public boolean isMultiple(int n) {
		if ((n % 3 == 0) || (n % 5 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public long sum(long bound) {
		long sum = 0;
		for (int i = 1; i < bound; i++) {
			if (isMultiple(i)) {
				sum += i;
			}
		}
		return sum;
	}

	public long readInput() {
		long number;
		while (true) {
			String s = (String) JOptionPane.showInputDialog(null, "Give the bound:");
			try {
				number = Long.parseLong(s);
				break;// breaks if conversion succeeds
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "Enter a valid number!");
			} finally {

			}
		}
		return number;
	}

	public static void main(String[] args) {
		Twist s = new Twist();
		long bound = s.readInput();
		System.out.println("The sum of multiples below " + bound + " is " + s.sum(bound) + ".");
	}
	/**
	 * Twist 2: An overflow will occur into an integer variable if the number is
	 * greater then Integer.MAX_VALUE which is equal to 2147483647.
	 * 
	 * An overflow will occur into a long variable if we have a number greater
	 * then Long.MAX_VALUE which is equal to 9223372036854775807.
	 */
}
