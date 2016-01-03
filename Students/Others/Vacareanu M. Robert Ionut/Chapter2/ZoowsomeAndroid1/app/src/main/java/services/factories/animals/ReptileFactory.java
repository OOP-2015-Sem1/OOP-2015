package services.factories.animals;

import models.animals.Animal;
import models.animals.Crocodile;
import models.animals.Lizard;
import models.animals.Snake;
import services.factories.Constants;

public class ReptileFactory extends services.factories.animals.SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Reptiles.Crocodile.equals(type)) {
			return new Crocodile();
		} else if (Constants.Animals.Reptiles.Lizard.equals(type)) {
			return new Lizard();
		} else if (Constants.Animals.Reptiles.Snake.equals(type)) {
			return new Snake();
		} else {
			throw new Exception("Invalid animal excepion!");
		}
	}
}
