package javasmmr.zoowsome.repositories;

import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javasmmr.zoowsome.models.animals.Alligator;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.animals.ButterFly;
import javasmmr.zoowsome.models.animals.Cockroach;
import javasmmr.zoowsome.models.animals.Cow;
import javasmmr.zoowsome.models.animals.Frog;
import javasmmr.zoowsome.models.animals.Monkey;
import javasmmr.zoowsome.models.animals.Newt;
import javasmmr.zoowsome.models.animals.Salamander;
import javasmmr.zoowsome.models.animals.Stork;
import javasmmr.zoowsome.models.animals.Tiger;
import javasmmr.zoowsome.models.interfaces.XML_Parsable;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

public class AnimalRepository {

	private static final String XML_FILENAME = "Animals.xml";

	public AnimalRepository() {
	}

	public void save(ArrayList<Animal> animals) throws FileNotFoundException, XMLStreamException {
		XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
		// Create XMLEventWriter
		XMLEventWriter eventWriter = outputFactory.createXMLEventWriter(new FileOutputStream(XML_FILENAME));
		// Create a EventFactory
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		// Create and write Start Tag
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		// Create content open tag
		StartElement configStartElement = eventFactory.createStartElement("", "", "content");
		eventWriter.add(configStartElement);
		eventWriter.add(end);
		for (XML_Parsable animal : animals) {
			StartElement sElement = eventFactory.createStartElement("", "", Constants.XML_TAGS.ANIMAL);
			eventWriter.add(sElement);
			eventWriter.add(end);
			animal.encodeToXml(eventWriter);
			EndElement eElement = eventFactory.createEndElement("", "", Constants.XML_TAGS.ANIMAL);
			eventWriter.add(eElement);
			eventWriter.add(end);
		}
		eventWriter.add(eventFactory.createEndElement("", "", "content"));
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	public ArrayList<Animal> load() throws ParserConfigurationException, SAXException, IOException {
		ArrayList<Animal> animals = new ArrayList<Animal>();
		File fXmlFile = new File(XML_FILENAME);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		NodeList nodeList = doc.getElementsByTagName(Constants.XML_TAGS.ANIMAL);
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				String discriminant = element.getElementsByTagName(Constants.XML_TAGS.DISCRIMINANT).item(0)
						.getTextContent();
				switch (discriminant) {
				case Constants.Animals.Insects.Butterfly:
					Animal butterfly = new ButterFly(1.0, 2);
					butterfly.decodeFromXml(element);
					animals.add(butterfly);
					break;

				case Constants.Animals.Insects.Cockroach:
					Animal cockroach = new Cockroach(1.0, 2);
					cockroach.decodeFromXml(element);
					animals.add(cockroach);
					break;
					
				case Constants.Animals.Insects.Spider:
					Animal spider = new Cockroach(1.0, 2);
					spider.decodeFromXml(element);
					animals.add(spider);
					break;

				case Constants.Animals.Birds.Stork:
					Animal stork = new Stork(3.0, 4.0);
					stork.decodeFromXml(element);
					animals.add(stork);
					break;

				case Constants.Animals.Birds.Eagle:
					Animal eagle = new Stork(3.0, 4.0);
					eagle.decodeFromXml(element);
					animals.add(eagle);
					break;

				case Constants.Animals.Birds.Pigeon:
					Animal pigeon = new Stork(3.0, 4.0);
					pigeon.decodeFromXml(element);
					animals.add(pigeon);
					break;

				case Constants.Animals.Reptiles.Alligator:
					Animal alligator = new Alligator(5.0, 6);
					alligator.decodeFromXml(element);
					animals.add(alligator);
					break;

				case Constants.Animals.Reptiles.Crocodile:
					Animal crocodile = new Alligator(5.0, 6);
					crocodile.decodeFromXml(element);
					animals.add(crocodile);
					break;

				case Constants.Animals.Reptiles.Snake:
					Animal snake = new Alligator(5.0, 6);
					snake.decodeFromXml(element);
					animals.add(snake);
					break;

				case Constants.Animals.Mammals.Cow:
					Animal cow = new Cow(5.0, 6);
					cow.decodeFromXml(element);
					animals.add(cow);
					break;

				case Constants.Animals.Mammals.Monkey:
					Animal monkey = new Monkey(5.0, 6);
					monkey.decodeFromXml(element);
					animals.add(monkey);
					break;

				case Constants.Animals.Mammals.Tiger:
					Animal tiger = new Tiger(5.0, 6);
					tiger.decodeFromXml(element);
					animals.add(tiger);
					break;

				case Constants.Animals.Aquatics.Frog:
					Animal frog = new Frog(5.0, 6);
					frog.decodeFromXml(element);
					animals.add(frog);
					break;

				case Constants.Animals.Aquatics.Newt:
					Animal newt = new Newt(5.0, 6);
					newt.decodeFromXml(element);
					animals.add(newt);
					break;

				case Constants.Animals.Aquatics.Salamander:
					Animal salamander = new Salamander(5.0, 6);
					salamander.decodeFromXml(element);
					animals.add(salamander);
					break;

				default:
					break;

				}
			}
		}
		return animals;
	}

	public static void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// Create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// Create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// Create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

}
