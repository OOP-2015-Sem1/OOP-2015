package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class ReptileFactory extends SpeciesFactory {
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Reptiles.Alligator.equals(type)) {
			return new Alligator(7, 0.99); // leave empty constructor, for now!
		} else if (Constants.Animals.Reptiles.Lizard.equals(type)) {
			return new Lizard(6, 0.56);
		} else if (Constants.Animals.Reptiles.Snake.equals(type)) {
			return new Snake(5.4, 0.899);
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
