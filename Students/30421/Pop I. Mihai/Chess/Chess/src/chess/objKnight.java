package chess;
public class objKnight extends objChessPieces
{
	
	public void objKnight ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix)
	{
		
		finalDesRow = desRow;
		finalDesColumn = desColumn;
		strErrorMsg = "ATENTIE! Calul se misca doar in forma de L!";
		
		if (desRow == (startRow - 2) && desColumn == (startColumn - 1)) //mutare 2N, 1E
		{
			return true;
		}
		else if (desRow == (startRow - 2) && desColumn == (startColumn + 1)) //mutare 2N, 1V
		{
			return true;
		}
		else if (desRow == (startRow + 2) && desColumn == (startColumn - 1)) //mutare 2S, 1E
		{
			return true;
		}
		else if (desRow == (startRow + 2) && desColumn == (startColumn + 1)) //mutare  2S, 1V
		{
			return true;
		}
		else if (desRow == (startRow - 1) && desColumn == (startColumn - 2)) //mutare  1N, 2E
		{
			return true;
		}
		else if (desRow == (startRow - 1) && desColumn == (startColumn + 2)) //mutare 1N, 2V
		{
			return true;
		}
		else if (desRow == (startRow + 1) && desColumn == (startColumn - 2)) //mutare 1S, 2E
		{
			return true;
		}
		else if (desRow == (startRow + 1) && desColumn == (startColumn + 2)) //mutare 1S, 2V
		{
			return true;
		}
		
		return false;
		
	}
	
}