package javasmmr.zoowsome.services;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Dolphin;
import javasmmr.zoowsome.models.animals.Seal;
import javasmmr.zoowsome.models.animals.Whale;

public class AquaticFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception {
		if(Constants.Animal.Aquatic.Dolphin.equals(type))
		{
			return new Dolphin();
		}
		else if(Constants.Animal.Aquatic.Seal.equals(type))
		{
			return new Seal();
		}
		else if(Constants.Animal.Aquatic.Whale.equals(type))
		{
			return new Whale();
		}
		else
		{
			throw new Exception("Invalid aquatic animal exception!");
		}
	}

}
