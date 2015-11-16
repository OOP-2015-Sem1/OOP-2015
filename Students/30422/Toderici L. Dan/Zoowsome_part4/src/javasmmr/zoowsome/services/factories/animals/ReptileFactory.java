package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Snake;
import javasmmr.zoowsome.models.animals.Turtule;
import javasmmr.zoowsome.services.factories.Constants;
import javasmmr.zoowsome.models.animals.Lizard;

public class ReptileFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception 
	{
		if(Constants.Animal.Reptile.Snake.equals(type))
		{
			return new Snake();
		}
		else if(Constants.Animal.Reptile.Turtule.equals(type)) 
		{
			return new Turtule();
		}
		else if(Constants.Animal.Reptile.Lizard.equals(type))
		{
			return new Lizard();
		}
		else
		{
			throw new Exception("Invalid reptile exception!");
		}
	}

}
