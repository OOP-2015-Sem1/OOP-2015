import java.util.Scanner;

public class sum {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int a,sum=0;
		System.out.print("Enter your number: ");
		a = input.nextInt();
		System.out.print("The sum of multiples of 3 and 5 bellow the given number is: ");
		for(int i=1;i<a;i++){
			if(i%3==0 || i%5==0)
				sum=sum+i;
		}
		System.out.print(sum);
		input.close();
	}
}
