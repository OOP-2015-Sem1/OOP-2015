
public class Main_1001 {

	public static void main(String[] args) {
		int i;
		PieceHolder.storePiece(PieceHolder.hold);
		// for (i = 0; i < 19; i++) {
		// Piece.printPiece(PieceHolder.hold[i]);

		// }
		int[][] a = new int[][] { { 3, 1, 0, 1, 1, 0, 1, 2 ,1 ,1 }, 
								  { 3, 0, 5, 5, 5, 0, 1, 2 ,1 ,1 },
								  { 1, 0, 1, 0, 5, 0, 3, 2 ,1 ,1 }, 
								  { 0, 1, 0, 1, 0, 4, 1, 5 ,1 ,1 },
								  { 1, 0, 1, 0, 1, 0, 1, 2 ,1 ,0 },
								  { 3, 1, 0, 1, 1, 1, 1, 2 ,1 ,1 }, 
								  { 3, 0, 5, 5, 5, 1, 1, 2 ,1 ,1 },
								  { 0, 1, 0, 0, 5, 1, 3, 2 ,1 ,1 }, 
								  { 0, 1, 0, 1, 0, 4, 1, 5 ,1 ,1 },
								  { 0, 1, 0, 0, 1, 1, 1, 2 ,1 ,0 }};
		/*
		 * int score=0; score=BackMatrix.deleteMatrix(a, score); for ( i = 0; i
		 * <= 4; i++) { for (int j = 0; j <= 4; j++) { System.out.print(a[i][j]+
		 * " "); } System.out.println(); } System.out.println();
		 * System.out.println("scorul este: "+ score);
		 */
		Piece[] randomPiece = new Piece[3];
		PieceHolder.generatePieces(randomPiece);
		for (i = 0; i < 3; i++) {
			Piece.printPiece(randomPiece[i]);
		}
		if(BackMatrix.gameOver(randomPiece, a)==true){
			System.out.println("true");
			
		}
		else System.out.println("game over");
	}

}
