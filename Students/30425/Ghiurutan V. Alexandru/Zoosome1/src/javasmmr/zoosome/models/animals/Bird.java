package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.repositories.AnimalRepository;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Element;

abstract public class Bird extends Animal {
	private boolean migrates;
	private int avgFlightAltitude;

	public Bird(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf,
			boolean migrates, int avgFlightAltitude) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf);
		this.setMigrates(migrates);
		this.setAvgFlightAltitude(avgFlightAltitude);
	}

	public boolean isMigrating() {
		return this.migrates;
	}

	public int getAvgFlightAltitude() {
		return this.avgFlightAltitude;
	}

	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	public void setAvgFlightAltitude(int avgFlightAltitude) {
		this.avgFlightAltitude = avgFlightAltitude;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		AnimalRepository.createNode(eventWriter, "migrates", String.valueOf(this.isMigrating()));
		AnimalRepository.createNode(eventWriter, "avgFlightAltitude", String.valueOf(this.getAvgFlightAltitude()));
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setMigrates(Boolean.valueOf(element.getElementsByTagName("migrates").item(0).getTextContent()));
		this.setAvgFlightAltitude(
				Integer.valueOf(element.getElementsByTagName("avgFlightAltitude").item(0).getTextContent()));
	}
}
