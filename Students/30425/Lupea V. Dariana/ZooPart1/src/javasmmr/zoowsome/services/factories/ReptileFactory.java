package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class ReptileFactory extends SpeciesFactory {
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Reptiles.Alligator.equals(type)) {
			return new Alligator(); // leave empty constructor, for now!
		} else if (Constants.Animals.Reptiles.Lizard.equals(type)) {
			return new Lizard();
		} else if (Constants.Animals.Reptiles.Snake.equals(type)) {
			return new Snake();
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
