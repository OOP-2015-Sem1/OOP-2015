package javasmmr.zoosome.services.factories;
import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.models.animals.Goldfish;
import javasmmr.zoosome.models.animals.Shark;
import javasmmr.zoosome.models.animals.Whale;
import javasmmr.zoosome.services.Constants;

public class AquaticFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Aquatics.Whale.equals(type)) {
			return new Whale();
		} else if (Constants.Animals.Aquatics.Shark.equals(type)) {
			return new Shark();
		} else if (Constants.Animals.Aquatics.Goldfish.equals(type)) {
			return new Goldfish();
		} else {
			throw new Exception("Invalid mammal exception!");
		}
	}
}
