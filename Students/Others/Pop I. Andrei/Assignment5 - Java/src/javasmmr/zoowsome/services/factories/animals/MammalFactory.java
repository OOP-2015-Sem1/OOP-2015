package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Cow;
import javasmmr.zoowsome.models.animals.Monkey;
import javasmmr.zoowsome.models.animals.Tiger;
import javasmmr.zoowsome.models.constants.Constants;

public class MammalFactory extends SpeciesFactory {

	public MammalFactory(double maintenance, double damage) {
		super(maintenance, damage);
	}

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		float temp = getBodyTemperature();
		String name = getRandomName();
		float hair = getHairPerc();

		if (Constants.Animals.Mammals.Cow.equals(type)) {
			//return new Cow(legs, name, hair, temp);
			return new Cow();
		} else if (Constants.Animals.Mammals.Monkey.equals(type)) {
			//return new Monkey(legs, name, hair, temp);
			return new Monkey();
		} else {
			if (Constants.Animals.Mammals.Tiger.equals(type))
				//return new Tiger(legs, name, hair, temp);
				return new Tiger();
			else
				throw new Exception("Invalid animal exception");
		}
	}

	private float getBodyTemperature() {
		return (float) (Math.random() * 40);
	}

	private float getHairPerc() {
		return (float) (Math.random() * 55);
	}
}
