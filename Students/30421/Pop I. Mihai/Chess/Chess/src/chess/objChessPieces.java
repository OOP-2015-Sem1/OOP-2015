package chess;
public class objChessPieces
{
	
	protected int finalDesRow = 0;
	protected int finalDesColumn = 0;
	protected String strErrorMsg = "";
	
	public objChessPieces ()
	{
	}
	//verificare celula e goala?
	private boolean checkAxisMove (int newRow, int newColumn, int[][] playerMatrix)
	{
		
		if (playerMatrix[newRow][newColumn] != 0) //nu e goala
		{

			strErrorMsg = "Atentie!O alta piesa blocheaza ruta!"; //EROARE
			return false;
			
		}
		
		return true;
		
	}
	//verificare daca se poate muta
	protected boolean axisMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix, boolean straightAxis)
	{
		
		if (straightAxis) //mutare dreapta tura regina
		{
			
			if ((startColumn == desColumn) && (startRow != desRow)) //se misca pe aceeasi coloana
			{
				
				if (desRow < startRow) //mutare Nord
				{					
					//se verifica fiecare celula row - 1 (mai putin in care e la inceput) pana la destinatie
					for (int newRow = (startRow - 1); newRow > desRow; newRow--) 
					{
						//verificare celula e goala
						if (!checkAxisMove(newRow, desColumn, playerMatrix))
						{
							return false;
						}
						
					}

				}
				else //mutare la sud
				{
					
					for (int newRow = (startRow + 1); newRow < desRow; newRow++)
					{
						
						if (!checkAxisMove(newRow, desColumn, playerMatrix))
						{
							return false;
						}
							
					}
				
				}
				
			}
			else if ((startRow == desRow) && (startColumn != desColumn)) //mutare pe acelasi rand
			{
				
				if (desColumn < startColumn) //miscare la VEST
				{
					
					for (int newColumn = (startColumn - 1); newColumn > desColumn; newColumn--)
					{
						
						if (!checkAxisMove(desRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}
				
				}
				else //miscare la Est
				{
					
					for (int newColumn = (startColumn + 1); newColumn < desColumn; newColumn++)
					{
						
						if (!checkAxisMove(desRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}
					
				}
				
			}
			else //se miscare pe diagonala
			{
				
				strErrorMsg = "Atentie!Eroare!";
				return false;
				
			}
		
		}
		else //Miscare pe diagonala(regina sau nebun)
		{
			
			strErrorMsg = "Atentie!Destinatia nu e corect!"; //EROARE
			int newColumn = 0;
			
			if (desRow < startRow && desColumn < startColumn) //daca se muta "NV"
			{
			
				if ((desRow - startRow) == (desColumn - startColumn))
				{
					
					for (int newRow = (startRow - 1); newRow > desRow; newRow--)
					{
						
						newColumn = startColumn - (startRow - newRow);

						if (!checkAxisMove(newRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}

				}
				else
				{
					return false;
				}
	
			}
			else if (desRow < startRow && desColumn > startColumn) //se muta NE
			{
				
				if ((desRow - startRow) == (startColumn - desColumn))
				{
					
					for (int newRow = (startRow - 1); newRow > desRow; newRow--)
					{
						
						newColumn = startColumn + (startRow - newRow);

						if (!checkAxisMove(newRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}

				}
				else
				{
					return false;
				}
				
			}
			else if (desRow > startRow && desColumn < startColumn) //se muta SV
			{
				
				if ((startRow - desRow) == (desColumn - startColumn))
				{
					
					for (int newRow = (startRow + 1); newRow < desRow; newRow++)
					{
						
						newColumn = startColumn - (newRow - startRow);

						if (!checkAxisMove(newRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}

				}
				else
				{
					return false;
				}
				
			}
			else if (desRow > startRow && desColumn > startColumn) //daca se  misca SE
			{
				
				if ((startRow - desRow) == (startColumn - desColumn))
				{
					
					for (int newRow = (startRow + 1); newRow < desRow; newRow++)
					{
						
						newColumn = startColumn + (newRow - startRow);

						if (!checkAxisMove(newRow, newColumn, playerMatrix))
						{
							return false;
						}
						
					}

				}
				else
				{
					return false;
				}
				
			}
			else //Nu e o miscare pe diagonala
			{
				
				strErrorMsg = "Atentie!EROARE!";
				return false;
				
			}
		
		}		
		
		finalDesRow = desRow;
		finalDesColumn = desColumn;
		
		return true;
		
	}
	
	public int getDesRow ()
	{
		return finalDesRow;
	}
	
	public int getDesColumn ()
	{
		return finalDesColumn;
	}
	
	public String getErrorMsg ()
	{
		return strErrorMsg;
	}
	
}