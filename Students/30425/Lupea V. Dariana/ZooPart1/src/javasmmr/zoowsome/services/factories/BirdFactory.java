package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class BirdFactory extends SpeciesFactory{

	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Birds.Flamingo.equals(type)) {
			return new Flamingo(); // leave empty constructor, for now!
		} else if (Constants.Animals.Birds.Eagle.equals(type)) {
			return new Eagle();
		} else if (Constants.Animals.Birds.Ostrich.equals(type)) {
			return new Ostrich();
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
