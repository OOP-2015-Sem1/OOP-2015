package models;

import java.util.Random;

public class Ai extends Player
{

	public Ai(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	
	public void placementMadeByAI()
	{
		int typeOfShip;
		int row;
		int column;
		int orientation;

		char rowChar;

		String inputString;

		Random randomNbGenerator = new Random();

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

}
