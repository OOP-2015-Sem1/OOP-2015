package pack1;

public class Palindrome {
	public static int revers(int nr) {
		int rev = 0;
		while (nr != 0) {
			int uc = nr % 10;
			rev = rev * 10 + uc;
			nr /= 10;
		}
		return rev;
	}

	public static boolean testPalindrome(int nr) {
		if (nr == revers(nr)) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int nr1, rez=34;
		nr1 = 999; 			//for 4 digit numbers change to 9999;
		boolean found=false;
		while(!found){
			for(int i=0; i<200; i++){   //for 4 digit numbers replace 200 with 2000;
				rez=nr1*(nr1-i);
				System.out.println(rez);
				if(testPalindrome(nr1*(nr1-i))){
					found=true;
					System.out.printf("The number found is: %d=%d*%d",rez,nr1,nr1-i);
					break;
				}
			}
			nr1--;
		}
		
		
	}

}
