package models;

import models.BoardPiece;

public class HuntingBoard {
	
	private BoardPiece [][] checkBoard;
	private BoardPiece [][]  huntingBoard = new BoardPiece[10][10];
	private int rowToHit;
	private int columnToHit;

	public HuntingBoard(BoardPiece[][] board) 
	{
		this.checkBoard = board;
		
		for(int i = 0 ; i <=9 ;i++)
		{
			for(int j = 0 ; j <=9 ;j++)
			{
				
				huntingBoard[i][j] = new BoardPiece();
				
			}
		}
	}
	
	public String hitCell(String inputString)
	{
	
		System.out.println("Your hit :"+inputString);
		String returnCoordinates = null;
		
		if(verifyHuntInput(inputString))
		{
			this.initHuntCoordinates(inputString);

			if(!verifyIfHuntInputIsDuplicated(rowToHit,columnToHit))
			{
			
				
				if(checkBoard[rowToHit][columnToHit].getPiece().equals("_@_|"))
				{
					
					
					
					huntingBoard[rowToHit][columnToHit].setPiece("_X_|");
					returnCoordinates = rowToHit +"-"+ columnToHit;
					this.printHuntingBoard();
					//System.out.println("C:"+returnCoordinates);
					return returnCoordinates;
				}
				else
				{
					huntingBoard[rowToHit][columnToHit].setPiece("_O_|");
					returnCoordinates = rowToHit +"-"+ columnToHit;
					this.printHuntingBoard();
					//System.out.println("C:"+returnCoordinates);
					return returnCoordinates;
				}
			}
		}
		this.printHuntingBoard();
		return "N";
		
	}
	


	public boolean verifyHuntInput(String input)
	{
		if(input.length()<2 || input.length()>3)
		{
			System.out.println("Not a good hunt input");
			return false;
		}
		if(input.length() == 2)
		{

			if(((int)input.charAt(1)-49) >= 9 || ((int)input.charAt(1)-49) < 0)
			{
				System.out.println("Invalid column");
			}
		}
		else
		{
			if(input.charAt(1)!='1')
			{
				System.out.println("Invalid column");
				return false;
			}
			else if(input.charAt(2)!='0')
			{
				System.out.println("Invalid column");
				return false;
			}
		}

		if(((int)input.charAt(0)-65) >= 10 || ((int)input.charAt(0)-65) < 0)
		{
			System.out.println("Invalid row");
			return false;
		}

		return true;
	}

	public void initHuntCoordinates(String input)
	{
		rowToHit =(int)input.charAt(0)-65;
		if(input.length()==2)
		{
			columnToHit = (int)input.charAt(1)-49;
		}
		else
		{
			columnToHit = 9;
		}
	}

	public boolean verifyIfHuntInputIsDuplicated(int row, int column)
	{

		if(!huntingBoard[row][column].getPiece().equals("___|"))
		{
			System.out.println("Already hit that place! Try again");
			return true;
		}

		return false;
	}

	public void printHuntingBoard()
	{
		for(int i = 1; i <= 10 ;i++)
		{
			System.out.print("   "+i);
		}
		System.out.println();

		for(int i=0; i<=9; i++)
		{
			System.out.print((char)(65+i) + " ");
			for(int j=0; j<=9; j++)
			{
				System.out.print(huntingBoard[i][j].getPiece());
			}
			System.out.print("     ");
			System.out.println();
		}
	}
	
	public BoardPiece [][] getHuntingBoardMatrix()
	{
		return huntingBoard;
	}


}
