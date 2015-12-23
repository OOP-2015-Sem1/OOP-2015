package com.example.maria.zoowsome.services.factories.animal;

import com.example.maria.zoowsome.models.animals.Animal;

public abstract class SpeciesFactory {
    public abstract Animal getAnimal(String type) throws Exception;
}
