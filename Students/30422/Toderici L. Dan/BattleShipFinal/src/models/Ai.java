package models;

import java.awt.Point;
import java.util.Random;

public class Ai extends Player
{

	private int  didHitSomething;
	private Point coordinatesOfLastHitPart;
	private Point coordinatesOfInitialHit;
	private int direction;
	private Random randomNbGenerator;


	public Ai(String name)
	{
		super(name);
		randomNbGenerator = new Random();
		setDidHitSomething(0);

	}


	public void placementMadeByAI()
	{
		int typeOfShip;
		int row;
		int column;
		int orientation;

		char rowChar;

		String inputString;


		typeOfShip = 0;

		for(int i = 5; i >= 1; i--)
		{
			typeOfShip = i;
			while(!this.getShipOfType(i).getIsPlaced())
			{

				row = randomNbGenerator.nextInt(10)+1;
				column = randomNbGenerator.nextInt(10)+1;
				orientation = randomNbGenerator.nextInt(2)+1;

				inputString =""+typeOfShip;

				row +=64;
				rowChar = (char)row;

				inputString = inputString+" "+rowChar;
				inputString = inputString+""+column;

				if(orientation == 1)
				{
					inputString =inputString + " " + 'H';
				}
				else
				{
					inputString =inputString + " " + 'V';
				}

				System.out.println(inputString);
				if(getPlacementBoard().verifyInput(inputString))
				{
					if(!getShipOfType(inputString.charAt(0)-'0').getIsPlaced())
					{
						getPlacementBoard().placeShipOnBoard(initShipToBePlaced(inputString, inputString.charAt(0) - '0' ));
					}

				}
			}
		}

		System.out.println("Ai finished placing ships!");

		getPlacementBoard().setIsBoardReady(true) ;

		this.printBoard();

	}

	public String generateHitByAi()
	{

		String coordinates = null;

		if(getDidHitSomething() == 0)
		{

			coordinates = getRandomCoordinates();
		}
		else
		{

	
			
			while(!verifyDirection(getDirection()))
			{
				setDirection(getDirection()+1);
			}


			Point nextLocation = getCoordinatesInSameArea();
			nextLocation.x =  nextLocation.x+1 ;
			nextLocation.y = nextLocation.y+1;


			coordinates =""+(char)(nextLocation.x+64);
			coordinates = coordinates + nextLocation.y;



		}


		return coordinates;
	}


	public String getRandomCoordinates()
	{
		String coordinates;
		int row;
		int column;

		row = randomNbGenerator.nextInt(10)+1;
		column = randomNbGenerator.nextInt(10)+1;

		row +=64;
		char rowChar = (char)row;

		coordinates =""+rowChar;
		coordinates = coordinates+""+column;

		setDirection(0);

		return coordinates;
	}


	public boolean verifyDirection(int direction)
	{
		
		Point lastCoordinates = getCoordinatesOfLastHitPart();
		BoardPiece [][] matrix =  this.getHuntingBoard().getHuntingBoardMatrix();

		switch(direction){
		case 0:
			System.out.println("initial position");
			return false;
		case 1 :

			if(lastCoordinates.y - 1 < 0)
			{
				System.out.println("Nu mai poti merge in stanga cu coloana");
				return false;
			}

			if(!matrix[lastCoordinates.x][lastCoordinates.y -1].getPiece().equals("___|"))
			{
				System.out.println("Already verified direction y-1");
				return false;
			}

			break;

		case 2:

			if(lastCoordinates.x + 1 > 10)
			{
				System.out.println("Nu mai poti merge in jos cu linia");
				return false;
			}

			if(!matrix[lastCoordinates.x+1][lastCoordinates.y].getPiece().equals("___|"))
			{
				System.out.println("Already verified direction x+1");
				return false;
			}

			break;

		case 3:

			if(lastCoordinates.y + 1 > 10)
			{
				System.out.println("Nu mai poti merge in dreapta cu coloana");
				return false;
			}

			if(!matrix[lastCoordinates.x][lastCoordinates.y+1].getPiece().equals("___|"))
			{
				System.out.println("Already verified direction y+1");
				return false;
			}

			break;

		case 4:

			if(lastCoordinates.x - 1 < 0)
			{
				System.out.println("Nu mai poti merge in sus cu linia");
				return false;
			}


			if(!matrix[lastCoordinates.x - 1][lastCoordinates.y].getPiece().equals("___|"))
			{
				System.out.println("Already verified direction x-1");
				return false;
			}

			break;

		}

		return true;

	}


	private Point getCoordinatesInSameArea() 
	{

		int row = 0;
		int column = 0;
	
		switch(direction)
		{
		case 1 :
			row = getCoordinatesOfLastHitPart().x;
			column = getCoordinatesOfLastHitPart().y -1;

			break;

		case 2:
			row = getCoordinatesOfLastHitPart().x + 1;
			column = getCoordinatesOfLastHitPart().y ;

			break;

		case 3:
			row = getCoordinatesOfLastHitPart().x;
			column = getCoordinatesOfLastHitPart().y + 1;

			break;

		case 4:
			row = getCoordinatesOfLastHitPart().x - 1;
			column = getCoordinatesOfLastHitPart().y ;

			break;

		}

		Point lastCoordinates = new Point(row, column);

		return lastCoordinates;

	}

	public int getDirection(){
		return direction;
	}

	public void setDirection(int direction){
		this.direction = direction;
	}


	public Point getCoordinatesOfLastHitPart() {
		return coordinatesOfLastHitPart;
	}


	public void setCoordinatesOfLastHitPart(Point coordinatesOfLastHitPart) {
		this.coordinatesOfLastHitPart = coordinatesOfLastHitPart;
	}


	public int getDidHitSomething() {
		return didHitSomething;
	}


	public void setDidHitSomething(int didHitSomething) {
		this.didHitSomething = didHitSomething;
	}


	public Point getCoordinatesOfInitialHit() {
		return coordinatesOfInitialHit;
	}


	public void setCoordinatesOfInitialHit(Point coordinatesOfInitialHit) {
		this.coordinatesOfInitialHit = coordinatesOfInitialHit;
	}






}
