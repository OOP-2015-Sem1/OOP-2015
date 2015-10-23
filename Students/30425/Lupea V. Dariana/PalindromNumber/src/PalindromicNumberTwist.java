public class PalindromicNumberTwist {
	

		public static void main(String args[]) {

			int max = 10000001;

			for (int i = 9999; i >= 1000; i--) {
				if (max >= i * 9999) {
					break;
				}
				for (int j = 9999; j >= i; j--) {
					int p = i * j;
					if (max < p && isPalindrome(p)) {
						max = p;
					}
				}
			}
			System.out.println("The largest palindrome made from the product of two 4-digit numbers is " + max);

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
