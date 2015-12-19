package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.services.factories.Constants;
import javasmmr.zoosome.repositories.AnimalRepository;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

public class Butterfly extends Insect {
	public Butterfly() {
		this(6, "Butterfly", 0.1, 0.0, false, true, false);
	}

	public Butterfly(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean canFly, boolean isDangerous) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, canFly, isDangerous);
	}

	// Interface method implementation.
	// It returns true if the entity interacting with our animal gets killed.
	@Override
	public boolean kill() {
		double percent = Math.random() + getPredisposition();
		return (percent < this.getDangerPerc());
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Butterfly);
	}
}
