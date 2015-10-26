package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Cow;
import javasmmr.zoowsome.models.animals.Monkey;
import javasmmr.zoowsome.models.animals.Tiger;

public class MammalFactory extends SpeciesFactory {

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		float temp = getBodyTemperature();
		String name = getRandomName();
		float hair = getHairPerc();

		if (Constants.Animals.Mammals.Cow.equals(type)) {
			return new Cow(legs, name, hair, temp); 
		} else if (Constants.Animals.Mammals.Monkey.equals(type)) {
			return new Monkey(legs, name, hair, temp);
		} else {
			if (Constants.Animals.Mammals.Tiger.equals(type))
				return new Tiger(legs, name, hair, temp);
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
