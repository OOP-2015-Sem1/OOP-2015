package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Parrot;
import javasmmr.zoowsome.models.animals.Penguin;
import javasmmr.zoowsome.models.animals.Swallow;
import javasmmr.zoowsome.models.constants.Constants;

public class BirdFactory extends SpeciesFactory {

	public BirdFactory(double maintenance, double damage) {
		super(maintenance, damage);
	}

	int legs = getRandomLegs();
	String name = getRandomName();
	boolean migrates = getRandomMigrates();
	int flight = getRandomFlight();

	public Animal getAnimal(String type) throws Exception {

		if (Constants.Animals.Birds.Swallow.equals(type)) {
			//return new Swallow(legs, name, migrates, flight);
			return new Swallow();
		} else if (Constants.Animals.Birds.Penguin.equals(type)) {
			//return new Penguin(legs, name, migrates, flight);
			return new Penguin();
		} else { if (Constants.Animals.Birds.Parrot.equals(type))
				//return new Parrot(legs, name, migrates, flight);
				return new Parrot();
			else
				throw new Exception("Invalid animal exception");
		}

	}

	private int getRandomFlight() {
		return (int) (Math.random() * 10);
	}

	private boolean getRandomMigrates() {
		int nr = (int) (Math.random() * 12);
		if (nr % 2 == 0)
			return true;
		else
			return false;
	}

}