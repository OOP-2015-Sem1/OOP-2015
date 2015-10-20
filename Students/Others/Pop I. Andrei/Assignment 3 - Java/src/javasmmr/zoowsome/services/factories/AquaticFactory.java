package javasmmr.zoowsome.services.factories;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Piranha;
import javasmmr.zoowsome.models.animals.Shark;
import javasmmr.zoowsome.models.animals.WaterEnum;
import javasmmr.zoowsome.models.animals.Whale;

public class AquaticFactory extends SpeciesFactory{
public Animal getAnimal(String type){
		
		
	int legs = getRandomLegs();
	String name = getRandomName();
	int depth = getRandomDepth();
	WaterEnum water = getRandomWater();
	
	
	if (Constants.Animals.Aquatics.Shark.equals(type)) { 
			return new Shark(legs, name, depth, water); // leave empty constructor, for now! 
			} 
		else 
			if (Constants.Animals.Aquatics.Whale.equals(type)) { 
				return new Whale(legs, name, depth, water); 
				} 
			else { 
				if(Constants.Animals.Aquatics.Piranha.equals(type))
					return new Piranha(legs, name, depth, water);
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

	private int getRandomDepth() {
		return (int) (Math.random() * 45);
	}
	
	private WaterEnum getRandomWater() {
		int nr = (int) (Math.random() * 40);
		if(nr % 2 == 0)
			return WaterEnum.saltWater;
		else
			return WaterEnum.freshWater;
	}

}
