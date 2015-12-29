import java.util.Scanner;
public class Main_1001 {

	public static void main(String[] args) {
		Scanner read= new Scanner(System.in);
		int i;
		PieceHolder.storePiece(PieceHolder.hold);
		int[][] a = new int[][] { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, 
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
								  { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } 
								  };
		
								  
		int score = 0;
		int dimension;
		int index,startI,startJ;
		Piece[] randomPiece=new Piece[3];
		PieceHolder.generatePieces(randomPiece);
		dimension = 3;
		for(i = 0; i <= 2;i++){
			Piece.printPiece(randomPiece[i]);
			
			}
		while(BackMatrix.gameOver(randomPiece, a)==true){
			print(a, score);
			if(dimension==0){
				PieceHolder.generatePieces(randomPiece);
				dimension = 3;
				for(i = 0; i <= 2;i++){
					Piece.printPiece(randomPiece[i]);
					
					}
			}
			System.out.println("Choose a piece(1-3): ");
			index=read.nextInt();
			System.out.println("Scrieti o valoare pentru axa i: ");
			startI = read.nextInt(); // indexul i din matrix unde sa fie pusa piesa
			System.out.println("insert a value for J: ");
			startJ = read.nextInt(); // indexul j din matrix unde sa fie pusa piesa
			
			while(BackMatrix.movePiece(a, randomPiece[index], startI, startJ)==false){
				System.out.println("not good indexes:  ");
				System.out.println("Scrieti o valoare pentru axa i: ");
				startI = read.nextInt(); // indexul i din matrix de unde se cauta
				System.out.println("insert a value for J: ");
				startJ = read.nextInt(); // indexul j din matrix de unde se cauta
			}
			
			System.out.println();
			print(a, score);
			emptyP(randomPiece[index]);
			dimension--;
		}
		System.out.println("Game Over");
		System.out.println("scorul este:"+score);
	}
	static void print(int [][] matrix ,int score){
		score=BackMatrix.deleteMatrix(matrix, score);
		for ( int i = 0; i <= 9; i++) {
			for (int j = 0; j <= 9; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println("scorul este: " + score);

	}
	static void emptyP(Piece randomPiece){
		int indexI,indexJ;
		indexI=randomPiece.getDimI();
		indexJ=randomPiece.getDimJ();
		int [][] temp= new int[indexI][indexJ];
		temp= randomPiece.getForm();
		for(int i = 0 ; i< indexI ; i++){
			for( int j = 0; j < indexJ ; j++){
				temp[i][j]=0;
			}
		}
		randomPiece.setForm(temp);
	}

}
