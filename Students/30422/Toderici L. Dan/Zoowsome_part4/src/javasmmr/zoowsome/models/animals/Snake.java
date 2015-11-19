package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Snake extends Reptile
{
	public Snake()
	{
		super (3.0,0.3);
		this.setName("Marcel");
		this.setNrOfLegs(0);
		this.setLaysEggsStatus(true);
	}
	
	public Snake(String name, int nrOfLegs, boolean laysEggsStatus)
	{
		super (3.0,0.3);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggsStatus);
	}
 
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Reptile.Snake);
	}
}
