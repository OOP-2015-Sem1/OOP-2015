package com.example.maria.zoowsome.services.factories.animal;

import com.example.maria.zoowsome.models.animals.Animal;
import com.example.maria.zoowsome.models.animals.Bee;
import com.example.maria.zoowsome.models.animals.Beetle;
import com.example.maria.zoowsome.models.animals.Butterfly;
import com.example.maria.zoowsome.services.factories.Constants;

public class InsectFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Insects.Beetle.equals(type)) {
            return new Beetle(2.0, 0.0);
        } else if (Constants.Animals.Insects.Bee.equals(type)) {
            return new Bee(3.0, 0.6);
        } else if (Constants.Animals.Insects.Butterfly.equals(type)) {
            return new Butterfly(2.0, 0.0);
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }

}
