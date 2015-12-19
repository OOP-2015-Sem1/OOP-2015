package com.example.alexh.zoosome.repositories;

import android.content.Context;

import org.w3c.dom.Element;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.models.animals.Scorpion;
import com.example.alexh.zoosome.models.animals.Bass;
import com.example.alexh.zoosome.models.animals.Bear;
import com.example.alexh.zoosome.models.animals.Beetle;
import com.example.alexh.zoosome.models.animals.Clownfish;
import com.example.alexh.zoosome.models.animals.Crocodile;
import com.example.alexh.zoosome.models.animals.Dove;
import com.example.alexh.zoosome.models.animals.Dragon;
import com.example.alexh.zoosome.models.animals.Dragonfly;
import com.example.alexh.zoosome.models.animals.Eagle;
import com.example.alexh.zoosome.models.animals.Hummingbird;
import com.example.alexh.zoosome.models.animals.Kangaroo;
import com.example.alexh.zoosome.models.animals.Lionfish;
import com.example.alexh.zoosome.models.animals.Lizard;
import com.example.alexh.zoosome.models.animals.Mangonel;
import com.example.alexh.zoosome.models.animals.Mantis;
import com.example.alexh.zoosome.models.animals.Ostrich;
import com.example.alexh.zoosome.models.animals.Ram;
import com.example.alexh.zoosome.models.animals.Rhinoceros;
import com.example.alexh.zoosome.models.animals.Spider;
import com.example.alexh.zoosome.models.animals.Surgeonfish;
import com.example.alexh.zoosome.models.animals.Trebuchet;
import com.example.alexh.zoosome.models.animals.Turtle;
import com.example.alexh.zoosome.models.animals.Whale;
import com.example.alexh.zoosome.services.factories.Constants;

public class AnimalRepository extends EntityRepository<Animal> {
	private static final String XML_FILENAME = "Animals.xml";

	public AnimalRepository(Context context) {
		super(XML_FILENAME, Constants.XML_TAGS.ANIMAL, context);
	}

	//@Override
	protected Animal getEntityFormXMLElement(Element element) {
		String discriminant = element.getElementsByTagName(Constants.XML_TAGS.DISCRIMINANT).item(0).getTextContent();

		Animal a;
		switch (discriminant) {
		case Constants.Animals.Mammals.BEAR:
			a = new Bear();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Mammals.KANGAROO:
			a = new Kangaroo();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Mammals.WHALE:
			a = new Whale();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Mammals.RHINOCEROS:
			a = new Rhinoceros();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Reptiles.LIZARD:
			a = new Lizard();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Reptiles.TURTLE:
			a = new Turtle();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Reptiles.CROCODILE:
			a = new Crocodile();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Reptiles.DRAGON:
			a = new Dragon();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Birds.DOVE:
			a = new Dove();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Birds.OSTRICH:
			a = new Ostrich();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Birds.EAGLE:
			a = new Eagle();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Birds.HUMMINGBIRD:
			a = new Hummingbird();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Aquatics.BASS:
			a = new Bass();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Aquatics.CLOWNFISH:
			a = new Clownfish();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Aquatics.SURGEONFISH:
			a = new Surgeonfish();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Aquatics.LIONFISH:
			a = new Lionfish();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Insects.BEETLE:
			a = new Beetle();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Insects.SPIDER:
			a = new Spider();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Insects.MANTIS:
			a = new Mantis();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Insects.DRAGONFLY:
			a = new Dragonfly();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Sieges.RAM:
			a = new Ram();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Sieges.MANGONEL:
			a = new Mangonel();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Sieges.SCORPION:
			a = new Scorpion();
			a.decodeFromXML(element);
			return a;

		case Constants.Animals.Sieges.TREBUCHET:
			a = new Trebuchet();
			a.decodeFromXML(element);
			return a;

		default:
			break;
		}

		return null;
	}

}
