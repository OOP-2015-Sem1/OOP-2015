package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.Piranha;
import javasmmr.zoowsome.models.animals.Shark;
import javasmmr.zoowsome.models.animals.WaterEnum;
import javasmmr.zoowsome.models.animals.Whale;
import javasmmr.zoowsome.models.constants.Constants;

public class AquaticFactory extends SpeciesFactory {
	public AquaticFactory(double maintenance, double damage) {
		super(maintenance, damage);
	}

	public Animal getAnimal(String type) throws Exception {

		int legs = getRandomLegs();
		String name = getRandomName();
		int depth = getRandomDepth();
		WaterEnum water = getRandomWater();

		if (Constants.Animals.Aquatics.Shark.equals(type)) {
			//return new Shark(legs, name, depth, water);
			return new Shark();
		} else if (Constants.Animals.Aquatics.Whale.equals(type)) {
			//return new Whale(legs, name, depth, water);
			return new Whale();
		} else {
			if (Constants.Animals.Aquatics.Piranha.equals(type))
				//return new Piranha(legs, name, depth, water);
				return new Piranha();
			else
				throw new Exception("Invalid animal exception");
		}
	}

	private int getRandomDepth() {
		return (int) (Math.random() * 45);
	}

	private WaterEnum getRandomWater() {
		int nr = (int) (Math.random() * 40);
		if (nr % 2 == 0)
			return WaterEnum.saltWater;
		else
			return WaterEnum.freshWater;
	}

}
