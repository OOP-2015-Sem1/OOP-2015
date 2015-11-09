package javasmmr.zoowsome.models.employees;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.constants.Constants;

public class CareTaker extends Employee
                       implements CareTaker_1{
	
	private double workingHours;
	
	public CareTaker() {
		super();
		this.workingHours = 3.4;
	}

	public double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	@Override
	public String takeCareOf(Animal animal) {
		
		if(animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		}
		else if(animal.getMaintenanceCost() > this.workingHours)
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		else {
			animal.setTakenCareOf(true);
			this.workingHours -= animal.getMaintenanceCost();
			return Constants.Employees.Caretakers.TCO_SUCCESS;
		}
		
	}

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "workingHours", String.valueOf(this.workingHours));
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Employees.CareTakers);
	}

	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		setWorkingHours(Double.valueOf(element.getElementsByTagName("workingHours").item(0).getTextContent()));
	}
}
