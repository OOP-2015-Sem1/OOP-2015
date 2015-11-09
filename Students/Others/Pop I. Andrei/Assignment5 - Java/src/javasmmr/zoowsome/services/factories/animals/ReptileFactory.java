package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Boa;
import javasmmr.zoowsome.models.animals.Lizard;
import javasmmr.zoowsome.models.constants.Constants;

public class ReptileFactory extends SpeciesFactory {

	public ReptileFactory(double maintenance, double damage) {
		super(maintenance, damage);
		// TODO Auto-generated constructor stub
	}

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		String name = getRandomName();
		boolean eggs = getRandomEggs();
		
		if (Constants.Animals.Reptiles.Alligator.equals(type)) {
			//return new Alligator(legs, name, eggs);
			return new Alligator();
		} else if (Constants.Animals.Reptiles.Lizard.equals(type)) {
			//return new Lizard(legs, name, eggs);
			return new Lizard();
		} else {
			if (Constants.Animals.Reptiles.Boa.equals(type))
				//return new Boa(legs, name, eggs);
				return new Boa();
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
