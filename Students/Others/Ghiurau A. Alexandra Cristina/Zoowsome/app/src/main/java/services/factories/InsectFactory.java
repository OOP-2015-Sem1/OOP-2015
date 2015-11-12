package services.factories;


import models.animals.Animal;
import models.animals.Butterfly;
import models.animals.LadyBug;
import models.animals.Spider;

public class InsectFactory extends SpeciesFactory {

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Insects.Spider.equals(type)) {
            return new Spider();
        } else if (Constants.Animals.Insects.Butterfly.equals(type)) {
            return new Butterfly();
        } else if (Constants.Animals.Insects.LadyBug.equals(type)) {
            return new LadyBug();
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }

}
