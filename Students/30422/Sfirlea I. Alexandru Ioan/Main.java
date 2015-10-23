import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		PieceHolder test = new PieceHolder();
		Pieces[] sir=new Pieces[20];
		int[][] a=new int[1][1];
		a[0][0]=1;
		Pieces aux= new Pieces("a",a,1,1,1);
		test.generatePiece(sir);
		for (int i = 0; i < sir.length; i++) {
			aux.printer(sir[i]);
		}
	}

	
}
