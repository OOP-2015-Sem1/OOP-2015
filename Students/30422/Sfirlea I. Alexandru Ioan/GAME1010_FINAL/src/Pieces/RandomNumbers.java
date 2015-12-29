package Pieces;

import java.util.Random;

class RandomNumbers 
{
	private static RandomNumbers singeltonInstance = null;
	
	private RandomNumbers() 
	{

	}
	
	public static RandomNumbers getInstance() 
	{
		if(singeltonInstance == null)
		{
			singeltonInstance = new RandomNumbers();
		}
		return singeltonInstance;
	}
	
	public int getRandomNumber()
	{
		Random randomGenerator = new Random();
		return randomGenerator.nextInt(19)*1;
	}
	
}


