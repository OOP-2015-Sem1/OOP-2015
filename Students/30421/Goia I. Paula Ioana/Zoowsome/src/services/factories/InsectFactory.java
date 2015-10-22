package services.factories;
 import models.animals.*;
import controllers.*;
 
public class InsectFactory extends SpeciesFactory {


	public Animal getAnimal(String type) {
		
		if(Constants.Animals.Insects.Butterfly.equals(type)){
			return new Butterfly();
		}
		else if(Constants.Animals.Insects.Spider.equals(type)){
			return new Spider();
		}
		else if (Constants.Animals.Insects.Dragonfly.equals(type)){
			return new Dragonfly();
		}
		else {
			System.out.println("Invalid animal!");
			return null;
		}
		
	}

}
