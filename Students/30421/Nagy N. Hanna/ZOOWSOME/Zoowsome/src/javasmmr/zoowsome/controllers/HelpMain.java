package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.services.factoryForAnimals.AnimalFactory;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;
import javasmmr.zoowsome.services.factoryForAnimals.SpeciesFactory;

public class HelpMain {

	AnimalFactory abstractFactory = new AnimalFactory();
	Animal a1 = null;
	SpeciesFactory speciesFactory1 = null;


	public  HelpMain(AnimalFactory abstractFactory, Animal a1, SpeciesFactory speciesFactory1) {

		this.abstractFactory = abstractFactory;
		this.a1 = a1;
		this.speciesFactory1 = speciesFactory1;

	}

	public Animal getAnAnimal(int randomSpecies, int randomType) {
		switch (randomSpecies) {

		case 1:
			speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);

			// 1 Tiger; 2 Monkey; 3 Cow;
			switch (randomType) {
			case 1:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);
				//System.out.println(" Mammal " + a1.getNrOfLegs() + " with  legs! its name is  " + a1.getName());
				break;
			case 2:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Monkey);
				//System.out.println(" Mammal " + a1.getNrOfLegs() + " with  legs! its name is " + a1.getName());
				break;
			case 3:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Tiger);
				//System.out.println(" Mammal " + a1.getNrOfLegs() + " with  legs! its name is  " + a1.getName());
				break;
			default:
				System.out.println("no");
			}
			break;
		case 2:
			speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);

			// 1 Crocodile; 2 Alligator; 3 Snake;
			switch (randomType) {
			case 1:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Reptiles.Crocodile);
				//System.out.println("Reptiles with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 2:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Reptiles.Alligator);
				//System.out.println("Reptiles with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 3:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Reptiles.Snake);
				//System.out.println("Reptiles with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			default:
				System.out.println("no");

			}
			break;
		case 3:
			speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Insects);

			// 1 Butterfly; 2 Cockroach; 3 Spider;
			switch (randomType) {
			case 1:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Insects.Butterfly);
				//System.out.println(" Insects with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 2:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Insects.Cockroach);
				//System.out.println(" Insects with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 3:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Insects.Spider);
				//System.out.println(" Insects with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			default:
				System.out.println("no");
			}
			break;
		case 4:
			speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);

			// 1 Frog; 2 Newt; 3 Salamander;
			switch (randomType) {
			case 1:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Aquatics.Frog);
				//System.out.println("Aquatics with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 2:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Aquatics.Newt);
				//System.out.println("Aquatics with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 3:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Aquatics.Salamander);
				//System.out.println("Aquatics with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			default:
				System.out.println("no");
			}
			break;
		case 5:
			speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Birds);

			// 1 stork; 2 Pigeon; 3 Eagle;
			switch (randomType) {
			case 1:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Birds.Stork);
			//	System.out.println("  Birds with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 2:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Birds.Pigeon);
			//	System.out.println(" Birds with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			case 3:
				a1 = speciesFactory1.getAnimal(Constants.Animals.Birds.Eagle);
			//	System.out.println(" Birds with " + a1.getNrOfLegs() + " legs! its name is " + a1.getName());
				break;
			default:
				System.out.println("no");

			}
			break;
		}
		return a1;
	}
}
