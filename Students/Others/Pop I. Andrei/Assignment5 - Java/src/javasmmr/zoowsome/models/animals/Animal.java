package javasmmr.zoowsome.models.animals;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.interfaces.XML_Parsable;

public abstract class Animal implements Killer, XML_Parsable{
	private int nrOfLegs;
	private String name;
	private double maintenanceCost;
	private double damagePerc;
	private boolean takenCareOf = false;

	public Animal(){
		this(0.1, 0);
	}
	
	public Animal(double maintenance, double damage) {
		if(maintenance < 0.1)
			maintenanceCost = 0.1;
		else if(maintenance > 8.0)
			maintenanceCost = 8.0;
		else
			maintenanceCost = maintenance;
		
		if(damage < 0)
			damagePerc = 0;
		else if(damage > 1)
			damagePerc = 1;
		else
			damagePerc = damage;
	}	
	
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		createNode(eventWriter, "nrOfLegs", String.valueOf(this.nrOfLegs));
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "maintenanceCost", String.valueOf(this.maintenanceCost));
		createNode(eventWriter, "dangerPerc", String.valueOf(this.damagePerc));
		createNode(eventWriter, "takenCareOf", String.valueOf(this.takenCareOf));
		}
	
	
	public void decodeFromXml(Element element) {
		setNrOfLegs (Integer.valueOf(element.getElementsByTagName("nrOfLegs").item(0).getTextContent()));
		setName(element.getElementsByTagName("name").item(0).getTextContent()) ;
		setMaintenanceCost (Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
		setDamagePerc(Double.valueOf(element.getElementsByTagName("dangerPerc").item(0).getTextContent()));
		setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("takenCareOf").item(0).getTextContent()));
	}
	
	public boolean kill() {
		double nr= Math.random();
		if(nr < damagePerc + this.getPredisposition())
			return true;
		else
			return false;
	}
	
	public double getPredisposition() {
		return 0;
	}
	
	public boolean isTakenCareOf() {
		return takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf) {
		this.takenCareOf = takenCareOf;
	}

	public int getNrOfLegs() {
		return nrOfLegs;
	}

	public void setNrOfLegs(int nrOfLegs) {
		this.nrOfLegs = nrOfLegs;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getMaintenanceCost() {
		return maintenanceCost;
	}
	
	
	public double getDamagePerc() {
		return damagePerc;
	}
	
	public void setDamagePerc(double damage) {
		damagePerc = damage;
	}
	
	public void setMaintenanceCost(double cost) {
		maintenanceCost = cost;
	}

}