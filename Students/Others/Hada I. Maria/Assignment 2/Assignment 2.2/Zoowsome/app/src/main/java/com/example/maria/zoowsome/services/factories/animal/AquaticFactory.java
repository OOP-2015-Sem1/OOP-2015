package com.example.maria.zoowsome.services.factories.animal;

import com.example.maria.zoowsome.models.animals.Animal;
import com.example.maria.zoowsome.models.animals.Dolphin;
import com.example.maria.zoowsome.models.animals.Jellyfish;
import com.example.maria.zoowsome.models.animals.Swordfish;
import com.example.maria.zoowsome.services.factories.Constants;

public class AquaticFactory extends SpeciesFactory {


    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Aquatics.Dolphin.equals(type)) {
            return new Dolphin(6.0, 0.25);
        } else if (Constants.Animals.Aquatics.Jellyfish.equals(type)) {
            return new Jellyfish(3.0, 1.0);
        } else if (Constants.Animals.Aquatics.Swordfish.equals(type)) {
            return new Swordfish(4.0, 0.75);
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }
}
