package repositories;

import android.content.Context;

import org.w3c.dom.Element;

import models.animals.Animal;
import models.animals.Butterfly;
import models.animals.Cow;
import models.animals.Crocodile;
import models.animals.Dove;
import models.animals.Dragon;
import models.animals.LadyBug;
import models.animals.Monkey;
import models.animals.MoonJellyfish;
import models.animals.Nightingale;
import models.animals.SeaHorse;
import models.animals.SeaTurtle;
import models.animals.Spider;
import models.animals.Tiger;
import models.animals.Turtule;
import models.animals.Woodpecker;
import services.factories.Constants;


public class AnimalRepository extends EntityRepository<Animal> {
    private static final String XML_FILENAME = "Animals.xml";

    public AnimalRepository(Context context) {
        super(XML_FILENAME, Constants.XML_TAGS.ANIMAL, context);
    }

    @Override
    protected Animal getEntityFromXmlElement(Element element) {
        String discriminant = element.getElementsByTagName(Constants.XML_TAGS.DISCRIMINANT).item(0).getTextContent();
        switch (discriminant) {
            case Constants.Animals.Insects.Butterfly:
                Animal butterfly = new Butterfly();
                butterfly.decodeFromXml(element);
                return butterfly;
            case Constants.Animals.Insects.LadyBug:
                Animal ladyBug = new LadyBug();
                ladyBug.decodeFromXml(element);
                return ladyBug;
            case Constants.Animals.Insects.Spider:
                Animal spider = new Spider();
                spider.decodeFromXml(element);
                return spider;
            case Constants.Animals.Aquatics.SeaTurtle:
                Animal seaturtle = new SeaTurtle();
                seaturtle.decodeFromXml(element);
                return seaturtle;
            case Constants.Animals.Aquatics.MoonJellyfish:
                Animal jellyfish = new MoonJellyfish();
                jellyfish.decodeFromXml(element);
                return jellyfish;
            case Constants.Animals.Aquatics.SeaHorse:
                Animal seahorse = new SeaHorse();
                seahorse.decodeFromXml(element);
                return seahorse;
            case Constants.Animals.Birds.Dove:
                Animal dove = new Dove();
                dove.decodeFromXml(element);
                return dove;
            case Constants.Animals.Birds.Nightingale:
                Animal nightingale = new Nightingale();
                nightingale.decodeFromXml(element);
                return nightingale;
            case Constants.Animals.Birds.Woodpecker:
                Animal woodpecker = new Woodpecker();
                woodpecker.decodeFromXml(element);
                return woodpecker;
            case Constants.Animals.Mammals.Cow:
                Animal cow = new Cow();
                cow.decodeFromXml(element);
                return cow;
            case Constants.Animals.Mammals.Monkey:
                Animal monkey = new Monkey();
                monkey.decodeFromXml(element);
                return monkey;
            case Constants.Animals.Mammals.Tiger:
                Animal tiger = new Tiger();
                tiger.decodeFromXml(element);
                return tiger;
            case Constants.Animals.Reptiles.Dragon:
                Animal dragon = new Dragon();
                dragon.decodeFromXml(element);
                return dragon;
            case Constants.Animals.Reptiles.Turtule:
                Animal turtle = new Turtule();
                turtle.decodeFromXml(element);
                return turtle;
            case Constants.Animals.Reptiles.Crocodile:
                Animal croco = new Crocodile();
                croco.decodeFromXml(element);
                return croco;

            default:
                break;
        }
        return null;
    }

}
