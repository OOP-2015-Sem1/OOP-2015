package javasmmr.zoowsome.controllers;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.services.factories.AnimalFactory;
import javasmmr.zoowsome.services.factories.Constants;
import javasmmr.zoowsome.services.factories.SpeciesFactory;

public class MainController {

		public static void main(String[] args) throws Exception {
			AnimalFactory abstractFactory = new AnimalFactory();
			SpeciesFactory speciesFactory1 = abstractFactory.getSpeciesFactory(Constants.Species.Mammals);
			SpeciesFactory speciesFactory2 = abstractFactory.getSpeciesFactory(Constants.Species.Aquatics);
			SpeciesFactory speciesFactory3 = abstractFactory.getSpeciesFactory(Constants.Species.Reptiles);
			SpeciesFactory speciesFactory4 = abstractFactory.getSpeciesFactory(Constants.Species.Birds);
			SpeciesFactory speciesFactory5 = abstractFactory.getSpeciesFactory(Constants.Species.Insects);
			
			/*
			
			Animal a1 = speciesFactory1.getAnimal(Constants.Animals.Mammals.Monkey);
			Animal a2 = speciesFactory2.getAnimal(Constants.Animals.Aquatics.Piranha);
			
			System.out.printf("We have a " + a1.getName() + " with %d legs!\n", a1.getNrOfLegs());
			System.out.printf("We have a " + a2.getName() + " with %d legs!\n", a2.getNrOfLegs()); */
			
			SpeciesFactory[] Factories = {speciesFactory1, speciesFactory2, speciesFactory3, speciesFactory4, speciesFactory5};
			String[] category = {"Mammal", "Aquatic", "Reptile", "Bird", "Insect"};
			Animal [] anim = new Animal[51];
			String [][] species = {{"Monkey", "Cow", "Tiger"}, {"Whale", "Piranha", "Shark"}, {"Alligator", "Lizard", "Boa"}, {"Swallow", "Parrot", "Penguin"}, {"Butterfly", "Cockroach", "Spider"}};   
			
			
			int factNo, specNo;
			
			for(int i = 0; i < 50; i++) {
				factNo = (int) (Math.random() * 5);
				specNo = (int) (Math.random() * 3);
				anim[i] = Factories[factNo].getAnimal(species[factNo][specNo]);
				System.out.printf("Animal number: %d is a %s, Name: %s has %d legs\n", i, category[factNo], anim[i].getName(), anim[i].getNrOfLegs());
			}
			
			}
}
