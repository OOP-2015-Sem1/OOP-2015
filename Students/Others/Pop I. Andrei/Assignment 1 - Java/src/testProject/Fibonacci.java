package testProject;



public class Fibonacci {
	
	public static int fibSumCalculator() {
		int t1 = 1, t2 = 2, aux;
		int sum = 0;
		
		while(t2 < 4000000) {
			if(t2 % 2 == 0)
				sum += t2;
			aux = t2;
			t2 = t1 + t2;
			t1 = aux;
			//System.out.println(sum + " ");
		}
		return sum;
	}
	
	
	public static void multiply(int F[][], int A[][]) {
		
		int a, b, c, d;
		
		a = F[0][0] * A[0][0] + F[0][1] * A[0][1];
		b = F[1][0] * A[0][0] + F[1][1] * A[1][0];
		c = F[0][0] * A[0][1] + F[0][1] * A[1][1];
		d = F[1][0] * A[0][1] + F[1][1] * A[1][1];
		
		F[0][0] = a;
		F[0][1] = b;
		F[1][0] = c;
		F[1][1] = d;
		
	}
	
	
	public static void powerF(int n, int F[][]) {
		
		if(n == 0 || n == 1)
			return;
		else {
			int A[][] = {{1,1},{1,0}};
			
			powerF(n/2, F);
			
			multiply(F, F);
			
			if(n % 2 == 1)
				multiply(F, A);
		}
	}
	
	
	public static int fibNthTherm(int n) {
		
		int F[][] = {{1, 1}, {1, 0}};
		powerF(n, F);
		
		return F[0][0];
		
	}
	
	
	public static void main(String[] args) {
	
		System.out.println("The sum of the even Fibonacci therms is: " + fibSumCalculator());
		System.out.println("The N th Fibonacci therm is " + fibNthTherm(5));

		
	}

}