package javasmmr.zoosome.controllers;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.services.Constants;
import javasmmr.zoosome.services.factories.AnimalFactory;
import javasmmr.zoosome.services.factories.SpeciesFactory;

public class MainControllers {
	public static void main(String[] args) throws Exception {
		AnimalFactory abstractFactory = new AnimalFactory();
		SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
		Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Monkey);
		System.out.println("We have an animal with :" + a1.nrOfLegs + " legs");
	}
}
