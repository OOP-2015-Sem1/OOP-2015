package polinomial;
import java.io.*;
import java.util.*;


public class Main {

	public static void main(String[] args) {

		Scanner fileScanner = null ;
		try {
			fileScanner = new Scanner(new File("pol.txt"));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		Polynomial a = new Polynomial();
		a.setCoefficientsFromFile(fileScanner);
		a.showPolinom();

		System.out.println();

		Polynomial b = new Polynomial();
		b.setCoefficientsFromFile(fileScanner);
		b.showPolinom();

		System.out.println();
		fileScanner.nextLine();

		PolynomOperations operation = new PolynomOperations();
		Polynomial resultOfOperation = new Polynomial();




		while(fileScanner.hasNextLine())
		{
			System.out.println();
			String instruction = fileScanner.nextLine();
			
			int value = 0 ;
			int resultOfEvaluateInstruction = 0;
			
			if(instruction.contains(" "))
			{
				String [] instructionSplitted = instruction.split(" ");
				instruction = instructionSplitted[0];
				value = Integer.parseInt(instructionSplitted[1]);
				//System.out.println(instructionSplitted[0]+" "+instructionSplitted[1]);
			}
			switch (instruction) {
			case "ADD" :
				System.out.println("Result of adding the 2 polynomials :");
				resultOfOperation = operation.addPolynoms(a, b);
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				break;
			case "SUBSTRACT" :
				System.out.println("Result of substracting the second polynomial from the first :");
				resultOfOperation = operation.substractPolynoms(a, b);
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				System.out.println("Result of substracting the first polynomial from the second :");
				resultOfOperation = operation.substractPolynoms(b, a);
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				break;
			case "MULTIPLY" :
				System.out.println("Results of multiplying the 2 polynomials :");
				resultOfOperation = operation.multiplyPolynoms(a, b);
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				break;
			case "MUL_SCAL" :
				System.out.println("Results of multiplying with  scalar "+value);
				resultOfOperation = operation.multiplyByScalar(a, value);
				System.out.println("first polynomial :");
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				System.out.println("second polynomial :");
				resultOfOperation = operation.multiplyByScalar(b, value);
				resultOfOperation.showPolinom();
				
				System.out.println();
				
				break;
			case "EVAL" :
				System.out.println("Results of evaluating the first polynomial with value : " + value);
				resultOfEvaluateInstruction = (int) operation.evaluatePolynom(a, value);
				System.out.println("Result: "+resultOfEvaluateInstruction);
				
				System.out.println();
				
				System.out.println("Results of evaluating the second polynomial with value : " + value);
				resultOfEvaluateInstruction = (int) operation.evaluatePolynom(b, value);
				System.out.println("Result: "+resultOfEvaluateInstruction);
				
				System.out.println();
				
				break;
				
			}
			

		}

	}
}






