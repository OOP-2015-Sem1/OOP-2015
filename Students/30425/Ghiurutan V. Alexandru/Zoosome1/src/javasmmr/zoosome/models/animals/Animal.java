package javasmmr.zoosome.models.animals;

import javasmmr.zoosome.models.interfaces.XML_Parsable;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import org.w3c.dom.Element;
import javasmmr.zoosome.repositories.AnimalRepository;

/**
 * 
 * @author Alexandru This is the parent class for the whole package. It is
 *         abstract ,so it may have abstract methods.
 */
abstract public class Animal implements Killer, XML_Parsable {
	private int nrOfLegs;
	private String name;
	// represents the number of hours per week an animal require attention from
	// one or more employees.
	// Its value ranges from 0.1 to 0.8.
	private double maintenanceCost;
	// This field shows how dangerous an animal could be in percents.[0,1]
	private double dangerPerc;
	// Field that shows if our specific animal is taken care of.
	// It is set to false by default.
	private boolean takenCareOf;

	// Constructor with parameters.
	public Animal(int nrOfLegs, String name, double maintenanceCost, double dangerPerc, boolean takenCareOf) {
		this.setNrOfLegs(nrOfLegs);
		this.setName(name);
		this.setTakenCareOf(takenCareOf);
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
	}

	public int getNrOfLegs() {
		return this.nrOfLegs;
	}

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMaintenanceCost(double maintenanceCost) {
		this.maintenanceCost = maintenanceCost;
	}

	public double getMaintenanceCost() {
		return this.maintenanceCost;
	}

	public void setDangerPerc(double dangerPerc) {
		this.dangerPerc = dangerPerc;
	}

	public double getDangerPerc() {
		return this.dangerPerc;
	}

	public boolean isTakenCareOf() {
		return this.takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}

	// Twist 1
	public double getPredisposition() {
		return 0.0;
	}

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		AnimalRepository.createNode(eventWriter, "nrOfLegs", String.valueOf(this.getNrOfLegs()));
		AnimalRepository.createNode(eventWriter, "name", String.valueOf(this.getName()));
		AnimalRepository.createNode(eventWriter, "maintenanceCost", String.valueOf(this.getMaintenanceCost()));
		AnimalRepository.createNode(eventWriter, "dangerPerc", String.valueOf(this.getDangerPerc()));
		AnimalRepository.createNode(eventWriter, "takenCareOf", String.valueOf(this.isTakenCareOf()));
	}

	public void decodeFromXml(Element element) {
		this.setNrOfLegs(Integer.valueOf(element.getElementsByTagName("nrOfLegs").item(0).getTextContent()));
		this.setName(element.getElementsByTagName("name").item(0).getTextContent());
		this.setMaintenanceCost(
				Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
		this.setDangerPerc(Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
		this.setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("takenCareOf").item(0).getTextContent()));
	}
}
