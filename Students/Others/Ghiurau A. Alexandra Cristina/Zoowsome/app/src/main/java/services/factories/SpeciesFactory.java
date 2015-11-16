package services.factories;

import models.animals.Animal;

public abstract class SpeciesFactory {
    public abstract Animal getAnimal(String type) throws Exception;
}
