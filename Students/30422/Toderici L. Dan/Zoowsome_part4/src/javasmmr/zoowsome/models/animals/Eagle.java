package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Eagle extends Bird 
{
	
	public Eagle()
	{
		super(3.5,0.4);
		this.setName("Hunter");
		this.setNrOfLegs(2);
		this.setMigratesStatus(false);
		this.setAvgFlightAltitude(150);
	}
	
	public Eagle(String name, int nrOfLegs, boolean migrationStatus, int avgFlightAltitude)
	{
		super(3.5,0.4);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	
	public Eagle( boolean migrationStatus, int avgFlightAltitude)
	{
		super(3.5,0.4);
		this.setName("Hunter");
		this.setNrOfLegs(2);
		this.setMigratesStatus(migrationStatus);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Bird.Eagle);
	}
}
