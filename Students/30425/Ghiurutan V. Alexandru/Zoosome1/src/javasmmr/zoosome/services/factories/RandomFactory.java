package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.models.animals.*;

/*
 * In this class we generate a random animal from a random species.
 */
public class RandomFactory extends SpeciesFactory {
	private String getSpecies(int species) throws Exception {
		String name = "";
		switch (species) {
		case 1:
			name = "Mammal";
			break;
		case 2:
			name = "Aquatic";
			break;
		case 3:
			name = "Insect";
			break;
		case 4:
			name = "Reptile";
			break;
		case 5:
			name = "Bird";
			break;
		default:
			throw new Exception("An error occured.");
		}
		return name;
	}

	/**
	 * 
	 * @param name
	 *            is defined in the previous function getSpecies
	 * @param type
	 *            must be between 1 and 3
	 * @return a new animal of a specific species.
	 */
	private Animal getAnim(String name, int type) throws Exception {
		if (name.equals("Mammal")) {
			switch (type) {
			case 1:
				return new Tiger();

			case 2:
				return new Cow();

			case 3:
				return new Monkey();

			default:
				throw new Exception("An error ocurred.");
			}
		} else if (name.equals("Reptile")) {
			switch (type) {
			case 1:
				return new Crocodile();

			case 2:
				return new Turtle();

			case 3:
				return new Snake();

			default:
				throw new Exception("An error occured.");
			}
		} else if (name.equals("Insect")) {
			switch (type) {
			case 1:
				return new Butterfly();

			case 2:
				return new Cockroach();

			case 3:
				return new Spider();

			default:
				throw new Exception("An error occured.");
			}
		} else if (name.equals("Aquatic")) {
			switch (type) {
			case 1:
				return new Frog();

			case 2:
				return new Dolphin();

			case 3:
				return new Seal();

			default:
				throw new Exception("An error occured.");
			}
		} else if (name.equals("Bird")) {
			switch (type) {
			case 1:
				return new Vulture();

			case 2:
				return new Sparrow();

			case 3:
				return new Penguin();

			default:
				throw new Exception("An error occured");
			}
		} else {
			throw new Exception("Invalid animal name.");
		}
	}

	// The method that will coordinate all the other methods in this class.
	@Override
	public Animal getAnimal(String type) throws Exception {
		int species = (int) (Math.random() * 5) + 1;
		int number = (int) (Math.random() * 3) + 1;
		return getAnim(getSpecies(species), number);
	}
}
