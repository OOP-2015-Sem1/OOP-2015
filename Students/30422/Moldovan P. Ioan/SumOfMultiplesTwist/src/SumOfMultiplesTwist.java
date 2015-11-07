import java.util.*;

public class SumOfMultiplesTwist {
	
	private static boolean isMultipleOfDiv(int number, int divider) {
		// method to test if "number" is a multiple of "divider"
		if (number % divider == 0) {
			return true;
		}
		return false;
	}

	public static void main(String[] args){
		int sum = 0;
		int number=0;
		int maxValue=0; //value read from the input stream
		System.out.println("What's your value?");
		Scanner input = new Scanner(System.in);
		String inputString=input.nextLine();
		maxValue=Integer.parseInt(inputString);
		while (number<maxValue){
			if (isMultipleOfDiv(number,3)||(isMultipleOfDiv(number,5))){
				sum+=number;
			}
			number++;
		}
		System.out.println(sum);
		input.close();
	}
}
