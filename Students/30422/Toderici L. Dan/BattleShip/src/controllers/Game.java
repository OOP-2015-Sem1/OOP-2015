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
	private boolean didPlayerHit;

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
		System.out.println("You have 5 ships:");
		System.out.println("1: 5, 1 spot-size SealShip");
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
			setDidPlayerHit(false);
			if(turn%2 == 1)
			{

				while(coordinates.equals("N") || (getDidPlayerHit()))
				{
					firstPlayer.printBoard();
					firstPlayer.printHuntingBoard();



					System.out.println("press enter to continue");
					scanner.nextLine();
					System.out.println(firstPlayer.getName()+" take hit :");

					coordinates = takeAHit(turn,true);
					System.out.println(coordinates);



					if(checkForWinner(turn))
					{
						break;
					}



				}


			}
			else
			{
				while(coordinates.equals("N") || (getDidPlayerHit()))
				{
					secondPlayer.printBoard();
					secondPlayer.printHuntingBoard();

					System.out.println("press enter to continue");
					scanner.nextLine();
					System.out.println(secondPlayer.getName()+" take hit :");

					coordinates = takeAHit(turn,true);
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

	public String takeAHit(int turn,boolean isAi)
	{
		String coordinates;
		if(turn %2 == 1 )
		{

			System.out.println(firstPlayer.getName()+ " place coordinates for hit:");

			if(!isAi)
			{
				coordinates = firstPlayer.getHuntingBoard().hitCell(this.scanner.nextLine());
			}
			else
			{
				coordinates = firstPlayer.getHuntingBoard().hitCell(firstPlayer.generateHitByAi());

			}

			if(!coordinates.equals("N"))
			{
				String[] parts = coordinates.split("-");
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);

				int type = secondPlayer.getPlacementBoard().markAsHitOrMiss(row, column);

				if(type ==0)
				{
					setDidPlayerHit(false);
					
					if(firstPlayer.getDidHitSomething() == 1)
					{
						firstPlayer.setCoordinatesOfLastHitPart(firstPlayer.getCoordinatesOfInitialHit());
					}
					
				}
				else
				{
					setDidPlayerHit(true);

					if(secondPlayer.getDidHitSomething() == 0)
					{
						firstPlayer.setCoordinatesOfInitialHit(new Point(row, column));
						firstPlayer.setCoordinatesOfLastHitPart(new Point(row, column));
					}
					else
					{
						firstPlayer.setCoordinatesOfLastHitPart(new Point(row, column));
					}

					firstPlayer.setDidHitSomething(1);

				}

				if(getDidPlayerHit())
				{
					Point location = new Point(row, column);
					secondPlayer.getShipOfType(type).destroyAPartFromLocation(location);
					if(secondPlayer.isShipOfTypeDestroyed(type))
					{
						System.out.println("Ship of type:"+type+" is destroyed");
						
						firstPlayer.setDidHitSomething(0);
					}


				}
			}
			return coordinates;
		}
		else
		{
			System.out.println(secondPlayer.getName()+ " place coordinates for hit:");

		
			if(!isAi)
			{
				coordinates = secondPlayer.getHuntingBoard().hitCell(this.scanner.nextLine());
			}
			else
			{
				coordinates = secondPlayer.getHuntingBoard().hitCell(secondPlayer.generateHitByAi());

			}
			if(!coordinates.equals("N"))
			{
				String[] parts = coordinates.split("-");
				int row = Integer.parseInt(parts[0]);
				int column = Integer.parseInt(parts[1]);
				int type = firstPlayer.getPlacementBoard().markAsHitOrMiss(row, column);
				if(type ==0)
				{
					setDidPlayerHit(false);
					
					if(secondPlayer.getDidHitSomething() == 1)
					{
						secondPlayer.setCoordinatesOfLastHitPart(secondPlayer.getCoordinatesOfInitialHit());
					}

				}
				else
				{
					setDidPlayerHit(true);

					if(secondPlayer.getDidHitSomething() == 0)
					{
						secondPlayer.setCoordinatesOfInitialHit(new Point(row, column));
						secondPlayer.setCoordinatesOfLastHitPart(new Point(row, column));
					}
					else
					{
						secondPlayer.setCoordinatesOfLastHitPart(new Point(row, column));
					}

					secondPlayer.setDidHitSomething(1);


				}
				if(getDidPlayerHit())
				{
					Point location = new Point(row, column);
					firstPlayer.getShipOfType(type).destroyAPartFromLocation(location);
					if(firstPlayer.isShipOfTypeDestroyed(type))
					{
						System.out.println("Ship of type:"+type+" is destroyed");


						secondPlayer.setDidHitSomething(0);
					}

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


	public boolean getDidPlayerHit() {
		return didPlayerHit;
	}

	public void setDidPlayerHit(boolean wasHit) {
		this.didPlayerHit = wasHit;
	}

}
