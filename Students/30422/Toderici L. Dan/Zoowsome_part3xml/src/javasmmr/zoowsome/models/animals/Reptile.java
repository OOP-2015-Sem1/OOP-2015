package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public class Reptile extends Animal 
{
	private boolean laysEggs;
	
	public Reptile(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}
	
	public void setLaysEggsStatus (boolean status) 
	{
		this.laysEggs = status;
	}
	public boolean getLaysEggsStatus()
	{
		return laysEggs;
	}
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "laysEggs", String.valueOf(getLaysEggsStatus()));
	}
	public void decodeFromXml(Element element) 
	{
		super.decodeFromXml(element);
		setLaysEggsStatus(Boolean.valueOf(element.getElementsByTagName("laysEggs").item(0).getTextContent()));
	}
}
