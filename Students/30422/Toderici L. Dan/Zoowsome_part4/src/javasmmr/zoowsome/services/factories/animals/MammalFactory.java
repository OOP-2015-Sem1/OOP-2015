package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Tiger;
import javasmmr.zoowsome.services.factories.Constants;
import javasmmr.zoowsome.models.animals.Monkey;
import javasmmr.zoowsome.models.animals.Cow;

public class MammalFactory extends SpeciesFactory
{
	@Override
	public Animal getAnimal(String type) throws Exception 
	{
		if (Constants.Animal.Mammal.Tiger.equals(type))
		{
			return new Tiger(); 
		} 
		else if (Constants.Animal.Mammal.Monkey.equals(type))
		{
			return new Monkey();
		} 
		else if (Constants.Animal.Mammal.Cow.equals(type)) 
		{
			return new Cow();
		} 
		else 
		{
			throw new Exception("Invalid mammal exception!");
		}  
	}
}
