package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class Cow extends Mammals {

	
	
	public Cow(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Cow");
		setNrOfLegs(4);
		setPercBodyHair(70);
		setNormalBodyTemp(38.6f);
		
	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Mammals.Cow);
		}

}
