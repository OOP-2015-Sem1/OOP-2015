package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class ButterFly extends Insect{

	public ButterFly(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("ButterFly");
		setNrOfLegs(6);
		setCanFly(true);
		setIsDangerous(false);
	}
	

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Insects.Butterfly);
		}

	
	
}
