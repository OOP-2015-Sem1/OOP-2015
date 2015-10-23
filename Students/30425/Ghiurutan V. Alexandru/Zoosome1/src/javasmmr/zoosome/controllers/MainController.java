package javasmmr.zoosome.controllers;

import javasmmr.zoosome.services.factories.Constants;
import javasmmr.zoosome.services.factories.SpeciesFactory;
import javasmmr.zoosome.services.factories.AnimalFactory;
import javasmmr.zoosome.models.animals.Animal;
import java.util.Scanner;

public class MainController {
	// Twist 1
	static Scanner in;

	private void readInput() throws Exception {
		in = new Scanner(System.in);
		int nr = 0;
		System.out.println("Give the number of random elements that you want to create.");
		if (in.hasNextInt()) {
			nr = in.nextInt();
		} else {
			System.err.println("Incorrect input.");
		}
		createRandomAnimals(nr);
	}

	private void createRandomAnimals(int number) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Random);
		Animal a1;
		for (int i = 0; i < number; i++) {
			a1 = speciesFactory1.getAnimal(Constants.Animals.Random.RandomAnimal);
			System.out.printf("%s has %d legs.\n", a1.getName(), a1.getNrOfLegs());
		}
	}

	public static void main(String[] args) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);

		System.out.printf("We have an %s animal with %d legs.\n", a1.getName(), a1.getNrOfLegs());
		MainController controller = new MainController();
		controller.readInput();
	}
}
