package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

//import javasmmr.zoowsome.models.animals.Aquatic.waterType;

public class Frog extends Aquatic {

	public Frog(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		setName("Frog");
		setNrOfLegs(4);
		setAvgSwimDepth(24);
		
		//not sure on this ...
		waterType wType = waterType.FreshWater;

	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
		Constants.Animals.Aquatics.Frog);
		}

}
