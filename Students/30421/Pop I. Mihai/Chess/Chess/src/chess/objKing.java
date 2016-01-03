package chess;
public class objKing extends objChessPieces
{
	
	public void objKing ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix)
	{
		
		finalDesRow = desRow;
		finalDesColumn = desColumn;
		
		if (desRow == (startRow + 1) && desColumn == startColumn) //mutare S
		{
			return true;
		}
		else if (desRow == (startRow + 1) && desColumn == (startColumn - 1)) //mutare SV
		{
			return true;
		}
		else if (desRow == startRow && desColumn == startColumn - 1) //mutare W
		{
			return true;
		}
		else if (desRow == (startRow - 1) && desColumn == (startColumn - 1)) //mutare Nv
		{
			return true;
		}
		else if (desRow == (startRow - 1) && desColumn == startColumn) //mutare N
		{
			return true;
		}
		else if (desRow == (startRow - 1) && desColumn == (startColumn + 1)) //mutare NE
		{
			return true;
		}
		else if (desRow == startRow && desColumn == (startColumn + 1)) //mutare E
		{
			return true;
		}
		else if (desRow == (startRow + 1) && desColumn == (startColumn + 1)) //SE
		{
			return true;
		}
		else
		{
			
			strErrorMsg = "ATENTIE! Regele se misca un sigur spatiu in orice directie!";
			return false;
			
		}
		
	}
	
}