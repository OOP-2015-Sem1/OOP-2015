package javasmmr.zoowsome.models.animals;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.interfaces.Killer;
import javasmmr.zoowsome.models.interfaces.XML_Parsable;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

public class Animal implements Killer,XML_Parsable{
	private int nrOfLegs;
	private String name;
	private double maintenanceCost;
	private double dangerPerc;
	private boolean takenCareOf;

	public Animal(double maintenanceCost, double dangerPerc)
	{
		this.maintenanceCost = maintenanceCost;
		this.dangerPerc = dangerPerc;
		setTakenCareOf(false);
	}

	public void setNrOfLegs(int nrLegs)
	{
		this.nrOfLegs=nrLegs;
	}

	public int getNrOfLegs()
	{
		return nrOfLegs;
	}

	public void setName(String s)
	{
		this.name=s ;
	}
	public String getName()
	{
		return name;
	}

	public double getMaintenanceCost()
	{
		return this.maintenanceCost;
	}
	public void setMaintenanceCost(double maintenanceCost) 
	{
		this.maintenanceCost = maintenanceCost;
	}

	public double getDangerPerc()
	{
		return this.dangerPerc;
	}
	public void setDangerPerc(double dangerPerc) 
	{
		this.dangerPerc = dangerPerc;
	}

	public boolean kill()  
	{

		return(Math.random() < this.dangerPerc + getPredisposition());

	}

	public double getPredisposition()  
	{
		return 0;
	}

	public boolean getTakenCareOf() 
	{
		return takenCareOf;
	}

	public void setTakenCareOf(boolean takenCareOf)
	{
		this.takenCareOf = takenCareOf;
	}

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException
	{
		createNode(eventWriter, "nrOfLegs", String.valueOf(this.nrOfLegs));
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "maintenanceCost", String.valueOf(this.maintenanceCost));
		createNode(eventWriter, "dangerPerc", String.valueOf(this.dangerPerc));
		createNode(eventWriter, "takenCareOf", String.valueOf(this.takenCareOf));

	}

	public void decodeFromXml(Element element) 
	{
		setNrOfLegs(Integer.valueOf(element.getElementsByTagName("nrOfLegs").item(0).getTextContent()));;
		setName(element.getElementsByTagName("name").item(0).getTextContent());
		setMaintenanceCost(Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
	    setDangerPerc(Double.valueOf(element.getElementsByTagName("dangerPerc").item(0).getTextContent()));
	    setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("takenCareOf").item(0).getTextContent()));
	}



}