package services.factories;
import controllers.*;  
import models.animals.*;

public class MammalFactory extends SpeciesFactory{

	public Animal getAnimal(String type) {
		 if(Constants.Animals.Mammals.Tiger.equals(type))
		  {
			  return new Tiger();
		  }
		  else if(Constants.Animals.Mammals.Panda.equals(type))
		  {
			  return new Panda();
		  }
		  else if(Constants.Animals.Mammals.Horse.equals(type))
		  {
			  return new Horse();
		  }
		  else
		  {
			  System.out.println("Invalid animal!");
			  return null;
		  }
	}
	

}
