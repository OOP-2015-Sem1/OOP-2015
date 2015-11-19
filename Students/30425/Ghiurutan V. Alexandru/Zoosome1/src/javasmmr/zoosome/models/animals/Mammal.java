package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.repositories.AnimalRepository;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

abstract public class Mammal extends Animal {
	private float normalBodyTemp;
	private float pereBodyHair;

	public Mammal(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			float normalBodyTemp, float pereBodyHair) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);
		this.setNormalBodyTemp(normalBodyTemp);
		this.setPereBodyHair(pereBodyHair);
	}

	public float getNormalBodyTemp() {
		return this.normalBodyTemp;
	}

	public float getPereBodyHair() {
		return this.pereBodyHair;
	}

	public void setNormalBodyTemp(float normalBodyTemp) {
		this.normalBodyTemp = normalBodyTemp;
	}

	public void setPereBodyHair(float pereBodyHair) {
		this.pereBodyHair = pereBodyHair;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, "normalBodyTemp", String.valueOf(this.getNormalBodyTemp()));
		AnimalRepository.createNode(eventWriter, "pereBodyHair", String.valueOf(this.getPereBodyHair()));
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setNormalBodyTemp(Float.valueOf(element.getElementsByTagName("normalBodyTemp").item(0).getTextContent()));
		this.setPereBodyHair(Float.valueOf(element.getElementsByTagName("pereBodyHair").item(0).getTextContent()));
	}
}
