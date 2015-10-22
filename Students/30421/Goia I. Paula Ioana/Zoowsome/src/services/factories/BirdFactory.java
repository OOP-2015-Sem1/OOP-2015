package services.factories;

import controllers.*;
import models.animals.*;

public class BirdFactory extends SpeciesFactory {
	public Animal getAnimal(String type){
		 if(Constants.Animals.Birds.Eagle.equals(type))
		  {
			  return new Eagle();
		  }
		  else if(Constants.Animals.Birds.Hawk.equals(type))
		  {
			  return new Hawk();
		  }
		  else if(Constants.Animals.Birds.Sparrow.equals(type))
		  {
			  return new Sparrow();
		  }
		  else
		  {
			  System.out.println("Invalid animal!");
			  return null;
		  }
	}

}
