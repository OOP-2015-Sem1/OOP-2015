package assignment2;

//import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.security.Signature;

public class Functions {
	
	public Polynomial addition(Polynomial p1, Polynomial p2) {
		
		int newDegree= Math.max(p1.degree, p2.degree);
		int ind = Math.min(p1.degree, p2.degree);
		double[] coeff = new double[newDegree];
		
		for(int i = 0; i < ind; i++){
			coeff[i] = p1.coeffs[i] + p2.coeffs[i];
		}
		
		if(p1.degree > p2.degree)
			for(int i = ind; i < p1.degree; i++) {
				coeff[i] = p1.coeffs[i];
			}
		else
			for(int i = ind; i < p2.degree; i++) {
				coeff[i] = p2.coeffs[i];
			}
		
		Polynomial p = new Polynomial(coeff, newDegree);
		
		return p;
	}
	
	public Polynomial substraction(Polynomial p1, Polynomial p2) {
		
		int newDegree= Math.max(p1.degree, p2.degree);
		int ind = Math.min(p1.degree, p2.degree);
		double [] coeff = new double[newDegree];
		
		for(int i = 0; i < ind; i++){
			coeff[i] = p1.coeffs[i] - p2.coeffs[i];
		}
		
		if(p1.degree > p2.degree)
			for(int i = ind; i < p1.degree; i++) {
				coeff[i] = p1.coeffs[i];
			}
		else
			for(int i = ind; i < p2.degree; i++) {
				coeff[i] = p2.coeffs[i];
			}
		
		Polynomial p = new Polynomial(coeff, newDegree);
		
		return p;
	}
	
	public Polynomial multByScalar(Polynomial p1, double scalar) {
		int newDegree = p1.degree;
		double [] coeff = new double[newDegree];
		
		for(int i = 0; i < newDegree; i++){
			coeff[i] = p1.coeffs[i] * scalar;
		}
		
		Polynomial p = new Polynomial(coeff, newDegree);
		
		return p;
	}
	
	public Polynomial multiplication(Polynomial p1, Polynomial p2) {
		
		int newDegree= p1.degree + p2.degree -1;
		double [] coeff = new double[newDegree];
		
		
		for(int i = 0; i < p1.degree; i++){
			for(int j = 0; j < p2.degree; j++) {
				coeff[i + j] += p1.coeffs[i] * p2.coeffs[j];
			}
		}
		
		Polynomial p = new Polynomial(coeff, newDegree);
		
		return p;
	}
	
	public double evaluate(Polynomial p, double n){
		
		double sum = 0;
		
		for(int i = 0; i < p.degree; i++) {
				sum += p.coeffs[i] * Math.pow(n, i);
		}
		
		String temp = String.format("%.2f", sum);
		sum = Double.parseDouble(temp); // format the number such that it will have 2 decimal digits 
		
		return sum;
		
	}
	
	public int getPower(double x, double y){
		return (int) Math.abs(x - y);
	}
	
	public double getCoeff(double x, double y) {
		double temp = x/y;
		temp = (int) temp * 100;
		return temp /100;
	}
	
	public void fillCoeffs(int index, double[] coeffs) {
		for(int i = index; i>=0; i--) {
			coeffs[i] = 0;
		}
	}
	
	public Polynomial divide(Polynomial p1, Polynomial p2, Polynomial pr) {
		
		int index = getPower(p1.degree-1, p2.degree-1);
		double multNr = getCoeff(p1.coeffs[p1.degree-1],  p2.coeffs[p2.degree-1]);
		double tempCoeffs[] = new double[index + 1];
		
		fillCoeffs(index, tempCoeffs);
		tempCoeffs[index] = multNr;
		
		Polynomial tempPolynomial = new Polynomial(tempCoeffs, index + 1); // the polynomial that contains the temporary result
		
		pr.coeffs[index] = multNr;
		
		Polynomial remainder;
		
		if(index != 0) {
			remainder = multiplication(tempPolynomial, p2);
		}
		else {
			remainder = multByScalar(p2, multNr);
		}
		
		remainder = substraction(p1, remainder);
		
		return remainder;
	}
	
	public void updateDegree(Polynomial p) {
		
		int aux = p.degree-1;
		
		for(int i = aux; i >= 0; i--) {
			if((int)p.coeffs[i] == 0)
				p.degree-=1;
			else
				break;
		}
		
	}
	
	public void divideMaster(Polynomial p1, Polynomial p2, PrintWriter write) {
		
		if(p1.degree <= p2.degree) {
			write.println("The polynom 1 can't be divided by polynom 2");
			return;
		}
		
		int newDegree = Math.abs(p1.degree - p2.degree) + 1;
		double coeffs[] = new double[newDegree + 1];
		
		fillCoeffs(newDegree, coeffs);
		 
		Polynomial pr = new Polynomial(coeffs, newDegree);
		
		Polynomial remainder = divide(p1, p2, pr);
		
		while(remainder.degree >= p2.degree) {
			updateDegree(remainder);
				if(remainder.degree >= p2.degree)
			remainder = divide(remainder, p2, pr);
		}
		
		write.println("The division has been done");
		write.println("The result is:" + pr );
		write.println("The remainder is:" + remainder);
	}
	
	public int sign(double x) {
		if(x >= 0)
			return 1;
		else
			return -1;
	}
	
	public double findRoot(Polynomial p, double a, double b) {
		double valA, valB, valC;
		valA = evaluate(p, a);
		valB = evaluate(p, b);
		double c = (a + b) /2;
		valC = evaluate(p, c);
		
		while(valC != 0.00) {
			if(sign(valA) == sign(valC))
				a = c;
			else
				b = c;
			c = (a + b) /2;
			valA = evaluate(p, a);
			valC = evaluate(p, c);
		}
		
		return c;
	}
	
	public void roots(Polynomial p, PrintWriter write){
		
		System.out.print("The roots of the polynomial are: ");
		
		double root;
		int minValue = -1000000, maxValue = 1000000;
		double step = 1;
		
		for(int i = minValue; i <= maxValue; i++) {
			double a = evaluate(p, i);
			double b = evaluate(p, i+step);
			
			if(sign(a) != sign(b)) {
				root = findRoot(p, i, i+step);
				System.out.println(root + "  ");
				i += 1;
			}
		}
	}
	
	public void mathematicalForm(String str) {
		String s[] = str.split("\\+?\\d+x\\^");
		
		System.out.println(s);
	}
}
