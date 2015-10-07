package chapter1.assignment1_1;

public class SummOfMultiples {
	public boolean isMultiple(int n) {
		if ((n % 3 == 0) || (n % 5 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	public long sum() {
		long sum = 0;
		for (int i = 1; i < 1000; i++) {
			if (isMultiple(i)) {
				sum += i;
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		SummOfMultiples s = new SummOfMultiples();
		System.out.println("The sum of multiples below " + 1000 + " is " + s.sum() + ".");
	}
}
