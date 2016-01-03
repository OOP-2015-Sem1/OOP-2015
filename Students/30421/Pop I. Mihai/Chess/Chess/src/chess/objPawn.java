package chess;
public class objPawn extends objChessPieces
{
	
	public void objPawn ()
	{
	}
	
	public boolean legalMove (int startRow, int startColumn, int desRow, int desColumn, int[][] playerMatrix, int currentPlayer)
	{
		
		boolean legalMove = true;
		int[] playerPawnStart = {6,1};
		
		if ((currentPlayer == 1 && desRow >= startRow) || (currentPlayer == 2 && desRow <= startRow)) //Mutare in directia gresita
		{
			
			strErrorMsg = "ATENTIE!Pionul nu se poate muta in aceasta directie!";
			legalMove = false;
			
		}
		else if (desColumn != startColumn) //mutare stanga sau dreapta
		{
			
			if ((desColumn > startColumn && desColumn == (startColumn + 1)) || (desColumn < startColumn && desColumn == (startColumn - 1))) //o singura casuta in stanga sau dreapta
			{
				
				if ((desRow == (startRow + 1) && currentPlayer == 2) || (desRow == (startRow - 1) && currentPlayer == 1))
				{					
				
					if (playerMatrix[desRow][desColumn] == 0) //celula este goala
					{
						
						strErrorMsg = "ATENTIE! In momentul in care cucereste o piesa se poate muta doar diagonal";
						legalMove = false;
						
					}
					
				}
				else
				{
					
					strErrorMsg = "Atentie! Nu se poate muta atat de departe!";
					legalMove = false;
					
				}
				
			}
			else
			{
				
				strErrorMsg = "Atentie! Nu se poate muta atat de departe!";
				legalMove = false;
				
			}
			
		}
		else if ((currentPlayer == 1 && desRow < (startRow - 1)) || (currentPlayer == 2 && desRow > (startRow + 1))) //daca se muta doua sau mai multe celule
		{
			
			if ((currentPlayer == 1 && desRow == (startRow - 2)) || (currentPlayer == 2 && desRow == (startRow + 2))) //daca se muta doua celule
			{
				
				if (playerPawnStart[currentPlayer - 1] != startRow) //If not at pawn starting place
				{
			
				strErrorMsg = "Atentie! Nu se poate muta atat de departe!";
				legalMove = false;
			
				}
				
			}
			else
			{
				
				strErrorMsg = "Atentie! Nu se poate muta atat de departe!";				
				legalMove = false;
				
			}
			
		}		
		
		if (legalMove)
		{

			finalDesRow = desRow;
			finalDesColumn = desColumn;
			
		}
		
		return legalMove;
		
	}
	
}	