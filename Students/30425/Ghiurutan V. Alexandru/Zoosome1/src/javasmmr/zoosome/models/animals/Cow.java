package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.repositories.AnimalRepository;
import javasmmr.zoosome.services.factories.Constants;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

public class Cow extends Mammal {
	public Cow() {
		this(4, "Cow", 0.8, 0.3, false, 38.0F, 95.0F);
	}

	public Cow(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			float normalBodyTemp, float pereBodyHair) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, normalBodyTemp, pereBodyHair);
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
		AnimalRepository.createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Mammals.Cow);
	}
}
