package chess;
public class objBishop extends objChessPieces
{
	
	public void objBishop ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix)
	{
		
		if (startRow == desRow || startColumn == desColumn) //If moved straight
		{
			
			strErrorMsg = "Atentie!Nebunul se poate misca doar pe diagonala!";
			return false;
			
		}
		
		return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, false);
		
	}
	
}