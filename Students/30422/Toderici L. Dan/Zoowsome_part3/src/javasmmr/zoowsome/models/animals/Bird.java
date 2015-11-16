package javasmmr.zoowsome.models.animals;
import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public class Bird extends Animal
{
	private boolean migrates;
	private int avgFlightAltitude;
	
	public Bird(double maintenanceCost, double dangerPerc) 
	{
		super(maintenanceCost, dangerPerc);
	}
	
	public void setMigratesStatus (boolean status) 
	{
		this.migrates = status;
	}
	public boolean getMigratesStatus(){
		return migrates;
	}
	
	public void setAvgFlightAltitude (int avg) 
	{
		this.avgFlightAltitude = avg;
	}
	public int getAvgFlightAltitude(){
		return avgFlightAltitude;
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "migrates", String.valueOf(getMigratesStatus()));
		createNode(eventWriter, "avgFlightAltitude", String.valueOf(getAvgFlightAltitude()));
	}
	
	public void decodeFromXml(Element element) 
	{
		super.decodeFromXml(element);
		setAvgFlightAltitude(Integer.valueOf(element.getElementsByTagName("avgFlightAltitude").item(0).getTextContent()));
	    setMigratesStatus(Boolean.valueOf(element.getElementsByTagName("migrates").item(0).getTextContent()));
	}
}
