public class PalindromicNumber {

	public static void main(String args[]) {

		int max = 100001;

		for (int i = 999; i >= 100; i--) {
			if (max >= i * 999) {
				break;
			}
			for (int j = 999; j >= i; j--) {
				int p = i * j;
				if (max < p && isPalindrome(p)) {
					max = p;
				}
			}
		}
		System.out.println("The largest palindrome made from the product of two 3-digit numbers is " + max);

	}

	public static boolean isPalindrome(int number) {
		int palindrome = number; // copied number into variable
		int reverse = 0;

		while (palindrome != 0) {
			int remainder = palindrome % 10;
			reverse = reverse * 10 + remainder; // create the reverse of our
												// number
			palindrome = palindrome / 10;
		}

		// if original and reverse of the number are equal means the number is a
		// palindromic one

		if (number == reverse) {
			return true;
		} else
			return false;
	}

}
