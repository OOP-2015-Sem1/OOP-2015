package services.factories;
import controllers.*;  
import models.animals.*;


public class AquaticFactory extends SpeciesFactory {
	
	public Animal getAnimal(String type){
		if(Constants.Animals.Aquatics.Shark.equals(type)){
			return new Shark();
		}
		else if(Constants.Animals.Aquatics.ClownFish.equals(type)){
			return new ClownFish();
		}
		else if (Constants.Animals.Aquatics.JellyFish.equals(type)){
			return new JellyFish();
		}
		else{
			System.out.println("Invalid animal!");
			return null;
		}
		
	}
	

}
