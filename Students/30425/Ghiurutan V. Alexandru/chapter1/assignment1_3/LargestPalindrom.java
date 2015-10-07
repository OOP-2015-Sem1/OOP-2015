package chapter1.assignment1_3;

public class LargestPalindrom {
	/**
	 * Largest palindrom made from the product of three digit numbers.
	 */
	public boolean isPalindrom(int n) {
		int o = 0, p = n;
		while (p > 0) {
			o = o * 10 + p % 10;
			p /= 10;
		}
		if (o == n) {
			return true;
		} else {
			return false;
		}
	}

	public void largestDisplay() {
		int max = 0, first = 0, second = 0, product = 0;
		for (int i = 999; i > 99; i--) {
			for (int j = i; j > 99; j--) {
				product = i * j;
				if (isPalindrom(product) && (product > max)) {
					max = product;
					first = i;
					second = j;
				}
			}
		}
		System.out.println("The largest palindrom made from the product of 3 digit numbers:" + first + "*" + second
				+ "=" + max + ".");
	}

	public static void main(String[] args) {
		LargestPalindrom f = new LargestPalindrom();
		f.largestDisplay();
	}
}
