package models;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Scanner;

import models.Board;
import models.HuntingBoard;

public class Player 
{
	private String name;
	private ArrayList<Ship> playerShips ;

	private Board placementBoard;
	private HuntingBoard huntingBoard;
	//private boolean wasHit;

	public Player(String name)
	{
		setName(name);

		this.playerShips = new ArrayList<Ship>() ;
		this.addShips(playerShips);

		this.placementBoard = new Board();
		//this.huntingBoard = new HuntingBoard();
		//		this.setHuntingBoard(new HuntingBoard(placementBoard.getBoard(), "Player 1"));

	}

	//	public void initHuntingBoard(BoardPiece[][] checkBoard)
	//	{
	//		this.huntingBoard = new HuntingBoard(checkBoard);
	//	}

	public boolean initHuntingBoard(BoardPiece[][] checkBoard)
	{
		this.huntingBoard = new HuntingBoard(checkBoard);
	
			return true;
	}

	public void addShips( ArrayList<Ship>groupOfShips)
	{
		for(int boatSize = 1; boatSize<=5;boatSize++)
		{
			groupOfShips.add(new Ship(boatSize));
		}
	}

	public  ArrayList<Ship> getShips()
	{
		return playerShips;
	}

	public Ship getShipOfType(int type)
	{
		for(Ship ship: this.getShips())
		{
			if(ship.getSize() == type)
			{
				return ship;
			}
		}

		return null;

	}

	public void setShipOfTypeAsPlaced(int type)
	{
		this.getShipOfType(type).setIsPlaced(true);

	}

	public boolean areAllShipsPlaced()
	{
		for(Ship ship:this.getShips())
		{
			if(!ship.getIsPlaced())
			{
				return  false;	
			}
		}
		return true;

	}	

	public boolean areAllShipsDestroyed()
	{

		for(Ship ship:this.getShips())
		{
			if(!ship.getIsDestroyed())
			{
				return  false;	
			}
		}
		return true;

	}

	public boolean isShipOfTypeDestroyed(int type)
	{
		Ship ship = getShipOfType(type);
		return ship.getIsDestroyed();
	}

	public void printShips()
	{
		if(this.getShips()!= null)
		{	
			System.out.println("Player : "+this.name+" has:");
			for(Ship ship : this.getShips())
			{
				System.out.println("Ship of size : "+ ship.getSize()+" isPlaced: "+ship.getIsPlaced());
			}
		}
	}

	public void printBoard()
	{
		System.out.println("                "+name);
		placementBoard.printBoard();
	}
	public void printHuntingBoard()
	{
		System.out.println("                "+name);
		huntingBoard.printHuntingBoard();
	}

	public HuntingBoard getHuntingBoard() {
		return huntingBoard;
	}

	public void setHuntingBoard(HuntingBoard huntingBoard) {
		this.huntingBoard = huntingBoard;
	}

	public Board getPlacementBoard() {
		return placementBoard;
	}

	public void setPlacementBoard(Board board) {
		this.placementBoard = board;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return this.name;
	}

	//	public void placementMadeByPlayer(Scanner input)
	//	{
	//		String inputString = null;
	//
	//		while(!placementBoard.getIsBoardReady())
	//		{
	//			inputString = input.nextLine();
	//			if(placementBoard.verifyInput(inputString))
	//			{
	//
	//				if(inputString.equals("S") && this.areAllShipsPlaced())
	//				{
	//					System.out.println("intra");
	//					placementBoard.setIsBoardReady(true);
	//					System.out.println(name+"is ready");
	//					break;
	//				}
	//				else if(inputString.equals("S") && !this.areAllShipsPlaced())
	//				{
	//					System.out.println(inputString);
	//					System.out.println("Not or ships are placed");
	//				}
	//				else if(!this.getShipOfType(inputString.charAt(0)-'0').getIsPlaced())
	//				{
	//					placementBoard.placeShipOnBoard(this.initShipToBePlaced(inputString, inputString.charAt(0) - '0' ));
	//					
	//						//this.setShipOfTypeAsPlaced(inputString.charAt(0)-'0');
	//
	//					
	//				}
	//				else
	//				{
	//					int type = inputString.charAt(0)-'0';
	//					System.out.println("No ships of size "+type+" are left to be placed");
	//				}
	//			}
	//		}
	//	}

	public void placementMadeByPlayer(String inputString)
	{
		System.out.println("input :"+inputString);

		if(placementBoard.verifyInput(inputString))
		{

			if(inputString.equals("S") && this.areAllShipsPlaced())
			{
				System.out.println("intra");
				placementBoard.setIsBoardReady(true);
				System.out.println(name+"is ready");
				//break;
			}
			else if(inputString.equals("S") && !this.areAllShipsPlaced())
			{
				System.out.println(inputString);
				System.out.println("Not or ships are placed");
			}
			else if(!this.getShipOfType(inputString.charAt(0)-'0').getIsPlaced())
			{
				placementBoard.placeShipOnBoard(this.initShipToBePlaced(inputString, inputString.charAt(0) - '0' ));

				//this.setShipOfTypeAsPlaced(inputString.charAt(0)-'0');


			}
			else
			{
				int type = inputString.charAt(0)-'0';
				System.out.println("No ships of size "+type+" are left to be placed");
				placementBoard.setErrorMessage("No ships of size "+type+" are left to be placed");
			}
		}
	}

	public Ship initShipToBePlaced(String input, int type)
	{

		Ship ship = getShipOfType(type);

		if(input.length() == 6)
		{
			ship.setOrientation(input.charAt(5));

			Point location = new Point();
			location.setLocation(Integer.parseInt(""+(input.charAt(2)-65)), Integer.parseInt(""+(input.charAt(3)-49))); 


			//System.out.println((int)location.getX() +"-"+(int)location.getY());
			ship.setLocationOfASpecifiedShipPart(0, location);
		}
		else
		{
			ship.setOrientation(input.charAt(6));

			Point location = new Point();
			location.setLocation(Integer.parseInt(""+(input.charAt(2)-65)),9);
			ship.setLocationOfASpecifiedShipPart(0, location);
		}

		//System.out.println("Ship of type:"+ship.getSize()+" at location"+ship.getPartAtIndex(0).getLocation().getX()+"-"+ship.getPartAtIndex(0).getLocation().getY());
		return ship;
	}




}
