package chapter2.assignment2;

/**
 * 
 * @author Alexandru
 *
 */
public class TestMatrixOperations {
	public static void main(String[] args) {
		MatrixOperations op = MatrixOperations.getInstance();
		Operations f = new Operations();
		f.action(op);
	}
}
