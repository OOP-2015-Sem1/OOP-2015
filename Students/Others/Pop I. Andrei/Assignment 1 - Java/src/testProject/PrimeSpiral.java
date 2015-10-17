package testProject;

public class PrimeSpiral {
	
	public static float percentage(float prime, float total) {
		
	float result;
	
	result = prime/total * 100;
	
	return result;
		
	}
	
	public static boolean isPrime(int x) {
		
		for(int i = 2; i < x / 2; i++) {
			if(x % i == 0)
				return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		
		int prime = 0, total = 1, i, pas = 2, nr = 3;
		
		int temp = (int)  percentage(prime, total);
		
		while(temp > 10 || nr == 3) {
			
			for(i = 0; i < 4; i++) {
				if(isPrime(nr))
					prime += 1;
				nr += pas;
			}
			total = 2*(pas + 1) - 1;
			temp = (int)  percentage(prime, total);
			
			pas += 2;
			nr += 2;
			
			
			//System.out.println(temp);
		}
		
		System.out.println(pas);
	
	}

}
