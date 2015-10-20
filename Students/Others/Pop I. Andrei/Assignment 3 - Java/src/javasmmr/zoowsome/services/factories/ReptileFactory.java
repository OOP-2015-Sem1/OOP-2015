package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Boa;
import javasmmr.zoowsome.models.animals.Lizard;

public class ReptileFactory extends SpeciesFactory {
	
	public Animal getAnimal(String type){
		
		int legs = getRandomLegs();
		String name = getRandomName();
		boolean eggs = getRandomEggs();
		
		if (Constants.Animals.Reptiles.Alligator.equals(type)) { 
			return new Alligator(legs, name, eggs); // leave empty constructor, for now! 
			} 
		else 
			if (Constants.Animals.Reptiles.Lizard.equals(type)) { 
				return new Lizard(legs, name, eggs); 
				} 
			else { 
				if(Constants.Animals.Reptiles.Boa.equals(type))
					return new Boa(legs, name, eggs);
				else
					try {
						throw new Exception("Invalid animal"); // numai asa am reusit sa-l fac sa nu mai dea eroare. Te rog sa-mi spui daca este vreo metoda mai eleganta pt throw exception
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				}
		
		return null;
	}
	
	private boolean getRandomEggs() {
		int nr = (int) (Math.random() * 12);
		if(nr%2 == 0)
			return true;
		else
			return false;
	}

}
