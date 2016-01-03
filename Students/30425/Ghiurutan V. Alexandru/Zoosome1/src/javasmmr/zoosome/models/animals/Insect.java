package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.repositories.AnimalRepository;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

abstract public class Insect extends Animal {
	private boolean canFly;
	private boolean isDangerous;

	public Insect(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);
		this.setCanFly(canFly);
		this.setIsDangerous(isDangerous);
	}

	public boolean isFlying() {
		return this.canFly;
	}

	public boolean isDangerous() {
		return this.isDangerous;
	}

	public void setCanFly(boolean canFly) {
		this.canFly = canFly;
	}

	public void setIsDangerous(boolean isDangerous) {
		this.isDangerous = isDangerous;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, "canFly", String.valueOf(this.isFlying()));
		AnimalRepository.createNode(eventWriter, "isDangerous", String.valueOf(this.isDangerous()));
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setCanFly(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
		this.setIsDangerous(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
	}
}
