package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;


public abstract class Bird extends Animal {

	protected boolean migrates;
	protected Integer avgFlightAltitude;

	public Bird(Double maintenanceCost,double dangerPerc) {
		super(maintenanceCost,dangerPerc);
	}

	public void setMigrates(boolean migrates) {
		this.migrates = migrates;
	}

	public void setAvgFlightAltitude(Integer avg) {
		this.avgFlightAltitude = avg;
	}

	public boolean getMigrates() {
		return this.migrates;
	}

	public Integer getAvgFlightAltitude() {
		return this.avgFlightAltitude;
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "migrates", String.valueOf(getMigrates()));
		createNode(eventWriter, "avgFlightAltitude", String.valueOf(getAvgFlightAltitude()));
		}
	
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		setMigrates(Boolean.valueOf((element.getElementsByTagName("migrates").item(0).getTextContent())));
		setAvgFlightAltitude(Integer.valueOf(element.getElementsByTagName("avgFlightAltitude").item(0).getTextContent()));
		}
}
