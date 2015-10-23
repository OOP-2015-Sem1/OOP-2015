package assignment22;

import java.math.BigDecimal;

public class MatrixOperations {
	
	private static final MatrixOperations instance = new MatrixOperations();
	
	public static MatrixOperations getInstance() {
		return instance;
	}// after implementing and using a singleton pattern the methods don't need to be static anymore because even if we create multiple objects,
	// all the objects created will contain a reference to the unique instance of the class

	public int validDimensions(BigDecimal[][] m1, BigDecimal[][] m2) {

		if (m1.length != m2.length)
			return 0;
		else if (m1[0].length != m2[0].length)
			return 0;
		else
			return 1;
	}

	public BigDecimal[][] add(BigDecimal[][] a, BigDecimal[][] b) {

		if (validDimensions(a, b) == 0)
			System.out.println("The matrices don't have the same dimensions");

		BigDecimal r[][] = new BigDecimal[a.length][a[0].length];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++) {
				r[i][j] = a[i][j].add(b[i][j]);
			}

		return r;
	}

	public  BigDecimal[][] subtract(BigDecimal[][] a, BigDecimal[][] b) {

		if (validDimensions(a, b) == 0)
			System.out.println("The matrices don't have the same dimensions");

		BigDecimal r[][] = new BigDecimal[a.length][a[0].length];

		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < a[0].length; j++) {
				r[i][j] = a[i][j].subtract(b[i][j]);
			}

		return r;
	}
	
	public BigDecimal[][] multiply(BigDecimal[][] a, BigDecimal[][] b) {
		
		int m = a.length;
		int n = a[0].length;
		int p = b.length;
		int q = b[0].length;
		
		if(n != p) {
			System.out.println("The matrices can't be multyplied");
		}
		
		BigDecimal[][] r = new BigDecimal[m][q];
		
		BigDecimal sum = new BigDecimal(0);
		BigDecimal o = new BigDecimal(0);
		
		 for (int c = 0 ; c < m ; c++ )
         {
            for (int  d = 0 ; d < q ; d++ )
            {   
               for (int k = 0 ; k < p ; k++ )
               {
                  sum = sum.add(a[c][k].multiply(b[k][d]));
               }
 
               r[c][d] = sum;
               sum = o;
            }
         }
	return 	r;
	}
	
	public BigDecimal[][] multByScalar(BigDecimal [][] a, BigDecimal scalar) {
		
		int m  = a.length;
		int n = a[0].length;
		
		for(int i = 0; i < m; i++)
			for(int j = 0; j < n; j++)
				a[i][j] = a[i][j].multiply(scalar);
		
		return a;
	}
	
	public  void print(BigDecimal[][] a) {

		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}

	}
	
	public BigDecimal determinant(BigDecimal[][] a) {
		
		if(a.length != a[0].length) 
			System.out.println("The matrix isn't square");
	
		
		BigDecimal det = new BigDecimal(0);
		int n = a.length;
		int col = 0;
		
		if(n == 1)
			return a[0][0];
		else {
			if(n == 2)
				return a[0][0].multiply(a[1][1]).subtract(a[1][0].multiply(a[0][1]));
			else {
				BigDecimal[][] m = new BigDecimal[n-1][n-1];
				for(int j = 0; j<n; j++) {
					for(int k = 1; k<n; k++) {
						col = 0;
						for(int p = 0; p<n; p++) {
							if(j == p)
								continue;
							m[k-1][col] = a[k][p];
							col += 1;
						}
					}
					BigDecimal temp = new BigDecimal(Math.pow(-1.0, j) * a[0][j].intValue());
					//print(m);
					//System.out.println(temp);
					//System.out.println();
					det = det.add(determinant(m).multiply(temp));
				}
			}
		}
		
		return det;
	}
	
	public boolean areEqual(BigDecimal[][] a, BigDecimal[][] b) {
		
		if(validDimensions(a, b) == 0)
			System.out.println("The matrices can't be compared");
		
		int n = a.length;
		int m = a[0].length;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j] != b[i][j])
					return false;
		return true;
	}
	
	public boolean isZeroMatrix(BigDecimal[][] a) {
		
		int n = a.length;
		int m = a[0].length;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j].intValue() != 0)
					return false;
		return true;
		
	}
	
	public boolean isIdentity(BigDecimal [][] a) {
		int n = a.length;
		int m = a[0].length;
		if(n != m)
			System.out.println("It isn't an Identity matrix because it isn't square");
		
		for(int i = 0; i<n; i++) {
			for(int j = 0; j<n; j++) {
				if(a[i][j].intValue() != 0 && i != j)
					return false;
			}
		}
		
		return true;
	}
	
	public double fillDegree(BigDecimal[][] a) {
		
		int n = a.length;
		int m = a[0].length;
		
		double nr=0;
		double total = n * m;
		
		for(int i = 0; i < n; i++)
			for(int j = 0; j < m; j++)
				if(a[i][j].intValue() == 0)
					nr += 1;
		
		return nr/total * 100;
	}
	
	public void solveSystem(BigDecimal[][] x) { // suppose that the last column contains the numbers after '='
		// also suppose that the main determinant != 0
		double a, b, c;
		
		int n = x.length;
		int m = x[0].length;
		
		BigDecimal[][] tempM = new BigDecimal[n][m-1];
		
		double detP, detA, detB, detC;
		
		int i, j;
		
		for(i = 0; i < n; i++)
			for(j = 0; j<m-1; j++)
				tempM[i][j] = x[i][j];
		detP = determinant(tempM).doubleValue();
		
		for(i = 0; i<n; i++)
			for(j = 0; j<m-1; j++)
				if(j != 0)
					tempM[i][j] = x[i][j];
		for(i = 0; i <n; i++)
			tempM[i][0] = x[i][n];
		
		detA = determinant(tempM).doubleValue();
		
		for(i = 0; i<n; i++)
			for(j = 0; j<m-1; j++)
				if(j != 1)
					tempM[i][j] = x[i][j];
		for(i = 0; i <n; i++)
			tempM[i][1] = x[i][n];
		
		detB = determinant(tempM).doubleValue();
		
		for(i = 0; i<n; i++)
			for(j = 0; j<m-1; j++)
				if(j != 2)
					tempM[i][j] = x[i][j];
		for(i = 0; i <n; i++)
			tempM[i][2] = x[i][n];
		
		detC = determinant(tempM).doubleValue();
		
		a = detA / detP;
		b = detB / detP;
		c = detC / detP;
		
		System.out.println("The solutions of the system are:");
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
	}
	
}
