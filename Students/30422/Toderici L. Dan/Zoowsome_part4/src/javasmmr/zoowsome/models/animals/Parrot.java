package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Parrot extends Bird 
{
	public Parrot()
	{
		super(3.0,0.1);
		this.setName("Coco");
		this.setNrOfLegs(2);
		this.setAvgFlightAltitude(20);
		this.setMigratesStatus(false);
	}
	
	public Parrot(String name, int nrOfLegs, int avgFlightAltitude , boolean migrationStatus)
	{
		super(3.0,0.1);
		this.setName(name);
		this.setNrOfLegs(2);
		this.setAvgFlightAltitude(avgFlightAltitude);
		this.setMigratesStatus(migrationStatus);
	}
	
	public Parrot( int avgFlightAltitude , boolean migrationStatus)
	{
		super(3.0,0.1);
		this.setName("Coco");
		this.setNrOfLegs(2);
		this.setAvgFlightAltitude(avgFlightAltitude);
		this.setMigratesStatus(migrationStatus);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Bird.Parrot);
	}
}
