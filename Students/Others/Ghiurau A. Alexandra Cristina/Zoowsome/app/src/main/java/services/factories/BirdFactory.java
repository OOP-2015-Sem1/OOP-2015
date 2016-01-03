package services.factories;


import models.animals.Animal;
import models.animals.Dove;
import models.animals.Nightingale;
import models.animals.Woodpecker;

public class BirdFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Birds.Woodpecker.equals(type)) {
            return new Woodpecker();
        } else if (Constants.Animals.Birds.Dove.equals(type)) {
            return new Dove();
        } else if (Constants.Animals.Birds.Nightingale.equals(type)) {
            return new Nightingale();
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }

}
