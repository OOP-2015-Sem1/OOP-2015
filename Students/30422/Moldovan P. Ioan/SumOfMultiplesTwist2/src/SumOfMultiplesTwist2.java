public class SumOfMultiplesTwist2 {
	private static boolean isMultipleOfDiv(int number, int divider) {
		// method to test if "number" is a multiple of "divider"
		if (number % divider == 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {

		int number = 0;
		int sum = 0;
		do {
			if (isMultipleOfDiv(number, 3) || (isMultipleOfDiv(number, 5))) {
				sum += number;
			}
			number++;
//			System.out.println(number);
		} while (sum <= Integer.MAX_VALUE);
		System.out.println(number);
	}
}
