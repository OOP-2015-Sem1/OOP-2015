package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.models.animals.Animal;

public abstract class SpeciesFactory extends Animal{ 
		public abstract Animal getAnimal(String type); 
		
		protected int getRandomLegs(){
			return (int) (Math.random() * 14);
		}
		
		protected String getRandomName(){
			return Long.toHexString(Double.doubleToLongBits(Math.random()));
		}
		
	}
