package assignment2;

public class Polynomial {
	
	public double coeffs[];
	public int degree;
	
	public Polynomial(double[] coeffs, int degree){
		this.coeffs = coeffs;
		this.degree = degree;
	}
	
	public String nrSign(double nr){
		if(nr > 0)
			return "+" + nr;
		else
			return "" + nr;
	}
	
	public String toString() {
		StringBuilder temp = new StringBuilder();
		int ind = degree-1;
		
		for(int i = degree - 1; i >= 0; i--) {
			if(coeffs[i] != 0) {
				temp.append(nrSign(coeffs[i]) + "x^" + ind);
			}
			ind-=1;
		}
		
		return temp.toString();
	}
	
}
