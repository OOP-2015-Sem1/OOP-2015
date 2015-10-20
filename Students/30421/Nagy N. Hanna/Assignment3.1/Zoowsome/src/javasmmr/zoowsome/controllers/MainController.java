package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.services.factories.AnimalFactory;


import javasmmr.zoowsome.services.factories.SpeciesFactory;
import javasmmr.zoowsome.models.animals.Animals;

public class MainController {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AnimalFactory abstractFactory = new AnimalFactory();
		Animals a1 = null;
		SpeciesFactory speciesFactory1 = null;
		HelpMain help = new HelpMain();

		for (int i = 0; i < 50; i++) {
			int randomSpecies = (int) (1 + Math.random() * 4); //
			int randomSubType = (int) (1 + Math.random() * 3); //
			help.getAnAnimal(abstractFactory, a1, speciesFactory1, randomSpecies, randomSubType);

		}
	}

}
