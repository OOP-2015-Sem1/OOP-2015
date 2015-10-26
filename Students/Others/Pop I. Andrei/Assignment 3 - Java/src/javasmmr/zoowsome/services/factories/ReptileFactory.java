package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Boa;
import javasmmr.zoowsome.models.animals.Lizard;

public class ReptileFactory extends SpeciesFactory {

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		String name = getRandomName();
		boolean eggs = getRandomEggs();

		if (Constants.Animals.Reptiles.Alligator.equals(type)) {
			return new Alligator(legs, name, eggs);
		} else if (Constants.Animals.Reptiles.Lizard.equals(type)) {
			return new Lizard(legs, name, eggs);
		} else {
			if (Constants.Animals.Reptiles.Boa.equals(type))
				return new Boa(legs, name, eggs);
			else
				throw new Exception("Invalid animal exception");
		}
	}

	private boolean getRandomEggs() {
		int nr = (int) (Math.random() * 12);
		if (nr % 2 == 0)
			return true;
		else
			return false;
	}

}
