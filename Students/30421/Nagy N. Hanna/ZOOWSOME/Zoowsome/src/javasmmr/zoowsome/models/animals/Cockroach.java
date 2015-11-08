package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

// csótány
public class Cockroach extends Insect {

	public Cockroach(Double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
		setName("Coackroach");
		setNrOfLegs(6);
		canFly = false;
		isDangerous = true;
	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Insects.Cockroach);
		}

	
}
