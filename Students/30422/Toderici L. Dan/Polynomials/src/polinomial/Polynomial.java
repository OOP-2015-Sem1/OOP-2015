package polinomial;

import java.util.*;

public class Polynomial {

	int[] coefficientsOfPolynomial;
	int degreeOfPolynomial;

	void setCoefficientsFromFile(Scanner fileScanner)
	{
		String lineFromFile = fileScanner.nextLine();

		String[] coefficientInReverseOrder = lineFromFile.split(" ");
		degreeOfPolynomial = coefficientInReverseOrder.length -1;

		this.coefficientsOfPolynomial = new int [this.degreeOfPolynomial + 1];


		for(int index = coefficientInReverseOrder.length -1; index >= 0; index--)
		{
			coefficientsOfPolynomial[degreeOfPolynomial - index] = Integer.parseInt(coefficientInReverseOrder[index]);
		}

	}

	void showPolinom()
	{
		if(degreeOfPolynomial >=0)
		{
			for(int index = degreeOfPolynomial; index>0; index--)
			{
				if(coefficientsOfPolynomial[index]!=0) 
				{
					if(coefficientsOfPolynomial[index]>0 && index !=degreeOfPolynomial)
					{
						System.out.print(" +");
					}
					System.out.print(coefficientsOfPolynomial[index] + "x" );
					if(index!=1)
					{
						System.out.print("^"+index);
					}
				}


			}
		}
		if(coefficientsOfPolynomial[0]!=0)
		{
			if(coefficientsOfPolynomial[0]>0)
			{
				System.out.print(" + "+coefficientsOfPolynomial[0]);
			}
			else
			{
				System.out.print(" "+coefficientsOfPolynomial[0]);
			}
		}
	}





}
