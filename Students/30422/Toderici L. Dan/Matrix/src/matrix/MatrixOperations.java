package matrix;
import java.math.BigDecimal;

public class MatrixOperations 
{
	
	private static MatrixOperations singeltonInstance = null;
	
	private MatrixOperations()
	{
		
	}
	
	public static MatrixOperations getInstance() 
	{
		if(singeltonInstance == null)
		{
			singeltonInstance = new MatrixOperations();
		}
		return singeltonInstance;
	}
	
	
	
	public BigDecimal[][] add( BigDecimal[][] firstMatrix, BigDecimal[][] secondMatrix )
	{
		BigDecimal[][] result = new BigDecimal[firstMatrix.length][firstMatrix.length];

		for( int i = 0; i < firstMatrix.length; i ++ )
		{
			for( int j =0; j < firstMatrix.length; j ++ )
			{
				result[i][j] = firstMatrix[i][j].add(secondMatrix[i][j]);
			}
		}

		return result;

	}

	public BigDecimal[][] subtract( BigDecimal[][] firstMatrix, BigDecimal[][] secondMatrix )
	{
		BigDecimal[][] result = new BigDecimal[firstMatrix.length][firstMatrix.length];

		for( int i = 0; i<firstMatrix.length; i ++ )
		{
			for( int j = 0; j<firstMatrix.length; j ++ )
			{
				result[i][j] = firstMatrix[i][j].subtract(secondMatrix[i][j]);
			}
		}

		return result;

	}

	public BigDecimal[][] multiply( BigDecimal[][] firstMatrix, BigDecimal[][] secondMatrix )
	{
		BigDecimal[][] result = new BigDecimal[firstMatrix.length][firstMatrix.length];

		for( int i = 0; i < firstMatrix.length; i ++ )
		{
			for( int j = 0; j < firstMatrix.length; j ++ )
			{
				result[i][j] = BigDecimal.ZERO;
			}
		}

		for( int i = 0; i < firstMatrix.length; i ++ )
		{
			for( int j =0; j < firstMatrix.length; j ++ )
			{
				for( int t=0; t < firstMatrix.length; t ++ )
				{
					result[i][j] = result[i][j].add(firstMatrix[i][t].multiply(secondMatrix[t][j]));
				}
			}
		}

		return result;

	}

	public BigDecimal[][] multiplyScalar( BigDecimal[][] matrix, BigDecimal scalarValue )
	{
		BigDecimal[][] result = new BigDecimal[matrix.length][matrix.length];

		for( int i = 0; i < matrix.length; i ++ )
		{
			for( int j =0; j < matrix.length; j ++ )
			{
				result[i][j] = matrix[i][j].multiply(scalarValue);
			}
		}

		return result;

	}

	public boolean areEqual( BigDecimal[][] matrix1, BigDecimal[][] matrix2 )
	{
		for( int i = 0; i < matrix1.length; i++ )
		{
			for( int j = 0; j < matrix1.length; j++ )
			{
				if( matrix1[i][j].compareTo(matrix2[i][j]) !=0 )
				{
					return false;
				}
			}
		} 

		return true;
	}

	public boolean isIdentityMatrix( BigDecimal[][]matrix )
	{
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix.length; j++ )
			{
				if( ( matrix[i][j].equals(BigDecimal.ONE) == false ) && ( i == j ) )
				{
					return false;
				}
				else
				{
					if( ( i != j ) && ( matrix[i][j].equals(BigDecimal.ZERO) == false ) )
					{
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean isZeroMatrix( BigDecimal[][]matrix )
	{
		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix.length; j++ )
			{
				if( matrix[i][j].equals(BigDecimal.ZERO) == false )
				{
					return false;
				}
			}
		}

		return true;
	}

	public BigDecimal fillDegree( BigDecimal[][]matrix )
	{
		BigDecimal result = new BigDecimal(1);
		BigDecimal nbOfElements = new  BigDecimal( matrix.length * matrix.length);
		BigDecimal nbOfZeroElements = new BigDecimal(0);


		for( int i = 0; i < matrix.length; i++ )
		{
			for( int j = 0; j < matrix.length; j++ )
			{
				if( matrix[i][j].equals(BigDecimal.ZERO) )
				{
					nbOfZeroElements = nbOfZeroElements.add(BigDecimal.ONE);

				}
			}
		}

		if( !nbOfZeroElements.equals(BigDecimal.ZERO) )
		{
			result = nbOfZeroElements.divide(nbOfElements);
		}
		return result;

	}

	public BigDecimal determinant(BigDecimal[][] matrix) {
		BigDecimal result = BigDecimal.ZERO;
		BigDecimal aux;

		if (matrix.length == 1) {
			return matrix[0][0];
		}
		for (int i = 0; i < matrix.length; i++) {
			BigDecimal[][] reducedMatrix = new BigDecimal[matrix.length - 1][matrix.length - 1];
			for (int a = 1; a < matrix.length; a++) {
				for (int b = 0; b < matrix.length; b++) {
					if (b < i) {
						reducedMatrix[a - 1][b] = matrix[a][b];
					} else if (b > i) {
						reducedMatrix[a - 1][b - 1] = matrix[a][b];
					}
				}
			}
			if (i % 2 == 0) {
				aux = BigDecimal.ONE;
			} else {
				aux = BigDecimal.ZERO.subtract(BigDecimal.ONE);
			}
			result = result.add(aux.multiply(matrix[0][i]).multiply(determinant(reducedMatrix)));
		}
		return result;
	}

	static void prettyPrintMatrix (BigDecimal[][] matrix)
	{
		for( int i = 0; i < matrix.length; i ++ )
		{
			for( int j = 0; j < matrix.length; j ++ )
			{
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}


}
