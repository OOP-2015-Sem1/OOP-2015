package services.factories;
import controllers.*;  
import models.animals.*;

public class ReptileFactory extends SpeciesFactory{


	public Animal getAnimal(String type) {
		 if(Constants.Animals.Reptiles.Iguana.equals(type))
		  {
			  return new Iguana();
		  }
		  else if(Constants.Animals.Reptiles.Turtle.equals(type))
		  {
			  return new Turtle();
		  }
		  else if(Constants.Animals.Reptiles.Snake.equals(type))
		  {
			  return new Snake();
		  }
		  else
		  {
			  System.out.println("Invalid animal!");
			  return null;
		  }
	}
	

}
