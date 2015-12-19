package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.models.animals.Dolphin;
import javasmmr.zoosome.models.animals.Frog;
import javasmmr.zoosome.models.animals.Seal;

public class AquaticFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Aquatics.Dolphin.equals(type)) {
			return new Dolphin();
		} else if (Constants.Animals.Aquatics.Frog.equals(type)) {
			return new Frog();
		} else if (Constants.Animals.Aquatics.Seal.equals(type)) {
			return new Seal();
		} else {
			throw new Exception("Illegal aquatic exception.");
		}

	}
}
