package com.example.maria.zoowsome.services.factories.animal;

import com.example.maria.zoowsome.models.animals.Animal;
import com.example.maria.zoowsome.models.animals.Iguana;
import com.example.maria.zoowsome.models.animals.Turtle;
import com.example.maria.zoowsome.models.animals.Viper;
import com.example.maria.zoowsome.services.factories.Constants;

public class ReptileFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Reptiles.Iguana.equals(type)) {
            return new Iguana(3.0, 0.1);
        } else if (Constants.Animals.Reptiles.Turtle.equals(type)) {
            return new Turtle(5.0, 0.0);
        } else if (Constants.Animals.Reptiles.Viper.equals(type)) {
            return new Viper(3.0, 1.0);
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }
}
