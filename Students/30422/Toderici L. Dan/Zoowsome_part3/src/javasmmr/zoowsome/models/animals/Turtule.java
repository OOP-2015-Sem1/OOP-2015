package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Turtule extends Reptile 
{
	public Turtule()
	{
		super(2.0,0.1);
		this.setName("Magda");
	    this.setNrOfLegs(4);
	    this.setLaysEggsStatus(true);
	}
	
	public Turtule(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		super(2.0,0.1);
		this.setName(name);
	    this.setNrOfLegs(nrOfLegs);
	    this.setLaysEggsStatus(laysEggsStatus);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Reptile.Turtule);
	}
}
