package javasmmr.zoosome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javasmmr.zoosome.repositories.AnimalRepository;
import org.w3c.dom.Element;

abstract public class Aquatic extends Animal {
	private int avgSwimDepth;
	private WaterType waterType;

	public Aquatic(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			int avgSwimDepth, WaterType waterType) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);// Implicit
																		// call
																		// to
		// the super class
		// Animal parameter
		// constructor.
		this.setAvgSwimDepth(avgSwimDepth);
		this.setWaterType(waterType);
	}

	public int getAvgSwimDepth() {
		return this.avgSwimDepth;
	}

	public WaterType getWaterType() {
		return this.waterType;
	}

	public void setWaterType(WaterType waterType) {
		this.waterType = waterType;
	}

	public void setAvgSwimDepth(int avgSwimDepth) {
		this.avgSwimDepth = avgSwimDepth;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, "avgSwimDepth", String.valueOf(this.getAvgSwimDepth()));
		AnimalRepository.createNode(eventWriter, "waterType", String.valueOf(this.getWaterType()));
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setAvgSwimDepth(Integer.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
		this.setWaterType(WaterType.valueOf(element.getElementsByTagName("waterType").item(0).getTextContent()));
	}
}
