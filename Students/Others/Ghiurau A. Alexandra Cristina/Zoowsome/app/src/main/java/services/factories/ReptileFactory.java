package services.factories;


import models.animals.Animal;
import models.animals.Crocodile;
import models.animals.Dragon;
import models.animals.Turtule;

public class ReptileFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Reptiles.Crocodile.equals(type)) {
            return new Crocodile();
        } else if (Constants.Animals.Reptiles.Dragon.equals(type)) {
            return new Dragon();
        } else if (Constants.Animals.Reptiles.Turtule.equals(type)) {
            return new Turtule();
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }

}
