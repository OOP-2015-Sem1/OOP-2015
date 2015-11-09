package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.services.factories.Constants;

public class Cow extends Mammal
{
	public Cow()
	{
		super (1.5,0.1);
		this.setName("Shushan");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(38.6f);
		this.setPercBodyHair(80.0f);
	}
	
	public Cow( String name, int nrOfLegs, float normalBodyTemp, float percBodyHair )
	{
		super (1.5,0.1);
		this.setName(name);
		this.setNrOfLegs(nrOfLegs);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
	public Cow( float normalBodyTemp, float percBodyHair )
	{
		super (1.5,0.1);
		this.setName("Shusan");
		this.setNrOfLegs(4);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPercBodyHair(percBodyHair);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animal.Mammal.Cow);
	}
}
