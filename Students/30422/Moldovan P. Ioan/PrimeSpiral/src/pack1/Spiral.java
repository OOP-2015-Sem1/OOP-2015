package pack1;

public class Spiral {
	public static void main(String[] args) {
		Matrix matrix=new Matrix();
		matrix.computeLayers();
		matrix.printMatrix();
		System.out.printf("The number of layers is: %d",matrix.getDimension());
	}

}
