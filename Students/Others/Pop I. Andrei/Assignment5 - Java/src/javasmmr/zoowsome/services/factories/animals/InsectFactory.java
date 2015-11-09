package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Butterfly;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Spider;
import javasmmr.zoowsome.models.constants.Constants;

public class InsectFactory extends SpeciesFactory {

	public InsectFactory(double maintenance, double damage) {
		super(maintenance, damage);
	}

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		String name = getRandomName();
		boolean dangerous = getRandomDangerous();
		boolean fly = getRandomCanFly();

		if (Constants.Animals.Insects.Butterfly.equals(type)) {
			//return new Butterfly(legs, name, dangerous, fly);
			return new Butterfly();
		} else if (Constants.Animals.Insects.Cockroach.equals(type)) {
			//return new Cockroach(legs, name, dangerous, fly);
			return new Cockroach();
		} else {
			if (Constants.Animals.Insects.Spider.equals(type))
				//return new Spider(legs, name, dangerous, fly);
				return new Spider();
			else
				throw new Exception("Invalid animal excepion");
		}
	}

	private boolean getRandomDangerous() {
		int nr = (int) (Math.random() * 12);
		if (nr % 2 == 0)
			return true;
		else
			return false;
	}

	private boolean getRandomCanFly() {
		int nr = (int) (Math.random() * 12);
		if (nr % 2 == 0)
			return true;
		else
			return false;
	}

}
