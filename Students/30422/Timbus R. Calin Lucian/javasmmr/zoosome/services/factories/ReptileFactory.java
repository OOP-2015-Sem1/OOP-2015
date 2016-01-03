package javasmmr.zoosome.services.factories;
import javasmmr.zoosome.models.animals.Alligator;
import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.models.animals.Lizard;
import javasmmr.zoosome.models.animals.Snake;
import javasmmr.zoosome.services.Constants;

public class ReptileFactory extends SpeciesFactory {
	@Override
	public Animal getAnimal(String type) throws Exception {
		if (Constants.Animals.Reptile.Lizard.equals(type)) {
			return new Lizard();
		} else if (Constants.Animals.Reptile.Alligator.equals(type)) {
			return new Alligator();
		} else if (Constants.Animals.Reptile.Snake.equals(type)) {
			return new Snake();
		} else {
			throw new Exception("Invalid mammal exception!");
		}
	}
}
