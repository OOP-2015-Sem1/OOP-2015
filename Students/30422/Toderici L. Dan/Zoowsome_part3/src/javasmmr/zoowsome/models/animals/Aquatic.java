package javasmmr.zoowsome.models.animals;
import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public class Aquatic extends Animal 
{
	private int avgSwimDepth;
	private TypesOfWater typeOfWater;

	public Aquatic(double maintenanceCost, double dangerPerc) {
		super(maintenanceCost, dangerPerc);
	}

	public void setAvgSwimDepth (int avg) 
	{
		this.avgSwimDepth = avg;
	}
	public int getAvgSwimDepth(){
		return avgSwimDepth;
	}

	public TypesOfWater getTypeOfWater() 
	{
		return typeOfWater;
	}
	public void setTypeOfWater(TypesOfWater typeOfWater) 
	{
		this.typeOfWater = typeOfWater;
	}

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "avgSwimDepth", String.valueOf(getAvgSwimDepth()));
		createNode(eventWriter, "typeOfWater", String.valueOf(getTypeOfWater()));
	}
	public void decodeFromXml(Element element) 
	{
		super.decodeFromXml(element);
		setAvgSwimDepth(Integer.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
		setTypeOfWater(TypesOfWater.valueOf(element.getElementsByTagName("typeOfWater").item(0).getTextContent()));
		
	}

}
