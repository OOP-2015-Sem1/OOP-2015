package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Butterfly extends Insect 
{
	public Butterfly()
	{
		super(0.5, 0);
		this.setName("Lie");
		this.setNrOfLegs(6);
		this.setCanFly(true);
		this.setIsDangerous(false);
	}

	public Butterfly(String name, int nrOfLegs, boolean flyStatus, boolean dangerStatus)
	{
		super(0.5, 0);
		this.setNrOfLegs(nrOfLegs);
		this.setName(name);
		this.setCanFly(flyStatus);
		this.setIsDangerous(dangerStatus);
	}
	public Butterfly( int nrOfLegs, boolean flyStatus, boolean dangerStatus)
	{
		super(0.5, 0);
		this.setNrOfLegs(nrOfLegs);
		this.setName("Lie");
		this.setCanFly(flyStatus);
		this.setIsDangerous(dangerStatus);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Insect.Butterfly);
	}
}
