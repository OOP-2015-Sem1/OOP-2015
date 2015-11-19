package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public abstract class Aquatic extends Animal {

	private Integer avgSwimDepth;

	protected enum waterType {
		SaltWater, FreshWater
	}

	public Aquatic(Double maintenanceCost, double dangerPerc) {
		super(maintenanceCost,dangerPerc);
		
	}

	public Integer getAvgSwimDepth() {
		return avgSwimDepth;
	}

	public void setAvgSwimDepth(Integer avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "avgSwimDepth", String.valueOf(getAvgSwimDepth()));
		
		} 
	public void decodeFromXml(Element element) {
		setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
		
		}
	
	

}
