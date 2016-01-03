package pack1;

public class Matrix {
	
	private static int mat[][];
	private static int dimension;
	
	
	private static void moveMatrix() {
		dimension += 2;
		for (int i = dimension - 2; i > 0; i--) {
			for (int j = dimension - 2; j > 0; j--) {
				mat[i][j] = mat[i - 1][j - 1];
			}
		}
	}

	private static void insertNewLayer(){
		int lastValue=mat[dimension-1][dimension-1]+1; //stores the last value inserted in the matrix;
		moveMatrix();
		for(int i=dimension-2; i>=0; i--){  //fill last column;
			mat[i][dimension-1]=lastValue;
			lastValue++;	
		}
		
		for(int j=dimension-2; j>=0; j--){  //fill first row;
			mat[0][j]=lastValue;
			lastValue++;
		}
		
		for(int i=1; i<dimension; i++){  //fill first column;
			mat[i][0]=lastValue;
			lastValue++;
		}
		
		for(int j=1; j<dimension; j++){  //fill last row;
			mat[dimension-1][j]=lastValue;
			lastValue++;
		}
		
	}

	public void printMatrix() {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				System.out.printf("%2d ", mat[i][j]);
			}
			System.out.println();
		}
	}
	
	private static boolean isPrime(int number){
		int divider;
		if (number == 1){
			return false;
		}
		for (divider=2; divider<=number/2; divider++){
			if(number%divider==0){
				return false;
			}
		}
		return true;
	}
	
	public Matrix(){
		mat=new int[1000][1000];
		mat[0][0]=1;
		dimension=1;
	}
	
	private static int countPrime(){
		int counter=0;
		for(int i=0; i<dimension; i++){
			if(isPrime(mat[i][dimension-i-1])){
				counter++;
			}
			if(isPrime(mat[i][i])){
					counter++;
				}
			}
		return counter;
	}

	public int getDimension(){
		return dimension;
	}
	
	private static float ratio(){		
		return (countPrime()*100)/(2*dimension-1);
	}
	
	public void computeLayers(){
		int counter=0;
		do{
			insertNewLayer();
			counter++;
			System.out.printf("Inserting layer number %d. Ratio is: %f\n",counter, ratio());
		}
		while (ratio()>10);
	}
}
