
public class PieceHolder {
	public void generatePiece(Pieces[] hold) {
		int i, j, point = 0;
		int[][] aux = new int[5][5];
		aux[0][0] = 1;
		hold[point++] =asigner("blue", aux, 1, 1, 1);
		
		
		for (i = 0; i <=1; i++)
			aux[i][0] = 1;
		hold[point++] = asigner("blue", aux, 2, 1, 2);
		

		for (j = 0; j <=1; j++)
			aux[0][j] = 1;
		hold[point++] = asigner("blue-dark", aux, 1, 2, 2);
		

		for (i = 0; i <=2; i++)
			aux[i][0] = 1;
		hold[point++] = asigner("Yellow", aux, 3, 1, 3);
		

		for (j = 0; j <=2; j++)
			aux[0][j] = 1;
		hold[point++] = asigner("Yellow", aux, 1, 3, 3);
		

		for (i = 0; i <=1; i++)
			for (j = 0; j <=1; j++)
				aux[i][j] = 1;
		aux[0][1] = 0;
		hold[point++] = asigner("Yellow", aux, 2, 2, 3);
		

		for (i = 0; i <=1; i++)
			for (j = 0; j <=1; j++)
				aux[i][j] = 1;
		aux[0][0] = 0;
		hold[point++] = asigner("Yellow", aux, 2, 2, 3);
		
		for (i = 0; i <=1; i++)
			for (j = 0; j <=1; j++)
				aux[i][j] = 1;
		aux[1][0] = 0;
		hold[point++] = asigner("Yellow", aux, 2, 2, 3);
		

		for (i = 0; i <=1; i++)
			for (j = 0; j <=1; j++)
				aux[i][j] = 1;
		aux[1][1] = 0;
		hold[point++] = asigner("Yellow", aux, 2, 2, 3);
		

		for (i = 0; i <=3; i++)
			aux[0][i] = 1;
		hold[point++] = asigner("Yellow", aux, 1, 4, 4);
		

		for (i = 0; i <=3; i++)
			aux[i][0] = 1;
		hold[point++] = asigner("Yellow", aux, 4, 1, 4);
		

		for (i = 0; i <=4; i++)
			aux[0][i] = 1;
		hold[point++] = asigner("Yellow", aux, 1, 5, 5);
		

		for (i = 0; i <=4; i++)
			aux[i][0] = 1;
		hold[point++] = asigner("Yellow", aux, 5, 1, 5);
		

		for (i = 0; i <=2; i++)
			for (j = 0; j <=3; j++)
				if (i == 2 || j == 0)
					aux[i][j] = 1;
				else
					aux[i][j] = 0;
		hold[point++] = asigner("Yellow", aux, 3, 4, 4);
		
		
		for (i = 0; i <=2; i++)
			for (j = 0; j <=3; j++)
				if (i == 2 || j == 3)
					aux[i][j] = 1;
				else
					aux[i][j] = 0;
		hold[point++] = asigner("Yellow", aux, 3, 4, 4);
		
		
		for (i = 0; i <=2; i++)
			for (j = 0; j <=3; j++)
				if (i == 0 || j == 0)
					aux[i][j] = 1;
				else
					aux[i][j] = 0;
		hold[point++] = asigner("Yellow", aux, 3, 4, 4);
		

		for (i = 0; i <=2; i++)
			for (j = 0; j <=3; j++)
				if (i == 0 || j == 3)
					aux[i][j] = 1;
				else
					aux[i][j] = 0;
		hold[point++] = asigner("Yellow", aux, 3, 4, 4);
		
		
		for (i = 0; i <=1; i++)
			for (j = 0; j <=1; j++)
					aux[i][j] = 1;
			hold[point++] = asigner("Yellow", aux, 2, 2, 4);
			
			
			for (i = 0; i <=2; i++)
				for (j = 0; j <=2; j++)
						aux[i][j] = 1;
				hold[point++] = asigner("Yellow", aux, 3, 3, 9);	
		//return hold;
	}

	public Pieces asigner(String color, int[][] aux, int dim_x, int dim_y, int score) {
		Pieces temp;
		temp = new Pieces(color, aux, dim_x, dim_y, score);
		return temp;
	}
	
}
