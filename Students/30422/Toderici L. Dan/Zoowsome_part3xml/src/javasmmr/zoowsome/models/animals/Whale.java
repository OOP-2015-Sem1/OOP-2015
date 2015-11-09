package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Whale extends Aquatic 
{
	public Whale()
	{
		super(7.0,0.5);
		this.setName("BigBubba");
		this.setNrOfLegs(1);
		this.setAvgSwimDepth(250);
		this.setTypeOfWater(TypesOfWater.freshWater);
	}
	public Whale(String name, int nrOfLegs, int avgSwimDepth, TypesOfWater waterType)
	{
		super(7.0,0.5);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setTypeOfWater(waterType);
	}
	public Whale( int avgSwimDepth, TypesOfWater waterType)
	{
		super(7.0,0.5);
		this.setName("BigBubba");
		this.setNrOfLegs(1);
		this.setAvgSwimDepth(avgSwimDepth);
		this.setTypeOfWater(waterType);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Aquatic.Whale);
	}
}
