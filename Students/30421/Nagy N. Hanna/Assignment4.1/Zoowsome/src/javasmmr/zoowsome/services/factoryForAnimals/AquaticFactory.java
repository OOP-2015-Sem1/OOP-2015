package javasmmr.zoowsome.services.factoryForAnimals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Frog;
import javasmmr.zoowsome.models.animals.Newt;
import javasmmr.zoowsome.models.animals.Salamander;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals;
import javasmmr.zoowsome.services.factoryForAnimals.Constants.Animals.Aquatics;

public class AquaticFactory extends SpeciesFactory{

	
	 public Animal getAnimal(String type) {
         if (Constants.Animals.Aquatics.Frog.equals(type)) {
                return new Frog(0.5,0.01); // leave empty constructor, for now!
        } else if (Constants.Animals.Aquatics.Newt.equals(type)) {
             return new Newt(0.6,0.1);
                      } 
        else if (Constants.Animals.Aquatics.Salamander.equals(type)){
        	return new Salamander(0.6,0.1);
        	
        }
		return null;
       
       }
}
