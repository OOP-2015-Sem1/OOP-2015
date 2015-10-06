package testProject;

public class largestPalindrome {

	
	public static boolean isPalindrome(int x) {
		int aux = x;
		int newX = 0;
		
		while(aux != 0) {
			newX = newX * 10 + aux%10;
			aux /= 10;
		}
		
		if(newX == x)
			return true;
		else
			return false;
	}
	
	
	
	
	public static void main(String[] args) {
		
		int maxPalindrome = 0;
		
		for(int i = 1000; i <= 9999; i++)
			for(int j = 1000; j <= 9999; j++) {
				if(isPalindrome(i*j))
					if(maxPalindrome < i*j)
						maxPalindrome = i*j;
				
			}
		
		System.out.println("The largest palindrome is:" + maxPalindrome);
		
	}
	
	// the largest palindrome from the product of 3 digit numbers is 906609
	// the largest palindrome from the product of 4 digit numbers is 99000099

}
