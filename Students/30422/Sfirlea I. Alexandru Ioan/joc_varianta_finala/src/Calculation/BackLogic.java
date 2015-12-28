package Calculation;

import Pieces.Piece;

public class BackLogic {
	private static final int LENGHT = 10;

	/*
	 * Stergem din matricea din spate liniile/col pline returnam scorul
	 * 
	 */
	public int deleteMatrix(int[][] matrix, int score) {
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
		score = getScore(indexI, indexJ, matrix, score, sum);
		matrix = deleteRows(indexI, matrix);
		matrix = deleteCol(indexJ, matrix);
		return score;
	}

	/*
	 * stergem liiniile pline
	 */
	private int[][] deleteRows(int[] temp, int[][] matrix) {
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

	/*
	 * stergem coloanele pline
	 */
	private int[][] deleteCol(int[] temp, int[][] matrix) {
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

	/*
	 * calculam scorul si il returnam se ofera bonus pentru mai multe linii
	 * pline
	 */
	
	private int getScore(int[] tempr, int[] tempc, int[][] matrix, int score, int sum) {
		for (int i = 0; i < matrix.length; i++) {
			if (tempr[i] == 1) {
				for (int j = 0; j < matrix.length; j++) {
					score += matrix[i][j]%10;

				}
			}
		}
		for (int j = 0; j < matrix.length; j++) {
			if (tempc[j] == 1) {
				for (int i = 0; i < matrix.length; i++) {

					score = score + matrix[i][j]%10;

				}

			}
		}
		score = score + sum * 10;
		if (sum != 1)
			score = score + sum * 10;

		return score;
	}

	public static boolean gameOver(Piece randomPiece, int[][] matrix) {
		int dimI, dimJ;
		dimI = randomPiece.getDimI();
		dimJ = randomPiece.getDimJ();
		for (int i = 0; i <= 10 - dimI; i++) {
			for (int j = 0; j <= 10 - dimJ; j++) {

				if (check(randomPiece.getForm(), dimI, dimJ, i, j, matrix) == true) {
					return true;
				}
			}
		}
		return false;

	}

	/*
	 * verificam daca putem muta piesa acolo
	 * 
	 */
	
	public static boolean check(int[][] form, int dimI, int dimJ, int indexI, int indexJ, int[][] matrix) {
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

}
