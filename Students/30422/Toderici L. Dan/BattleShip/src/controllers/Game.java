package controllers;

import java.awt.Point;
import java.awt.SecondaryLoop;
import java.util.Scanner;

import models.Ai;
import models.Player;
import models.Ship;

public class Game 
{
	private Ai firstPlayer;
	private Ai secondPlayer;
	private Scanner scanner;
	private boolean wasHit;

	public Game()
	{
		firstPlayer = new Ai("Gigel");
		secondPlayer = new Ai("Maria");
		this.scanner = new Scanner(System.in);

		startPlacement();
		startHunt();
	}





	private void startPlacement() 
	{
		firstPlayer.printBoard();

		System.out.println();
		System.out.println("Place your ships on the board");
		System.out.println("You have 10 ships:");
		System.out.println("1: 4, 1 spot-size Submarine");
		System.out.println("1: 3, 2 spot-size Destroyer");
		System.out.println("1: 2, 3 spot-size Cruiser");
		System.out.println("1: 1, 4 spot-size Battleship");
		System.out.println("First, select the number of the ship. " +
				"Then select the first place where you want the ship to be placed, like 'C4'.");
		System.out.println("Then select the orientation, H=horizontal, V=vertical");
		System.out.println("For example, '2 B5 V', then press enter");
		System.out.println("Ships can be added a single time");
		System.out.println("When you are done, enter 'S' to start.");

		//firstPlayer.placementMadeByPlayer(scanner);

		firstPlayer.placementMadeByAI();
		secondPlayer.placementMadeByAI();

		firstPlayer.initHuntingBoard(secondPlayer.getPlacementBoard().getBoard());
		secondPlayer.initHuntingBoard(firstPlayer.getPlacementBoard().getBoard());

	}


	private void startHunt() 
	{
		int turn = 1;

		while(!checkForWinner(turn))
		{

			String coordinates = "N";
			setWasHit(true);
			if(turn%2 == 1)
			{

				while(coordinates.equals("N") || (getWasHit()))
				{
					firstPlayer.printBoard();
					firstPlayer.printHuntingBoard();
					//System.out.println(coordinates);

					System.out.println("press enter to continue");
					scanner.nextLine();
					System.out.println(firstPlayer.getName()+" take hit :");

					coordinates = takeAHit(scanner,turn);
					System.out.println(coordinates);
					
					if(checkForWinner(turn))
					{
						break;
					}



				}


			}
			else
			{
				while(coordinates.equals("N") || (getWasHit()))
				{
					secondPlayer.printBoard();
					secondPlayer.printHuntingBoard();

					System.out.println("press enter to continue");
					scanner.nextLine();
					System.out.println(secondPlayer.getName()+" take hit :");

					coordinates = takeAHit(scanner,turn);
					System.out.println(coordinates);
					
					if(checkForWinner(turn))
					{
						break;
					}
				}
			}
			turn ++;

		}


	}

	public String takeAHit(Scanner scanner,int turn)
	{
		if(turn %2 == 1 )
		{
			System.out.println(firstPlayer.getName()+ " place coordinates for hit:");

			String coordinates = firstPlayer.getHuntingBoard().hitCell(scanner.nextLine());
			if(!coordinates.equals("N"))
			{
				String[] parts = coordinates.split("-");
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);

				int type = secondPlayer.getPlacementBoard().markAsHitOrMiss(row, column);
				if(type ==0)
				{
					setWasHit(false);
				}
				else
				{
					setWasHit(true);
				}
				if(getWasHit())
				{
					Point location = new Point(row, column);
					secondPlayer.getShipOfType(type).destroyAPartFromLocation(location);
				}
			}
			return coordinates;
		}
		else
		{
			System.out.println(secondPlayer.getName()+ " place coordinates for hit:");

			String coordinates = secondPlayer.getHuntingBoard().hitCell(scanner.nextLine());
			if(!coordinates.equals("N"))
			{
				String[] parts = coordinates.split("-");
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);
				int type = firstPlayer.getPlacementBoard().markAsHitOrMiss(row, column);
				if(type ==0)
				{
					setWasHit(false);
				}
				else
				{
					setWasHit(true);
				}
				if(getWasHit())
				{
					Point location = new Point(row, column);
					firstPlayer.getShipOfType(type).destroyAPartFromLocation(location);
				}
			}
			return coordinates;

		}
	}

	private boolean checkForWinner(int turn) 
	{
		if(turn %2 ==0)
		{

			if(secondPlayer.areAllShipsDestroyed())
			{
				System.out.println("Player"+firstPlayer.getName()+" wins!");
				return true;
			}

		}
		else
		{
			if( firstPlayer.areAllShipsDestroyed())
			{	
				System.out.println("Player"+secondPlayer.getName()+" wins!");
				return true;
			}
		}
		return false;

	}





	public static void main(String[] args) 
	{
		Game g = new Game();

	}


	public boolean getWasHit() {
		return wasHit;
	}

	public void setWasHit(boolean wasHit) {
		this.wasHit = wasHit;
	}

}
