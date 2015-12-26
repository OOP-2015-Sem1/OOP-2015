
import java.util.Scanner;

public class BackMatrix {
	private static final int LENGHT = 10;

	public static int deleteMatrix(int[][] matrix, int score) {
		int i, j;
		int[] indexI = new int[LENGHT];
		int[] indexJ = new int[LENGHT];
		for (i = 0; i < LENGHT; i++)
			indexI[i] = 0;
		for (i = 0; i < LENGHT; i++)
			indexJ[i] = 0;

		int sum = 0;
		for (i = 0; i < LENGHT; i++) {
			for (j = 0; j < LENGHT; j++) {
				if (matrix[i][j] == 0) {
					indexI[i] = 0;
					j = LENGHT + 5;
				}

			}
			if (j == LENGHT) {
				indexI[i] = 1;
				sum++;

			}

		}
		for (j = 0; j < LENGHT; j++) {
			for (i = 0; i < LENGHT; i++) {
				if (matrix[i][j] == 0) {
					indexJ[j] = 0;
					i = LENGHT + 5;
				}

			}
			if (i == LENGHT) {
				indexJ[j] = 1;
				sum++;

			}

		}
		score =  getScore(indexI, indexJ, matrix, score, sum);
		matrix = deleteRows(indexI, matrix);
		matrix = deleteCol(indexJ, matrix);
		return score;
	}

	 static int[][] deleteRows(int[] temp, int[][] matrix) {
		int i, j;
		for (i = 0; i < LENGHT; i++) {
			if (temp[i] == 1) {
				for (j = 0; j < LENGHT; j++) {
					matrix[i][j] = 0;
				}
			}
		}

		return matrix;

	}

	static int[][] deleteCol(int[] temp, int[][] matrix) {
		int i, j;
		for (j = 0; j < LENGHT; j++) {
			if (temp[j] == 1) {
				for (i = 0; i < LENGHT; i++) {
					matrix[i][j] = 0;
				}
			}
		}

		return matrix;

	}

	 static int getScore(int[] tempr, int[] tempc, int[][] matrix, int score, int sum) {
		for (int i = 0; i < matrix.length; i++) {
			if (tempr[i] == 1) {
				for (int j = 0; j < matrix.length; j++) {
					score += matrix[i][j];

				}
			}
		}
		for (int j = 0; j < matrix.length; j++) {
			if (tempc[j] == 1) {
				for (int i = 0; i < matrix.length; i++) {

					score = score + matrix[i][j];

				}

			}
		}
		score = score + sum * 10;
		if (sum != 1)
			score = score + sum * 10;

		return score;
	}

	public static boolean gameOver(Piece[] randomPiece, int[][] matrix) {
		int dimension, indexPiece = 0;
		int dimI, dimJ;
		dimension = randomPiece.length;
		while (indexPiece < dimension) {
			dimI = randomPiece[indexPiece].getDimI();
			dimJ = randomPiece[indexPiece].getDimJ();
			for (int i = 0; i < 10 - dimI; i++) {
				for (int j = 0; j < 10 - dimJ; j++) {

					if (check(randomPiece[indexPiece].getForm(), dimI, dimJ, i, j, matrix) == true) {
						return true;
					}
				}
			}
			indexPiece++;
		}
		return false;

	}

	 static boolean check(int[][] form, int dimI, int dimJ, int indexI, int indexJ, int[][] matrix) {
		int i, j, m, n;
		// dimI,dimJ is dimensiunile piesei
		// indexI,indexJ de unde sa se porneasca in matrix

		for (i = indexI, m = 0; i < indexI + dimI && m < dimI; i++, m++) {
			for (j = indexJ, n = 0; j < indexJ + dimJ && n < dimJ; j++, n++) {
				if (form[m][n] != 0 && matrix[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

	public static boolean movePiece(int[][] matrix, Piece randomPiece,int startI, int startJ) {
		Scanner read = new Scanner(System.in);
		Piece aux;
		aux = randomPiece;
		int[][] temp = new int[aux.getDimI()][aux.getDimJ()];
		temp = aux.getForm();
		if (check(aux.getForm(), aux.getDimI(), aux.getDimJ(), startI, startJ, matrix) == true) {
			for (int i = startI, m = 0; i < startI + aux.getDimI() && m < aux.getDimI(); i++, m++) {
				for (int j = startJ, n = 0; j < startJ + aux.getDimJ() && n < aux.getDimJ(); j++, n++) {
					matrix[i][j] = temp[m][n];
				}

			}
			
		}else return false;
		return true;

	}

}
