package services.factories;
import models.animals.*;


public abstract class SpeciesFactory {
	
	public abstract Animal getAnimal(String type);
}
