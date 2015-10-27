package javasmmr.zoowsome.services.factoryForAnimals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Cow;
import javasmmr.zoowsome.models.animals.Monkey;
import javasmmr.zoowsome.models.animals.Tiger;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals.Mammals;

public class MammalFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) {
		if (Constants.Animals.Mammals.Tiger.equals(type)) {
			return new Tiger(2.0,0.9); // leave empty constructor, for now!
		} else if (Constants.Animals.Mammals.Monkey.equals(type)) {
			return new Monkey(5.0,0.1);
		} else if (Constants.Animals.Mammals.Cow.equals(type)) {
			return new Cow(2.0,0.1);
		} else {

			// this try-catch is just because I had some errors
			// if I've just written: throw new Exception("Invalid species
			// exception");
			// I don not know why ...
			try {
				throw new Exception("Invalid animals exception");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}
}
