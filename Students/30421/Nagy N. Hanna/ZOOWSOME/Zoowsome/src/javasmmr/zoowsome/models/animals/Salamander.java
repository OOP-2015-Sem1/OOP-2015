package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Salamander extends Aquatic {

	public Salamander(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Salamander");
		setNrOfLegs(4);
		setAvgSwimDepth(20);
		
		//not sure on this ...
		waterType wType = waterType.SaltWater;

	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Aquatics.Salamander);
		}
}
