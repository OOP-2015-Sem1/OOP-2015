/**
 * 
 */
package main;
import java.util.Scanner;
/**
 * @author gergo_000
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Enter max value:");
		Scanner in = new Scanner(System.in);
		int max = in.nextInt()+1; // remove +1 if max should not be included
		
		int sum=0;

		for(int i = 1; i<max;i++){
			if ((i%3==0) || (i%5==0)){
				sum=sum+i;
				//System.out.println(i);
			}
		}
		System.out.println(sum);
	}

}
