package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.repositories.AnimalRepository;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

abstract public class Reptile extends Animal {
	private boolean laysEggs;

	public Reptile(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean laysEggs) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);
		this.setLaysEggs(laysEggs);
	}

	public boolean isLayingEggs() {
		return this.laysEggs;
	}

	public void setLaysEggs(boolean laysEggs) {
		this.laysEggs = laysEggs;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, "laysEggs", String.valueOf(this.isLayingEggs()));
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setLaysEggs(Boolean.valueOf(element.getElementsByTagName("laysEggs").item(0).getTextContent()));
	}
}
