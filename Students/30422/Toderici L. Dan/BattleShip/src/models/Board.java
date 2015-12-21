package models;

import java.awt.Point;
import models.BoardPiece;
import models.Ship;

public class Board
{
	private BoardPiece[][] board ;
	private boolean isBoardReady = false;
	
	public Board()
	{
		board = new BoardPiece[10][10];

		for(int i = 0 ; i <=9 ;i++)
		{
			for(int j = 0 ; j <=9 ;j++)
			{
				board[i][j] = new BoardPiece();
			}
		}
	}
	
	public BoardPiece[][] getBoard()
	{
		return board;
	}

	public void printBoard()
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
				//System.out.print("_"+board[i][j].getType()+"_|");
				System.out.print(board[i][j].getPiece());
			}
			System.out.print("     ");
			System.out.println();
		}
	}
	
	public boolean verifyInput(String input)
	{
		if(input.length() !=1 )
		{
			if(input.length()!=6)
			{
				if(input.length()!=7)
				{
					System.out.println(input.length());
					System.out.println("Invalid Input. Try Again");
					return false;
				}
			}

		}

		if(input.length() == 1)
		{

			if(input.charAt(0)!= 'S')
			{
				System.out.println("Invalid Input. Try Again.");
				return false;
			}
		}

		if(input.length() == 6)
		{
			if(((int)input.charAt(3)-49) >= 9 || ((int)input.charAt(3)-49) < 0)
			{
				System.out.println("Invalid Column. Try Again.");
				return false;
			}
			else if((int)input.charAt(5) != 72 && (int)input.charAt(5) != 86)
			{
				System.out.println("Invalid Orientation. Try Again.");
				return false;
			}
			//System.out.println("R:"+Integer.parseInt(""+(input.charAt(2)-65))+" C:"+Integer.parseInt(""+input.charAt(3))+" S:"+Integer.parseInt(""+input.charAt(0))+" O:"+input.charAt(5));
			if(!verifyIfShipFits(Integer.parseInt(""+(input.charAt(2)-65)),Integer.parseInt(""+(input.charAt(3)-49)),input.charAt(0)-'0',input.charAt(5)))
			{
				return false;
			}
		}

		if(input.length() == 7)
		{
			if(input.charAt(3)!='1')
			{
				System.out.println("Invalid Column. Try Again.");
				return false;
			}
			else if(input.charAt(4)!='0')
			{
				System.out.println("Invalid Column. Try Again.");
				return false;
			}
			else if((int)input.charAt(6) != 72 && (int)input.charAt(6) != 86)
			{
				System.out.println("Invalid Orientation. Try Again.");
				return false;
			}

			if(!verifyIfShipFits(Integer.parseInt(""+(input.charAt(2)-65)),9,input.charAt(0)-'0',input.charAt(6)))
			{
				return false;
			}

			//if(input.charAt(0) - '0' >= 5)
			if(input.charAt(0) - '0' >= 6)
			{
				System.out.println("Invalid Ship Type. Try Again.");
				return false;
			}
			else if(((int)input.charAt(2)-65) >= 10 || ((int)input.charAt(2)-65) < 0)
			{
				System.out.println("Invalid Row. Try Again.");
				return false;
			}
		}

		return true;
	}
	
	public boolean verifyIfShipFits(int row , int column ,int size,char orientation)
	{
		if(orientation =='H')
		{
			if(column+size>10)
			{
				System.out.println("Ship to big to be put orizontaly");
				return false;
			}
		}
		else
		{
			if(row+size>10)
			{
				System.out.println("Ship to big to be put verticaly");
				return false;
			}
		}

		return true;

	}
	
	public void placeShipOnBoard(Ship ship)
	{

		boolean overlap = false;
		
		int startingColumn = ship.getPartAtIndex(0).getLocation().y;
		int startingRow = ship.getPartAtIndex(0).getLocation().x;
		
		if(ship.getOrientation() == 'H')
		{
			
		
			for( int i = startingColumn; i < (startingColumn + ship.getSize()) ;i++ )
			{
				if(board[startingRow][i].getIsUsed() == true )
				{
					overlap = true;
				}
			}
		}
		else
		{  
			for(int i = startingRow; i < (startingRow + ship.getSize()); i++)
			{
			
				if( board[i][startingColumn].getIsUsed() == true)
				{
					overlap = true;
				}
			}
		}


		if( !overlap )
		{ 
			System.out.println("Ship Part:"+0+" X:"+ship.getPartAtIndex(0).getLocation().getX()+" Y:"+ship.getPartAtIndex(0).getLocation().getY());
			if( ship.getOrientation() == 'H' )
			{
				int index = 1;
				
				for(int i = startingColumn; i < ( startingColumn + ship.getSize() ); i++)
				{
					board[startingRow][i].setPiece("_@_|"); 
					board[startingRow][i].setIsUsed(true); 
					board[startingRow][i].setType(ship.getSize());
					if(i != startingColumn)
					{
						Point location = new Point();
						location.x = startingRow; 
					    location.y = i;
						ship.getPartAtIndex(index).setLocation(location);
	
						index ++;
					}
				}
				
				
			}
			else
			{  
				//layout == 'V'
				int index = 1;
				
				for(int i = startingRow;i<( startingRow + ship.getSize() );i++)
				{
					board[i][startingColumn].setPiece("_@_|");
					board[i][startingColumn].setIsUsed(true);
					board[i][startingColumn].setType(ship.getSize());
					if(i != startingRow)
					{
						Point location = new Point();
						location.x = i; 
					    location.y = startingColumn;
						ship.getPartAtIndex(index).setLocation(location);
						
						index ++;
					}
				}
		
			}
//			if(ship.getSize()==5)
//			{
//				System.out.println("Ship Part:"+4+" X:"+ship.getPartAtIndex(4).getLocation().getX()+" Y:"+ship.getPartAtIndex(4).getLocation().getY());
//				System.out.println("Ship Part:"+3+" X:"+ship.getPartAtIndex(3).getLocation().getX()+" Y:"+ship.getPartAtIndex(3).getLocation().getY());
//				System.out.println("Ship Part:"+2+" X:"+ship.getPartAtIndex(2).getLocation().getX()+" Y:"+ship.getPartAtIndex(2).getLocation().getY());
//				System.out.println("Ship Part:"+1+" X:"+ship.getPartAtIndex(1).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//				//System.out.println("Ship Part:"+0+" X:"+ship.getPartAtIndex(0).getLocation().getX()+" Y:"+ship.getPartAtIndex(0).getLocation().getY());
//			}
//			if(ship.getSize()==4)
//			{
//				System.out.println("Ship Part:"+3+" X:"+ship.getPartAtIndex(3).getLocation().getX()+" Y:"+ship.getPartAtIndex(3).getLocation().getY());
//				System.out.println("Ship Part:"+2+" X:"+ship.getPartAtIndex(2).getLocation().getX()+" Y:"+ship.getPartAtIndex(2).getLocation().getY());
//				System.out.println("Ship Part:"+1+" X:"+ship.getPartAtIndex(1).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//				//System.out.println("Ship Part:"+0+" X:"+ship.getPartAtIndex(0).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//			}
//			if(ship.getSize()==3)
//			{
//				System.out.println("Ship Part:"+2+" X:"+ship.getPartAtIndex(2).getLocation().getX()+" Y:"+ship.getPartAtIndex(2).getLocation().getY());
//				System.out.println("Ship Part:"+1+" X:"+ship.getPartAtIndex(1).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//				//System.out.println("Ship Part:"+0+" X:"+ship.getPartAtIndex(0).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//			}
//			if(ship.getSize()==2)
//			{
//				
//				System.out.println("Ship Part:"+1+" X:"+ship.getPartAtIndex(1).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//				//System.out.println("Ship Part:"+0+" X:"+ship.getPartAtIndex(0).getLocation().getX()+" Y:"+ship.getPartAtIndex(1).getLocation().getY());
//			}
			
			ship.setIsPlaced(true);

			printBoard();
		}
		else
		{
			System.out.println("Invalid Placement. Ships Overlap.");
		}
	}
	
	public boolean getIsBoardReady() {
		return isBoardReady;
	}

	public void setIsBoardReady(boolean isBoardReady) {
		this.isBoardReady = isBoardReady;
	}
	
	public int markAsHitOrMiss(int row,int column)
	{
		System.out.println(row+" - "+column);
		if(board[row][column].getPiece().equals("_@_|"))
		{

			board[row][column].setPiece("_X_|");
			System.out.println("HIT");
			return board[row][column].getType();

		}
		else
		{
			board[row][column].setPiece("_O_|");
			System.out.println("MISS");
		}
		return 0;

	}

}
