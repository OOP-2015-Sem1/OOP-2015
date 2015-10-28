package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;

public class InsectFactory extends SpeciesFactory {
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Insects.Butterfly.equals(type)) {
			return new Butterfly(4.3, 0.2); // leave empty constructor, for now!
		} else if (Constants.Animals.Insects.Ladybug.equals(type)) {
			return new Ladybug(3.2, 0.1);
		} else if (Constants.Animals.Insects.Tarantula.equals(type)) {
			return new Tarantula(4.5, 0.99);
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
