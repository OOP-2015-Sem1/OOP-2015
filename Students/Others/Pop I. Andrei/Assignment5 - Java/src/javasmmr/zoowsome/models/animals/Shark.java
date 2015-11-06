package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.models.constants.Constants;

public class Shark extends Aquatic {

	public Shark(int nrLegs, String name, int depth, WaterEnum water, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}

	public Shark() {
		this(0, "Shark", 2000, WaterEnum.saltWater, 2.5, 0.9);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Shark);
	}

}
