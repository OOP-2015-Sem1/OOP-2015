package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.models.animals.Butterfly;
import javasmmr.zoosome.models.animals.Parrot;
import javasmmr.zoosome.models.animals.Woodpecker;
import javasmmr.zoosome.services.Constants;

public class BirdFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Bird.Parrot.equals(type)) {
			return new Parrot();
		} else if (Constants.Animals.Bird.Stork.equals(type)) {
			return new Butterfly();
		} else if (Constants.Animals.Bird.Woodpecker.equals(type)) {
			return new Woodpecker();
		} else {
			throw new Exception("Invalid mammal exception!");
		}
	}
}
