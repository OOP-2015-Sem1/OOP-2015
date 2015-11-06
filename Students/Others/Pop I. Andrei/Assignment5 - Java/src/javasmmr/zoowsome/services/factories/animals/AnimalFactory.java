package javasmmr.zoowsome.services.factories.animals;

import javasmmr.zoowsome.models.constants.Constants;

public class AnimalFactory {

	public SpeciesFactory getSpeciesFactory(String type) throws Exception{

		if (Constants.Species.Mammals.equals(type)) {
			return new MammalFactory(0,0);
		} else if (Constants.Species.Reptiles.equals(type)) {
			return new ReptileFactory(0,0);
		} else if (Constants.Species.Birds.equals(type)) {
			return new BirdFactory(0,0);
		} else if (Constants.Species.Insects.equals(type)) {
			return new InsectFactory(0,0);
		} else if (Constants.Species.Aquatics.equals(type)) {
			return new AquaticFactory(0,0);
		} else
			throw new Exception("Invalid animal exception");
	}
}
