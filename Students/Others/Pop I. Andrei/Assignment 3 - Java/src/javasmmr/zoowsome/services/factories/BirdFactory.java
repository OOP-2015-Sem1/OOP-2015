package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Parrot;
import javasmmr.zoowsome.models.animals.Penguin;
import javasmmr.zoowsome.models.animals.Swallow;

public class BirdFactory extends SpeciesFactory {

	int legs = getRandomLegs();
	String name = getRandomName();
	boolean migrates = getRandomMigrates();
	int flight = getRandomFlight();

	public Animal getAnimal(String type) throws Exception {

		if (Constants.Animals.Birds.Swallow.equals(type)) {
			return new Swallow(legs, name, migrates, flight);
		} else if (Constants.Animals.Birds.Penguin.equals(type)) {
			return new Penguin(legs, name, migrates, flight);
		} else { if (Constants.Animals.Birds.Parrot.equals(type))
				return new Parrot(legs, name, migrates, flight);
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