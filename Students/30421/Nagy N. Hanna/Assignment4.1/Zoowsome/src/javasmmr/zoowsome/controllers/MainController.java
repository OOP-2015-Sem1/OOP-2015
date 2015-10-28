package javasmmr.zoowsome.controllers;



import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.services.factoryForAnimals.AnimalFactory;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;
import javasmmr.zoowsome.services.factoryForAnimals.SpeciesFactory;

public class MainController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnimalFactory abstractFactory = new AnimalFactory();
		Animal a1 = null;
		SpeciesFactory speciesFactory1 = null;

		HelpMain help = new HelpMain(abstractFactory, a1, speciesFactory1);

		/**
		 * Building an Array with randomized animals;
		 */
		final int MAX = 15;Animal animalArray[] = new Animal[MAX];
		
		for (int i = 0; i < MAX; i++) {
			int randomSpecies = (int) (1 + Math.random() * 4); //
			int randomSubType = (int) (1 + Math.random() * 2); //
			
			animalArray[i] = help.getAnAnimal(randomSpecies, randomSubType);
			System.out.println("newbie animal in our nest: " + animalArray[i].getName());
		}
		/**
		 *  Building an array for Caretakers
		 */
		Caretaker caretakerArray[] = new Caretaker[MAX];
		for (int i = 0; i < MAX; i++) {
			caretakerArray[i] = new Caretaker();
			caretakerArray[i].setWorkingHours(5.0);
			caretakerArray[i].setName("AnimalLover-" + i);
		}

		/**
		 * Caretakers would like to take care of animals
		 * Unfortunately, this is a very dangerous job
		 * Often it happens that the animal is stronger than 
		 * its Caretaker... 
		 */
		for (Caretaker c1 : caretakerArray) {
			L: for (Animal a : animalArray) {
				// System.out.println("the animal is: "+a.getName());
				if (c1.getIsAlive() && !a.isTakenCareOf()) {
					String result = c1.takeCareof(a);

					if (Constants.Employees.Caretakers.TCO_KILLED.equals(result)) {
						c1.setIsAlive(false);
						System.out.println(a.getName() + " killed " + c1.getName());
						break L;
					}

					else if (Constants.Employees.Caretakers.TCO_NO_TIME.equals(result)) {
						// continuing to search for an animal
						System.out.println(c1.getName() + " has no time to take care of " + a.getName());
						break L;
					}

					else if (Constants.Employees.Caretakers.TCO_SUCCESS.equals(result)) {
						a.setTakenCareOf(true);
						System.out.println("Animal " + a.getName() + " is taken care of by caretaker: " + c1.getName());
					}
				}

			}
			if (c1.getIsAlive())
				System.out.println("Caretaker: " + c1.getName() + " still have free workingHours: "
			    + c1.getWorkingHours());
			else
				System.out.println("RIP Caretaker: " + c1.getName() + " still HAD workingHours: " 
			   + c1.getWorkingHours());
		}

	}
}
