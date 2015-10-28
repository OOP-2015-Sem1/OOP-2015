package polinomial;
import java.lang.Math;

public class PolynomOperations {

	Polynomial result = new Polynomial();

	Polynomial addPolynoms(Polynomial a ,Polynomial b)
	{

		if(a.degreeOfPolynomial>b.degreeOfPolynomial)
		{
			result.degreeOfPolynomial = a.degreeOfPolynomial;
		}
		else
		{
			result.degreeOfPolynomial = b.degreeOfPolynomial;
		}
		result.coefficientsOfPolynomial = new int[result.degreeOfPolynomial +1];


		for(int index = 0; index <= result.degreeOfPolynomial ;index ++)
		{
			if(a.degreeOfPolynomial >= index && b.degreeOfPolynomial >=index)
			{
				result.coefficientsOfPolynomial[index] = a.coefficientsOfPolynomial[index] + b.coefficientsOfPolynomial[index];

			}
			else
			{
				if(a.degreeOfPolynomial >=index && b.degreeOfPolynomial <index)
				{
					result.coefficientsOfPolynomial[index] = a.coefficientsOfPolynomial[index];

				}
				else
				{
					result.coefficientsOfPolynomial[index] = b.coefficientsOfPolynomial[index];

				}
			}


		}

		return result;
	}

	Polynomial substractPolynoms(Polynomial a ,Polynomial b)
	{

		if(a.degreeOfPolynomial>b.degreeOfPolynomial)
		{
			result.degreeOfPolynomial = a.degreeOfPolynomial;
		}
		else
		{
			result.degreeOfPolynomial = b.degreeOfPolynomial;
		}
		result.coefficientsOfPolynomial = new int[result.degreeOfPolynomial +1];


		for(int index = 0; index <= result.degreeOfPolynomial ;index ++)
		{
			if(a.degreeOfPolynomial >= index && b.degreeOfPolynomial >=index)
			{
				result.coefficientsOfPolynomial[index] = a.coefficientsOfPolynomial[index] - b.coefficientsOfPolynomial[index];

			}
			else
			{
				if(a.degreeOfPolynomial >=index && b.degreeOfPolynomial <index)
				{
					result.coefficientsOfPolynomial[index] = a.coefficientsOfPolynomial[index];

				}
				else
				{
					result.coefficientsOfPolynomial[index] = - b.coefficientsOfPolynomial[index];

				}
			}


		}

		return result;
	}

	Polynomial multiplyPolynoms(Polynomial a ,Polynomial b)
	{

		result.degreeOfPolynomial = a.degreeOfPolynomial + b.degreeOfPolynomial;
		result.coefficientsOfPolynomial = new int[result.degreeOfPolynomial +1];

		for(int index1 = 0; index1 <= a.degreeOfPolynomial ;index1 ++)
		{
			for(int index2 = 0; index2 <= b.degreeOfPolynomial ; index2 ++)
			{
				result.coefficientsOfPolynomial[index1 + index2] = result.coefficientsOfPolynomial[index1 + index2] + ( a.coefficientsOfPolynomial[index1] * b.coefficientsOfPolynomial[index2] ) ;
			}
		}
		return result;
	}
	
	Polynomial multiplyByScalar(Polynomial a, int scalar)
	{
		result.degreeOfPolynomial = a.degreeOfPolynomial;
		for(int index = 0; index <= a.degreeOfPolynomial; index++)
		{
			result.coefficientsOfPolynomial[index] = a.coefficientsOfPolynomial[index] * scalar;
		}
		
		return result;
	}
	
	double evaluatePolynom(Polynomial a, int value)
	{
		double result = 0;
		
		for(int index = 0; index <= a.degreeOfPolynomial; index++)
		{
			result = result + a.coefficientsOfPolynomial[index] * Math.pow(value, index);
		}
		return result;
	}
	
	Polynomial[] division(Polynomial a, Polynomial b)
	{
		Polynomial [] parts = new Polynomial[2];
		parts[0] = new Polynomial();
		parts[0].degreeOfPolynomial = a.degreeOfPolynomial - b.degreeOfPolynomial + 1 ;
		parts[0].coefficientsOfPolynomial = new int[a.degreeOfPolynomial -b.degreeOfPolynomial +1];
		parts[1] = new Polynomial();
		parts[1].degreeOfPolynomial = b.degreeOfPolynomial - 1 ;
		parts[1].coefficientsOfPolynomial = new int[b.degreeOfPolynomial -1];
		
		Polynomial divider = new Polynomial();
		divider = b;
		Polynomial divident= new Polynomial();
		divident = a;
		
		Polynomial auxPolynomial = new Polynomial();
		auxPolynomial.degreeOfPolynomial = 1;
		auxPolynomial.coefficientsOfPolynomial[0] =  0;
		auxPolynomial.coefficientsOfPolynomial[1] =  1;
		int auxDegreeOfA = a.degreeOfPolynomial;
		int auxDegreeOfB = b.degreeOfPolynomial;
		
		if(a.degreeOfPolynomial > 0)
		{
			while(divident.degreeOfPolynomial >= b.degreeOfPolynomial)
			{
				for(int index = 0; index < auxDegreeOfA -auxDegreeOfB ; index ++)
				{
					divider = multiplyPolynoms(divider, auxPolynomial);
				}
				parts[0].coefficientsOfPolynomial[divident.degreeOfPolynomial - b.degreeOfPolynomial] = divident.coefficientsOfPolynomial[divident.degreeOfPolynomial ] / divider.coefficientsOfPolynomial[divider.degreeOfPolynomial ];
				divider = multiplyByScalar(divider, parts[0].coefficientsOfPolynomial[divident.degreeOfPolynomial - b.degreeOfPolynomial]);
				divident = substractPolynoms(divident, divider);
				divident.degreeOfPolynomial = divident.degreeOfPolynomial - 1;
				divider = b ;
				auxDegreeOfA --;
			}
		}
		parts[1] = substractPolynoms(a, multiplyPolynoms(parts[0], divider)) ;
		
		return parts;
	}
	
}


