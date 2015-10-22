package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class AquaticFactory extends SpeciesFactory {

	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Aquatics.Goldfish.equals(type)) {
			return new Goldfish(); // leave empty constructor, for now!
		} else if (Constants.Animals.Aquatics.Koi.equals(type)) {
			return new Koi();
		} else if (Constants.Animals.Aquatics.Frog.equals(type)) {
			return new Frog();
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
