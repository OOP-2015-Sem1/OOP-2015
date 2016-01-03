package services.factories.animals;

import models.animals.Animal;
import models.animals.Nightjar;
import models.animals.Owl;
import models.animals.Woodpecker;
import services.factories.Constants;

public class BirdFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Birds.Nightjar.equals(type)) {
			return new Nightjar();
		} else if (Constants.Animals.Birds.Owl.equals(type)) {
			return new Owl();
		} else if (Constants.Animals.Birds.Woodpecker.equals(type)) {
			return new Woodpecker();
		} else {
			throw new Exception("Invalid animal excepion!");
		}
	}
}
