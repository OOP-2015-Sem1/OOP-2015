package javasmmr.zoosome.services.factories;

/**
 * It represents a way to populate the zoo
 * 
 * @author Alexandru. The class will return a specific factory ,related to the
 *         species that the String describes.
 */
public class AnimalFactory {
	// The class have just one method
	public SpeciesFactory getSpeciesFactory(String type) throws Exception {
		if (Constants.Species.Mammals.equals(type)) {
			return new MammalFactory();
		} else if (Constants.Species.Reptiles.equals(type)) {
			return new ReptileFactory();
		} else if (Constants.Species.Birds.equals(type)) {
			return new BirdFactory();
		} else if (Constants.Species.Insects.equals(type)) {
			return new InsectFactory();
		} else if (Constants.Species.Aquatics.equals(type)) {
			return new AquaticFactory();
		} else if (Constants.Species.Random.equals(type)) {
			return new RandomFactory();
		} else {
			throw new Exception("Invalid species exception.");
		}
	}
}
