package com.gellert.zoowsome.services.factories;
import com.gellert.zoowsome.models.animals.Animal;

public abstract class SpeciesFactory {
	public abstract Animal getAnimal(String type) throws Exception;
}

