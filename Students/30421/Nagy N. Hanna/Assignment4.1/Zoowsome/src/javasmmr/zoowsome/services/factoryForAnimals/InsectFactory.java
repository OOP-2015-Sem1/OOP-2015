package javasmmr.zoowsome.services.factoryForAnimals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.ButterFly;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Spider;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals.Insects;

public class InsectFactory extends SpeciesFactory{

	
	
	 public Animal getAnimal(String type) {
         if (Constants.Animals.Insects.Butterfly.equals(type)) {
                return new ButterFly(1.0,0); // leave empty constructor, for now!
        } else if (Constants.Animals.Insects.Cockroach.equals(type)) {
             return new Cockroach(0.3,0.2);
                      } 
        else if (Constants.Animals.Insects.Spider.equals(type)){
        	return new Spider(0.8,0.4);
        	
        }
		return null;
       
       }
}
