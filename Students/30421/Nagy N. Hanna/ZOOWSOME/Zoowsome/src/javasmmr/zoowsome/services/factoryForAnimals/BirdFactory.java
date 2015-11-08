package javasmmr.zoowsome.services.factoryForAnimals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Eagle;
import javasmmr.zoowsome.models.animals.Pigeon;
import javasmmr.zoowsome.models.animals.Stork;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals.Birds;


public class BirdFactory extends SpeciesFactory{
	
	 public Animal getAnimal(String type) {
         if (Constants.Animals.Birds.Stork.equals(type)) {
                return new Stork(1.0,0); // leave empty constructor, for now!
        } else if (Constants.Animals.Birds.Eagle.equals(type)) {
             return new Eagle(1.0,0.5);
                      } 
        else if (Constants.Animals.Birds.Pigeon.equals(type)){
        	return new Pigeon(1.2,0.0);
        	
        }
		return null;
       
       }

}
