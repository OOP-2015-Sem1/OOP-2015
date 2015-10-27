package javasmmr.zoowsome.services.factoryForAnimals;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;

import javasmmr.zoowsome.models.animals.Crocodile;
import javasmmr.zoowsome.models.animals.Snake;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals.Reptiles;

public class ReptileFactory extends SpeciesFactory{
         @Override
         public Animal getAnimal(String type) {
                 if (Constants.Animals.Reptiles.Alligator.equals(type)) {
                        return new Alligator(2.0,0.9); // leave empty constructor, for now!
                } else if (Constants.Animals.Reptiles.Snake.equals(type)) {
                     return new Snake(1.3,0.9);
                              } 
                else if (Constants.Animals.Reptiles.Crocodile.equals(type)){
                	return new Crocodile(1.1,0.99);
                	
                }
				return null;
               
               }
}
