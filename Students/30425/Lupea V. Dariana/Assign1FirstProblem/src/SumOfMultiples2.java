import java.util.Scanner;

public class SumOfMultiples2 {

	static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		int sum = 0;

		System.out.println("Please enter a number: ");
		int enteredNumber = input.nextInt();

		for (int counter = 0; counter < enteredNumber; counter++) {
			if (counter % 3 == 0 || counter % 5 == 0) {
				sum += counter;
			}
			if (counter % 3 == 0 && counter % 5 == 0) { // without the multiples
														// of both 3 and 5
				sum -= counter;
			}

			System.out.println("The sum of multiples of 3 or 5 below " + enteredNumber + " is " + sum);
		}
	}
}

/*
 * Twist 2: - in the case of an integer variable, an overflow will occur for a
 *            number greater than 2,147,483,647 or lower than -2,147,483,648
 *         - in the case of a long variable, an overflow will occur for a number greater
 *           than +9,223,372,036,854,775,807 or lower than -9,223,372,036,854,775,808
 */