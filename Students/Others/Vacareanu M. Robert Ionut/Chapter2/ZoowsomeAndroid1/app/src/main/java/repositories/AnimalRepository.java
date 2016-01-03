package repositories;

import android.content.Context;

import org.w3c.dom.Element;

import models.animals.Animal;
import models.animals.Butterfly;
import models.animals.Cockroach;
import models.animals.Cow;
import models.animals.Crocodile;
import models.animals.Goldfish;
import models.animals.Lizard;
import models.animals.Monkey;
import models.animals.Nightjar;
import models.animals.Owl;
import models.animals.Shark;
import models.animals.Snake;
import models.animals.Spider;
import models.animals.Tiger;
import models.animals.Whale;
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
		case Constants.Animals.Insects.Cockroach:
			Animal cockroach = new Cockroach();
			cockroach.decodeFromXml(element);
			return cockroach;
		case Constants.Animals.Insects.Spider:
			Animal spider = new Spider();
			spider.decodeFromXml(element);
			return spider;
		case Constants.Animals.Birds.Nightjar:
			Animal nightjar = new Nightjar();
			nightjar.decodeFromXml(element);
			return nightjar;
		case Constants.Animals.Birds.Owl:
			Animal owl = new Owl();
			owl.decodeFromXml(element);
			return owl;
		case Constants.Animals.Birds.Woodpecker:
			Animal woodpecker = new Woodpecker();
			woodpecker.decodeFromXml(element);
			return woodpecker;
		case Constants.Animals.Aquatics.Goldfish:
			Animal goldfish = new Goldfish();
			goldfish.decodeFromXml(element);
			return goldfish;
		case Constants.Animals.Aquatics.Shark:
			Animal shark = new Shark();
			shark.decodeFromXml(element);
			return shark;
		case Constants.Animals.Aquatics.Whale:
			Animal whale = new Whale();
			whale.decodeFromXml(element);
			return whale;
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
		case Constants.Animals.Reptiles.Crocodile:
			Animal crocodile = new Crocodile();
			crocodile.decodeFromXml(element);
			return crocodile;
		case Constants.Animals.Reptiles.Lizard:
			Animal lizard = new Lizard();
			lizard.decodeFromXml(element);
			return lizard;
		case Constants.Animals.Reptiles.Snake:
			Animal snake = new Snake();
			snake.decodeFromXml(element);
			return snake;
		default:
			break;
		}
		return null;
	}

}
