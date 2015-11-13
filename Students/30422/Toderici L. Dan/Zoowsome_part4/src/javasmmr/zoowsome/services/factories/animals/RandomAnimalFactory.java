package javasmmr.zoowsome.services.factories.animals;

import java.util.Random;

import javasmmr.zoowsome.models.animals.*;

public class RandomAnimalFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception {
		Random randomNumber = new Random();
		int number = randomNumber.nextInt(15);
		//System.out.println("Random number is: "+number);
		int nbOfLegs = randomNumber.nextInt(20);
		float normalBodyTemp = randomNumber.nextInt(1000)/10f;
		float bodyHairPerc = randomNumber.nextInt(1000)/10f;
		Boolean migrates = randomNumber.nextBoolean();
		Boolean isDangerous = randomNumber.nextBoolean();
		Boolean canFly = randomNumber.nextBoolean();
		int avgFlightAltitude = randomNumber.nextInt(2500)*200;
		int avgSwimDepth =randomNumber.nextInt(1000)*20;
		TypesOfWater typeOfWater;
		if(randomNumber.nextBoolean())
		{
			typeOfWater = TypesOfWater.freshWater;
		}
		else
		{
			typeOfWater = TypesOfWater.saltWater;
		}
		
		if (type.equals("RandomAnimal"))
		{
			switch(number)
			{
			case 0:
				return new Butterfly();
			case 1:
				return new Cockroach();
			case 2:
				return new Cow();
			case 3:
				return new Dolphin();
			case 4:
				return new Eagle();
			case 5:
				return new Lizard();
			case 6:
				return new Monkey();
			case 7:
				return new Parrot();
			case 8:
				return new Snake();
			case 9:
				return new Seal();
			case 10:
				return new Spider();
			case 11:
				return new Tiger();
			case 12:
				return new Turtule();
			case 13:
				return new Whale();
			case 14:
				return new Woodpecker();
			default:
				throw new Exception("Invalid random number!");
			}
			
		}
		else if (type.equals("RandomAnimalIntiliazed"))
		{
			switch(number)
			{
			case 0:
				return new Butterfly(nbOfLegs,canFly,isDangerous);
			case 1:
				return new Cockroach(nbOfLegs);
			case 2:
				return new Cow(normalBodyTemp,bodyHairPerc);
			case 3:
				return new Dolphin(avgSwimDepth,typeOfWater);
			case 4:
				return new Eagle(migrates,avgFlightAltitude);
			case 5:
				return new Lizard();
			case 6:
				return new Monkey(normalBodyTemp,bodyHairPerc);
			case 7:
				return new Parrot(avgFlightAltitude,migrates);
			case 8:
				return new Snake();
			case 9:
				return new Seal(avgSwimDepth,typeOfWater);
			case 10:
				return new Spider(nbOfLegs,canFly,isDangerous);
			case 11:
				return new Tiger(normalBodyTemp,bodyHairPerc);
			case 12:
				return new Turtule();
			case 13:
				return new Whale(avgSwimDepth,typeOfWater);
			case 14:
				return new Woodpecker(migrates,avgFlightAltitude);
			default:
				throw new Exception("Invalid random number!");
			}
			
		}
		else
		{
			throw new Exception("Invalid type number!");
		}
		
		
		
	}

}
