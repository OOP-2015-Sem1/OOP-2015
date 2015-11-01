package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.animals.Animal;

//SpeciesFactory class is an abstract class which contains the abstract method getAnimal .
public abstract class SpeciesFactory {
	// This method will return a specific Animal type related to the factory
	// returned in the AnimalFactory.
	// Each class that extends SpeciesFactory will need to define the getAnimal
	// method.
	public abstract Animal getAnimal(String type) throws Exception;
}
