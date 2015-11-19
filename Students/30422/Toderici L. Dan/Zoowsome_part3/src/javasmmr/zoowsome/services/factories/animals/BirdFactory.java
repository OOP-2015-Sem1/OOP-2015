package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Eagle;
import javasmmr.zoowsome.models.animals.Parrot;
import javasmmr.zoowsome.models.animals.Woodpecker;
import javasmmr.zoowsome.services.factories.Constants;

public class BirdFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception 
	{
		if(Constants.Animal.Bird.Eagle.equals(type))
		{
			return new Eagle();
		}
		else if(Constants.Animal.Bird.Parrot.equals(type))
		{
			return new Parrot();
		}
		else if(Constants.Animal.Bird.Woodpecker.equals(type))
		{
			return new Woodpecker();
		}
		else 
		{
			throw new Exception("Invalid bird exception!");
		}
	}

}
