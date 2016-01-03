package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.services.factories.Constants;

public class AnimalFactory 
{
	public SpeciesFactory getSpeciesFactory(String type) throws Exception
	{
		if(Constants.Species.Mammal.equals(type))
		{
			return new MammalFactory();	
		}
		else if (Constants.Species.Reptile.equals(type))
		{
			return new ReptileFactory();
		}
		else if (Constants.Species.Bird.equals(type))
		{
			return new BirdFactory();
		}
		else if(Constants.Species.Insect.equals(type))
		{
			return new InsectFactory();
		}
		else if (Constants.Species.Aquatic.equals(type))
		{
			return new AquaticFactory();
		}
		else if (Constants.Species.RandomAnimal.equals(type))
		{
			return new RandomAnimalFactory();
		}
		else 
		{
			throw new Exception("Invalid specie exception!");
		}
	}
}
