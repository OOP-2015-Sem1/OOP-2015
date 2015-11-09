package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Butterfly;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Spider;
import javasmmr.zoowsome.services.factories.Constants;

public class InsectFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception 
	{
		if(Constants.Animal.Insect.Butterfly.equals(type))
		{
			return new Butterfly();
		}
		else if(Constants.Animal.Insect.Cockroach.equals(type))
		{
			return new Cockroach();
		}
		else if(Constants.Animal.Insect.Spider.equals(type))
		{
			return new Spider();
		}
		else
		{
			throw new Exception("Invalid insect exception!");
		}
	}

}
