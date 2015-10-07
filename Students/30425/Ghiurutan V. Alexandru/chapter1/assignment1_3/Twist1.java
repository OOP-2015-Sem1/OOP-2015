package chapter1.assignment1_3;

public class Twist1 {
	/**
	 * Largest palindrom made from the product of four digit numbers.
	 */
	public boolean isPalindrom(int n) {
		int p = n, o = 0;
		while (p > 0) {
			o = o * 10 + p % 10;
			p /= 10;
		}
		if (n == o) {
			return true;
		} else {
			return false;
		}
	}

	public void largestDisplay() {
		int max = 0, first = 0, second = 0, product = 0;
		for (int i = 9999; i > 999; i--) {
			for (int j = i; j > 999; j--) {
				product = i * j;
				if (isPalindrom(product) && (max < product)) {
					max = product;
					first = i;
					second = j;
				}
			}
		}
		System.out.println("The largest palindrom made from the product of four digit numbers " + first + "*" + second
				+ "=" + max + ".");
	}

	public static void main(String[] args) {
		Twist1 f = new Twist1();
		f.largestDisplay();
	}
}
