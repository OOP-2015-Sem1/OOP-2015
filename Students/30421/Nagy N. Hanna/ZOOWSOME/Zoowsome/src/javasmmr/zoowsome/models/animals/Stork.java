package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class Stork extends Bird {

	public Stork(Double maintenance,double dangerPerc) {
		super(maintenance,dangerPerc);
		setName("Stork");
		setNrOfLegs(2);
		setMigrates(true);
		setAvgFlightAltitude(343);

	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Birds.Stork);
		}
}
