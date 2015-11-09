package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import javasmmr.zoowsome.models.constants.Constants;

public class Piranha extends Aquatic {

	public Piranha(int nrLegs, String name, int depth, WaterEnum water, double cost, double damage) {
		super(cost, damage);
		setNrOfLegs(nrLegs);
		setName(name);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}

	public Piranha() {
		this(0, "Piranha", 40, WaterEnum.freshWater, 1.0, 0.1);
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Piranha);
	}

}
