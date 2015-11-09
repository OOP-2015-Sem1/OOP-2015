package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public abstract class Aquatic extends Animal {

	public Aquatic(double maintenance, double damage) {
		super(maintenance, damage);
	}

	private int avgSwimDepth;
	private WaterEnum waterType;
	
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "avgSwmiDepth", String.valueOf(getAvgSwimDepth()));
		createNode(eventWriter, "waterType", String.valueOf(getWaterType()));
	}
	
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		setAvgSwimDepth(Integer.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
		setWaterType(WaterEnum.valueOf(element.getElementsByTagName("waterType").item(0).getTextContent()));
		}
	
	public int getAvgSwimDepth() {
		return avgSwimDepth;
	}

	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}

	public WaterEnum getWaterType() {
		return waterType;
	}

	public void setWaterType(WaterEnum waterType) {
		this.waterType = waterType;
	}
}
