package com.example.alexh.zoosome.services.factories.animals;

import com.example.alexh.zoosome.services.factories.Constants;

public class AnimalFactory {
    public SpeciesFactory getSpeciesFactory(final String type) throws Exception {
        if (Constants.Animals.Mammals.NAME.equals(type)) {
            return new MammalFactory();
        } else if (Constants.Animals.Reptiles.NAME.equals(type)) {
            return new ReptileFactory();
        } else if (Constants.Animals.Birds.NAME.equals(type)) {
            return new BirdFactory();
        } else if (Constants.Animals.Aquatics.NAME.equals(type)) {
            return new AquaticFactory();
        } else if (Constants.Animals.Insects.NAME.equals(type)) {
            return new InsectFactory();
        } else if (Constants.Animals.Sieges.NAME.equals(type)) {
            return new SiegeWorkshop();
        } else {
            throw new Exception("Invalid class exception!");
        }

    }
}
