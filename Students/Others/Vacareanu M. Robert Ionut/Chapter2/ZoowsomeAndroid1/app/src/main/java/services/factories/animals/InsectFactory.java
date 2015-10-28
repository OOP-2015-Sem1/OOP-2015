package services.factories.animals;

import models.animals.Animal;
import models.animals.Butterfly;
import models.animals.Cockroach;
import models.animals.Spider;
import services.factories.Constants;

public class InsectFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Insects.Butterfly.equals(type)) {
			return new Butterfly();
		} else if (Constants.Animals.Insects.Cockroach.equals(type)) {
			return new Cockroach();
		} else if (Constants.Animals.Insects.Spider.equals(type)) {
			return new Spider();
		} else {
			throw new Exception("Invalid animal excepion!");
		}
	}
}
