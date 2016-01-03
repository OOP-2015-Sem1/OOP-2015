package chess;
public class objRock extends objChessPieces
{
	
	public void objRock ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix)
	{
		
		if (startRow != desRow && startColumn != desColumn) //daca e o Mutare pe diagonala
		{
			
			strErrorMsg = "ATENTIE!Tura se muta doar orizontal sau vertical!";
			return false;
			
		}
		//Regina are aceeasi mutare precum un nebun sau o tura  asa ca verificarea caii este aceeasi pentru toate trei
	
		return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, true);
		
	}
	
}