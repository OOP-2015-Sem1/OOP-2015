package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Lizard extends Reptile
{
	
	public Lizard()
	{
		super(4.5,0.35);
		this.setName("Timo");
		this.setNrOfLegs(4);
		this.setLaysEggsStatus(true);
	}
	
	public Lizard(String name, int nrOfLegs, boolean laysEggs)
	{
		super(4.5,0.35);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setLaysEggsStatus(laysEggs);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Reptile.Lizard);
	}

}
