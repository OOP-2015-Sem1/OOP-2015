public class MainClass {

	public static void main(String args[]) {
		final int MAX_VALUE=4000000;
		int t1, t2;
		t1 = 1;
		t2 = 2;
		int sum = 0;
		while (t2 <MAX_VALUE) {
			if (t2 % 2 == 0) {
				sum += t2;
				System.out.println(t2);
			}
			int aux = t1 + t2;
			t1 = t2;
			t2 = aux;

		}
		System.out.printf("The sum is: %d",sum);
	}
}
