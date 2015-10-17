/**
 * 
 */
package main;

/**
 * @author gergo_000
 *
 */
public class Main {
	
	static boolean isPalindrome(int x){
		boolean p=false;
		int y=x;
		int invX=0;
		
		while(x!=0){
			invX=invX*10+x%10;
			x=x/10;
		}
		if (invX == y){
				p=true;
				
		}
		return p;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int a,b;
		a=b=999;
		
		// This might look ugly but is faster 
		while(!isPalindrome(a*b) && a>99){
			b=a;
			while(!isPalindrome(a*b) && b>99){
				b--;
				}
			if (isPalindrome(a*b)){
				System.out.println(a*b);
				break;
			}
			a--;
			}
	}
}
