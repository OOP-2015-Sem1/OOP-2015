package chess;
public class objQueen extends objChessPieces
{
	
	public void objQueen ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix)
	{
		
		boolean axis = true;
		
		if (startRow == desRow ^ startColumn == desColumn) //XOR 
		{
			axis = true; //mutare dreapta pe axe
		}
		else if (startRow != desRow && startColumn != desColumn)
		{
			axis = false; //mutare pe diagonala
		}
		else
		{
			
			strErrorMsg = "ATENTIE! REgina se muta drept in orice directie!";
			return false;
			
		}
		
		return axisMove(startRow, startColumn, desRow, desColumn, playerMatrix, axis);
		
	}
	
}