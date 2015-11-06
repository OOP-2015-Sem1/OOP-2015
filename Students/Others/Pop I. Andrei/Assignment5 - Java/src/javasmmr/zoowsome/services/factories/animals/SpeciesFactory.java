package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;

public abstract class SpeciesFactory extends Animal{ 
		
	public SpeciesFactory(double maintenance, double damage) {
		super(maintenance, damage);
	}

		public abstract Animal getAnimal(String type) throws Exception; 
		
		protected int getRandomLegs(){
			return (int) (Math.random() * 14);
		}
		
		protected String getRandomName(){
			return Long.toHexString(Double.doubleToLongBits(Math.random()));
		}
		
	}
