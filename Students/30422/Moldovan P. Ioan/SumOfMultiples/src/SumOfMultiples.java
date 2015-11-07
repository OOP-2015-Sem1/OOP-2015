public class SumOfMultiples {
	private static boolean isMultipleOfDiv(int number, int divider){
	 //method to test if "number" is a multiple of "divider"
		if(number%divider==0){
			return true;
		}
		return false;
	}
	
	private static final int MAX_VALUE = 1000;
	
	public static void main(String[] args){
		int sum = 0;   
		int number=0;   
		while (number<MAX_VALUE){
			if (isMultipleOfDiv(number,3)||(isMultipleOfDiv(number,5))){
				sum+=number;
			}
			number++;
		}
		System.out.println(sum);
	}
}
