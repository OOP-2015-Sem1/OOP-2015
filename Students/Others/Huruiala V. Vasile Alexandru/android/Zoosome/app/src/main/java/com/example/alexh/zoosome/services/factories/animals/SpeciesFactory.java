package com.example.alexh.zoosome.services.factories.animals;

import com.example.alexh.zoosome.models.animals.Animal;

public abstract class SpeciesFactory {
    public abstract Animal getAnimal(String type) throws Exception;

    public abstract Animal getRandomAnimalOfType(String type) throws Exception;
}
