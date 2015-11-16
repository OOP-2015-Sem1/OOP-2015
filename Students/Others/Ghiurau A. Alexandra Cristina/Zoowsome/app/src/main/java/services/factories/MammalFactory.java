package services.factories;


import models.animals.Animal;
import models.animals.Cow;
import models.animals.Monkey;
import models.animals.Tiger;

public class MammalFactory extends SpeciesFactory {

	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Mammals.Tiger.equals(type)) {
			return new Tiger();
		} else if (Constants.Animals.Mammals.Monkey.equals(type)) {
			return new Monkey();
		} else if (Constants.Animals.Mammals.Cow.equals(type)) {
			return new Cow();
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}
}
