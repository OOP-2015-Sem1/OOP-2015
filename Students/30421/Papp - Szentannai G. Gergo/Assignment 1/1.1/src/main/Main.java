/**
 * 
 */
package main;

/**
 * @author gergo_000
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int sum=0;
		// TODO Read max from keyboard
		int max=1001;
		for(int i = 1; i<max;i++){
			if ((i%3==0) || (i%5==0))
					sum=sum+i;
		}
		System.out.println(sum);
	}

}
