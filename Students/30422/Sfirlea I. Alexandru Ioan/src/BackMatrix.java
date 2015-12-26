
public class BackMatrix {
	public static int deleteMatrix(int[][] matrix, int score) {
		int i, j;
		int lenght = 5;
		int[] indexI = new int[lenght];
		int[] indexJ = new int[lenght];
		for (i = 0; i < lenght; i++)
			indexI[i] = 0;
		for (i = 0; i < lenght; i++)
			indexJ[i] = 0;

		int sum = 0;
		for (i = 0; i < lenght; i++) {
			for (j = 0; j < lenght; j++) {
				if (matrix[i][j] == 0) {
					indexI[i] = 0;
					j = lenght + 5;
				}

			}
			if (j == lenght) {
				indexI[i] = 1;
				sum++;

			}

		}
		for (j = 0; j < lenght; j++) {
			for (i = 0; i < lenght; i++) {
				if (matrix[i][j] == 0) {
					indexJ[j] = 0;
					i = lenght + 5;
				}

			}
			if (i == lenght) {
				indexJ[j] = 1;
				sum++;

			}

		}
		System.out.println("Sirul idexilor pe linie e: ");
		for (i = 0; i < 5; i++)
			System.out.print(indexI[i] + " ");
		System.out.println();
		System.out.println("Sirul idexilor pe coloana e: ");
		for (i = 0; i < 5; i++)
			System.out.print(indexJ[i] + " ");
		System.out.println();
		System.out.println();
		score = BackMatrix.getScore(indexI, indexJ, matrix, score, sum);
		matrix = BackMatrix.deleteRows(indexI, matrix);
		matrix = BackMatrix.deleteCol(indexJ, matrix);
		return score;
	}

	private static int[][] deleteRows(int[] temp, int[][] matrix) {
		int i, j;
		int lenght = 5;
		for (i = 0; i < lenght; i++) {
			if (temp[i] == 1) {
				for (j = 0; j < lenght; j++) {
					matrix[i][j] = 0;
				}
			}
		}

		return matrix;

	}

	private static int[][] deleteCol(int[] temp, int[][] matrix) {
		int i, j;
		int lenght = 5;
		for (j = 0; j < lenght; j++) {
			if (temp[j] == 1) {
				for (i = 0; i < lenght; i++) {
					matrix[i][j] = 0;
				}
			}
		}

		return matrix;

	}

	private static int getScore(int[] tempr, int[] tempc, int[][] matrix, int score, int sum) {
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
		int[][] temp = new int[5][5];
		dimension = randomPiece.length;
		while (indexPiece < dimension) {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					temp = randomPiece[indexPiece].getForm();
					if (check(temp, i, j, matrix) == true) {
						return true;
					}
				}
			}
			indexPiece++;
		}
		return false;

	}

	private static boolean check(int[][] form, int indexI, int indexJ, int[][] matrix) {
		int i, j,m,n;
		for (i = indexI,m=0; i < indexI + 5 && m<5; i++,m++) {
			for (j = indexJ,n=0; j < indexJ + 5 && n < 5 ; j++,n++) {
				if (form[m][n] != 0 && matrix[i][j] != 0) {
					return false;
				}
			}
		}
		return true;
	}

}
