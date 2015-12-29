
public class EvenSum {

	public static void main(String args[]) {

		int x = 1; // Here value of first 2 terms have been initialized as 1 and 2
	    int y = 2;
	    
		int sum = 0;
		int evenSum = 2;

		while ((x + y) < 4000000) {
			sum = x + y;
			x = y;
			y = sum;
			if (sum % 2 == 0)
				evenSum += sum;
		}
		System.out.println("The sum of the even-valued terms < 4 millions is " + evenSum);
	}

}
