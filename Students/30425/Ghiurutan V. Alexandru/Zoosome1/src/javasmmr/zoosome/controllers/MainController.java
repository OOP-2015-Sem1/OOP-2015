package javasmmr.zoosome.controllers;

import javasmmr.zoosome.services.factories.Constants;
import javasmmr.zoosome.services.factories.SpeciesFactory;
import javasmmr.zoosome.services.factories.AnimalFactory;
import javasmmr.zoosome.models.employees.Caretaker;
import javasmmr.zoosome.models.animals.Animal;
import java.util.Scanner;

public class MainController {
	// Twist 1
	static Scanner in;// Used to get the number of random animals we want to
						// create.

	private void readInput() throws Exception {
		in = new Scanner(System.in);
		int nr = 0;
		System.out.println("Give the number of random elements that you want to create.");
		if (in.hasNextInt()) {// Verifies if we have a correct input.
			nr = in.nextInt();
		} else {// If we don't have a correct input we will print an error.
			System.err.println("Incorrect input.");
		}
		createRandomAnimals(nr);
	}

	private void takeCare(int numberOfAnimals, int numberOfCaretakers) throws Exception {
		/*
		 * If i would use an ArrayList I could use an iterator to loop through
		 * all the elements in the list,as with the enhanced for loop.
		 */
		Animal[] animal = createRandomAnimals(numberOfAnimals);
		Caretaker[] caretaker = new Caretaker[numberOfCaretakers];
		for (int i = 0; i < numberOfCaretakers; i++) {
			caretaker[i] = new Caretaker();
		}
		String result = "";
		for (Caretaker c : caretaker) {
			for (Animal a : animal) {
				if (!c.isDead() && !a.isTakenCareOf()) {
					result = c.takeCareOf(a);
					if (c.equals(Constants.Employees.Caretakers.TCO_KILLED)) {
						c.setIsDead(true);
					} else if (result.equals(Constants.Employees.Caretakers.TCO_NO_TIME)) {
						break;
					} else {
						a.setTakenCareOf(true);
					}
				}
			}
		}
		int nr = 0;
		for (Animal a : animal) {
			if (a.isTakenCareOf()) {
				nr++;
			}
		}
		double procent = ((double) nr / numberOfAnimals) * 100.0;
		System.out.printf("%s.", (procent == 100.0) ? "All animals are taken care of"
				: "There are unkempt animals.The procent of animals that are taken care is:" + procent + "%");

	}

	private void readTakenCare() throws Exception {
		System.out.println("Enter the number of animal and caretakers:");
		int nrOfAnimals = in.nextInt(), nrOfCaretakers = in.nextInt();
		takeCare(nrOfAnimals, nrOfCaretakers);
	}

	// This method will create a number of animals from random species.
	private Animal[] createRandomAnimals(int number) throws Exception {
		Animal[] animal = new Animal[number];
		AnimalFactory abstractFactory = new AnimalFactory();
		// Get the specific factory.
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Random);
		Animal a1;
		for (int i = 0; i < number; i++) {
			a1 = speciesFactory1.getAnimal(Constants.Animals.Random.RandomAnimal);
			animal[i] = a1;
			System.out.printf("%s has %d legs.\n", a1.getName(), a1.getNrOfLegs());
		}
		return animal;
	}

	/**
	 * 
	 * @param args
	 * @throws Exception
	 *             from the method called in this class.
	 */
	public static void main(String[] args) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		/*
		 * speciesFactory1 becomes a reference to a MammalFactory object.
		 */
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		// a1 becomes a reference to a Tiger object.
		Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);
		System.out.printf("We have an %s animal with %d legs.\n", a1.getName(), a1.getNrOfLegs());
		// Creates a controller reference to the MainController object.
		// This will allow us to call non-static methods through the controller
		// reference.
		MainController controller = new MainController();
		controller.readInput();
		controller.readTakenCare();
	}
}
