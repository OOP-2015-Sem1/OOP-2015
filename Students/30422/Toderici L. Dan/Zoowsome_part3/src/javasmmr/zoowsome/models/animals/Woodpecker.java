package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Woodpecker extends Bird 
{
	public Woodpecker()
	{
		super(2.0,0.15);
		this.setName("Steve");
		this.setNrOfLegs(2);
		this.setMigratesStatus(true);
		this.setAvgFlightAltitude(70);
	}
	
	public Woodpecker(String name, int nrOfLegs, boolean migrationStatus, int avgFlightAltitude)
	{
		super(2.0,0.15);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	public Woodpecker( boolean migrationStatus, int avgFlightAltitude)
	{
		super(2.0,0.15);
		this.setName("Steve");
		this.setNrOfLegs(2);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Bird.Woodpecker);
	}
}
