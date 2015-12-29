public class fsum {
	public static void main(String[] args) {
		int temp = 0, first = 1, second = 0, sum = 0;
		System.out.print("The Fibonacci sequence: ");
		while (temp < 4000000) {
			System.out.print(" " +temp);
			temp++; 
			temp = first + second;
			first = second;
			second = temp;
			if (temp % 2 == 0) {
				sum = sum + temp;
			}
		}
		System.out.println("\nThe sum of the even-valued terms is: " + sum);
	}
}
