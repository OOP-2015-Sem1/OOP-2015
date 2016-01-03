package com.example.roxana.zoo.repositories;

import android.content.Context;

import com.example.roxana.zoo.Constants;
import com.example.roxana.zoo.models.animals.Animal;
import com.example.roxana.zoo.models.animals.Butterfly;
import com.example.roxana.zoo.models.animals.Catfish;
import com.example.roxana.zoo.models.animals.Cockroach;
import com.example.roxana.zoo.models.animals.Cow;
import com.example.roxana.zoo.models.animals.Crocodiles;
import com.example.roxana.zoo.models.animals.Dolphin;
import com.example.roxana.zoo.models.animals.Duck;
import com.example.roxana.zoo.models.animals.Lizard;
import com.example.roxana.zoo.models.animals.Monkey;
import com.example.roxana.zoo.models.animals.Pigeon;
import com.example.roxana.zoo.models.animals.Shark;
import com.example.roxana.zoo.models.animals.Sparrow;
import com.example.roxana.zoo.models.animals.Spider;
import com.example.roxana.zoo.models.animals.Tiger;

import org.w3c.dom.Element;

import java.util.ArrayList;

/**
 * Created by Roxana on 10/25/2015.
 */
public class AnimalRepository extends EntityRepository<Animal>{

    private static final String XML_FILENAME = "Animals.xml";

    //this is where I will store all the animals that have to be listed(assign 7)
    private static ArrayList<Animal> animalsToBeListed = new ArrayList<Animal>();

    public static void addAnimalToBeListed(Animal animal){
        animalsToBeListed.add(animal);
    }

    public static ArrayList<Animal>getAnimalsToBeListed(){
        return animalsToBeListed;
    }


    public AnimalRepository(Context context) {

        super(XML_FILENAME, Constants.XML_TAGS.ANIMAL,context);
    }



    @Override
    protected Animal getEntityFromXmlElement(Element element) {
        String discriminant;
        discriminant = element.getElementsByTagName(Constants.XML_TAGS.DISCRIMINANT).item(0)
                .getTextContent();
        switch (discriminant) {

            case Constants.Animals.Insects.Butterfly:
                Animal butterfly = new Butterfly();
                butterfly.decodeFromXml(element);
                return butterfly;

            case Constants.Animals.Aquatics.Catfish:
                Animal catfish = new Catfish();
                catfish.decodeFromXml(element);
                return catfish;

            case Constants.Animals.Insects.Cockroach:
                Animal cockroach = new Cockroach();
                cockroach.decodeFromXml(element);
                return cockroach;

            case Constants.Animals.Mammals.Cow:
                Animal cow = new Cow();
                cow.decodeFromXml(element);
                return cow;

            case Constants.Animals.Reptiles.Crocodiles:
                Animal crocodiles = new Crocodiles();
                crocodiles.decodeFromXml(element);
                return crocodiles;

            case Constants.Animals.Aquatics.Dolphin:
                Animal dolphin = new Dolphin();
                dolphin.decodeFromXml(element);
                return dolphin;

            case Constants.Animals.Birds.Duck:
                Animal duck = new Duck();
                duck.decodeFromXml(element);
                return duck;

            case Constants.Animals.Reptiles.Lizard:
                Animal lizard = new Lizard();
                lizard.decodeFromXml(element);
                return lizard;

            case Constants.Animals.Mammals.Monkey:
                Animal monkey = new Monkey();
                monkey.decodeFromXml(element);
                return monkey;

            case Constants.Animals.Birds.Pigeon:
                Animal pigeon = new Pigeon();
                pigeon.decodeFromXml(element);
                return pigeon;

            case Constants.Animals.Aquatics.Shark:
                Animal shark = new Shark();
                shark.decodeFromXml(element);
                return shark;

            case Constants.Animals.Birds.Sparrow:
                Animal sparrow = new Sparrow();
                sparrow.decodeFromXml(element);
                return sparrow;

            case Constants.Animals.Insects.Spider:
                Animal spider = new Spider();
                spider.decodeFromXml(element);
                return spider;

            case Constants.Animals.Mammals.Tiger:
                Animal tiger = new Tiger();
                tiger.decodeFromXml(element);
                return tiger;

            case Constants.Animals.Reptiles.Turtle:
                Animal turtle = new Butterfly();
                turtle.decodeFromXml(element);
                return turtle;

            default:
                break;
        }

        return null;
    }

}