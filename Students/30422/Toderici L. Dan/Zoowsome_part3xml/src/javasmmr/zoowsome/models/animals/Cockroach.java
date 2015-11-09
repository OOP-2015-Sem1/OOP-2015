package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Cockroach extends Insect 
{
	public Cockroach() 
	{
		super(0.5,0.1);
		setName("Red");
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
	
	public Cockroach(int nbOfLegs) 
	{
		super(0.5,0.1);
		setName("Red");
		setNrOfLegs(nbOfLegs);
		setCanFly(false);
		setIsDangerous(false);
	}
	
	public Cockroach(String name) 
	{
		super(0.5,0.1);
		setName(name);
		setNrOfLegs(10);
		setCanFly(false);
		setIsDangerous(false);
	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Insect.Cockroach);
	}
}
