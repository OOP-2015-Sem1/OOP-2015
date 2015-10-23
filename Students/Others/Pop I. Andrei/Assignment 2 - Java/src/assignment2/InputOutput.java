package assignment2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class InputOutput {
	
	public Polynomial p1, p2, pr;
	
	public Polynomial createPolynomial(String line){
		
		/*
		String[] elemArray = line.split(" ");
		double [] coeffs = new double[elemArray.length];
		int nr = 0;
		
		for(int i = elemArray.length-1; i >= 0; i--) {
			coeffs[nr++] = Integer.parseInt(elemArray[i]);
		}
		*/ // the old way of reading
		
		String text[]= line.split("\\+|\\-");
		double[] coeffs = new double[text.length];
		boolean first = true;
		
		int index, coeff, degree = 0;;
		
		for(String i : text) {
			String num[] = i.split("\\^");
			if(num.length > 1) {
				coeff = Integer.parseInt(num[0].substring(0,1));
				index = Integer.parseInt(num[1]);
				coeffs[index] = coeff;
			}
			else {
				if(num[0].length() > 1 || num[0].equals("x")){
					if(num[0].equals("x"))
						coeff = 1;
					else
						coeff = Integer.parseInt(num[0].substring(0,1));
					index = 1;
					coeffs[index] = coeff;
				}
				else {
					coeff = Integer.parseInt(num[0].substring(0, 1));
					index = 0;
					coeffs[index] = coeff;
			
				}
			}
			if(first) {
				degree = index + 1;
				first = false;
			}
		}
		
		Polynomial p = new Polynomial(coeffs, degree);
		return p;
	}
	
	public void getCommand(String line,  PrintWriter write) {
		String[] ElemArray = line.split(" ");
		String command = ElemArray[0];
		Functions function = new Functions();
		
		if(command.equals("ADD")) {
			pr = function.addition(p1, p2);
			write.print("The addition was performed and the result is:" + pr);
			write.println();
		}
		else {
			if(command.equals("SUBTRACT")) {
				pr = function.substraction(p1, p2);
				write.println("The subtraction was performed and the result is:" + pr);
			}
			else {
				if(command.equals("MULTIPLY")) {
					pr = function.multiplication(p1, p2);
					write.println("The multiplication was performed and the result is:" + pr);
				}
				else {
					if(command.equals("MUL_SCAL")) {
						int scalar = Integer.parseInt(ElemArray[1]);
						pr = function.multByScalar(p1, scalar);
						write.println("The multiplication by scalar was performed and the result is:" + pr);
					}
					else {
						if(command.equals("EVAL")) {
							int n = Integer.parseInt(ElemArray[1]);
							write.println("The result of the EVAL" + n + "expression is:" +function.evaluate(p1,n) + function.evaluate(p2, n));
						}
						else {
							if(command.equals("DIVIDE"))
								function.divideMaster(p1, p2, write);
							else
								function.roots(p1, write); 
						}
					}
				}
			}
		}
	}
		
	
	public void readWriteFile(File filename) {
		
		Scanner read = null;
		PrintWriter write= null;
		
		try {
			write = new PrintWriter("output.txt");
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}

		try
		{
			 read = new Scanner(filename);
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		
		String line;
		int nrLines = 0;
		
		while(read.hasNextLine()) {
			line = read.nextLine();
			if(nrLines == 0)
				p1 = createPolynomial(line);
			else {
				if(nrLines == 1)
					p2 =  createPolynomial(line);
				else {
					getCommand(line, write);
				}
			}
			nrLines += 1;
		}
		write.close();
	}
}
