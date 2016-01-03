package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class BirdFactory extends SpeciesFactory{

	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Birds.Flamingo.equals(type)) {
			return new Flamingo(4.5, 5); // leave empty constructor, for now!
		} else if (Constants.Animals.Birds.Eagle.equals(type)) {
			return new Eagle(3.4, 4.23);
		} else if (Constants.Animals.Birds.Ostrich.equals(type)) {
			return new Ostrich(2.4,2.3);
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
