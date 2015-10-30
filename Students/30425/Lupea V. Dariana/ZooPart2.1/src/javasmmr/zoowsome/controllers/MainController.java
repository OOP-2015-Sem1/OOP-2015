package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.services.factories.AnimalFactory;
import javasmmr.zoowsome.services.factories.EmployeeAbstractFactory;
import javasmmr.zoowsome.services.factories.SpeciesFactory;
import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.models.animals.*;
import javasmmr.zoowsome.services.factories.CaretakerFactory;
import javasmmr.zoowsome.models.employees.Caretaker;

public class MainController {

	public static void main(String[] args) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		SpeciesFactory speciesFactory;
		int nrOfAnimals = 50;
		final int maximum = 16;
		Animal[] a = new Animal[nrOfAnimals];

		for (int i = 0; i < nrOfAnimals; i++) {
			int oneAnimal = (int) (Math.random() * maximum);
			switch (oneAnimal) {
			case 0: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Birds);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Birds.Eagle);
				break;
			}
			case 1: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Birds);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Birds.Flamingo);
				break;
			}
			case 2: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Birds);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Birds.Ostrich);
				break;
			}

			case 3: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Aquatics.Frog);
				break;
			}
			case 4: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Aquatics.Koi);
				break;
			}
			case 5: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Aquatics.Goldfish);
				break;
			}
			case 6: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Insects);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Insects.Butterfly);
				break;
			}
			case 7: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Insects);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Insects.Ladybug);
				break;
			}
			case 8: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Insects);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Insects.Tarantula);
				break;
			}
			case 9: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Mammals.Zebra);
				break;
			}
			case 10: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Mammals.Monkey);
				break;
			}
			case 11: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Mammals.Lion);
				break;
			}
			case 12: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Reptiles.Snake);
				break;
			}
			case 13: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Reptiles.Lizard);
				break;
			}
			case 14: {
				speciesFactory = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);
				a[i] = speciesFactory.getAnimal(Constants.Animals.Reptiles.Alligator);
				break;
			}
			default: {
				System.out.println("Something went wrong");
			}
			}

		}

		int nrOfEmployees = 20;
		Caretaker employees[] = new Caretaker[nrOfEmployees];
		EmployeeAbstractFactory caretakers = new CaretakerFactory();
		for (int i = 0; i < nrOfEmployees; i++) {
			employees[i] = (Caretaker) caretakers.getEmployee(Constants.Employees.Employee.Caretaker);

		}

		for (int i = 0; i < nrOfEmployees; i++) {
			for (int j = 0; j < nrOfAnimals; j++) {
				if (!employees[i].getIsDead() && !a[j].getTakenCareOf()) {
					String result = employees[i].takeCareOf(a[j]);
					if (result.equals(Constants.Employees.Caretakers.TCO_KILLED)) {
						employees[i].setIsDead(true);

					} else if (result.equals(Constants.Employees.Caretakers.TCO_NO_TIME)) {
						continue;
					} else {
						a[j].setTakenCareOf(true);
					}
				}
			}
		}
		boolean allTakenCare = true;
		for (int j = 0; j < nrOfAnimals; j++) {
			if (!a[j].getTakenCareOf())
				System.out.println(j + " The caretakers haven't take care of the " + a[j].getName() + " yet.");
			allTakenCare = false;
		}

		if (allTakenCare)
			System.out.println("Animals are all good");
	}

}
