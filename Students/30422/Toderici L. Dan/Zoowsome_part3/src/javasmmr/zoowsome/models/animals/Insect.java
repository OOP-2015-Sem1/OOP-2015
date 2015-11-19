package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public class Insect extends Animal
{
	private boolean canFly;
	private boolean isDangerous;

	public Insect(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}

	public void setCanFly (boolean choice) 
	{
		this.canFly = choice;
	}
	public boolean getCanFLy(){
		return canFly;
	}

	public void setIsDangerous (boolean choice) 
	{
		this.isDangerous = choice;
	}
	public boolean getIsDangerous(){
		return isDangerous;
	}

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException 
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "canFly", String.valueOf(getCanFLy()));
		createNode(eventWriter, "isDangerous", String.valueOf(getIsDangerous()));
	}
	
	public void decodeFromXml(Element element) 
	{
		super.decodeFromXml(element);
		setIsDangerous(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
		setCanFly(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
	}
}
