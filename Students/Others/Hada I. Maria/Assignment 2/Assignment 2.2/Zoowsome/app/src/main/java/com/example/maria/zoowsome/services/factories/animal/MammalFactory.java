package com.example.maria.zoowsome.services.factories.animal;

import com.example.maria.zoowsome.models.animals.Animal;
import com.example.maria.zoowsome.models.animals.Cow;
import com.example.maria.zoowsome.models.animals.Monkey;
import com.example.maria.zoowsome.models.animals.Tiger;
import com.example.maria.zoowsome.services.factories.Constants;

public class MammalFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Mammals.Cow.equals(type)) {
            return new Cow(5.0, 0.0);
        } else if (Constants.Animals.Mammals.Monkey.equals(type)) {
            return new Monkey(6.0, 0.2);
        } else if (Constants.Animals.Mammals.Tiger.equals(type)) {
            return new Tiger(7.0, 0.85);
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }
}

