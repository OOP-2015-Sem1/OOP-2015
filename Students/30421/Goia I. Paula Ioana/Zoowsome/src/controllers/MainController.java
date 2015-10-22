package controllers;
import models.animals.*;
import services.factories.*;


public class MainController {

	public static void main(String[] args) throws Exception {
			AnimalFactory animalFactory = new AnimalFactory();
			SpeciesFactory mammalFactory = animalFactory.getSpeciesFactory(Constants.Species.Mammals);
			Animal a1 = mammalFactory.getAnimal(Constants.Animals.Mammals.Tiger);
			System.out.println("We have a/an " +a1.getName()+" which has "+a1.getNrOfLegs()+" legs");
			
	}

}
