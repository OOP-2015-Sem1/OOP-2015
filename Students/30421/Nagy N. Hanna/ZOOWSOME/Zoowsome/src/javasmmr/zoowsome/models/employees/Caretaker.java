package javasmmr.zoowsome.models.employees;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.interfaces.Caretaker_I;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class Caretaker extends Employee implements Caretaker_I {

	private Integer workingHours;

	public void setWorkingHours(Integer hours) {
		this.workingHours = hours;
	}

	public Integer getWorkingHours() {
		return workingHours;
	}

	@Override
	public String takeCareof(Animal animal) {

		if (animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		}
		if (this.workingHours < animal.getMaintenanceCost()) {
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		}
		// Set the animal takenCareOf flag to true

		else animal.setTakenCareOf(true);
		// Subtract the maintenance cost from the caretakers working hours
		this.workingHours = (int) (this.workingHours - animal.getMaintenanceCost());
		return Constants.Employees.Caretakers.TCO_SUCCESS;
	}
	
	
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "workingHours", String.valueOf(getWorkingHours()));
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT,
				Constants.Employees.Caretaker);
	}
	
	public void decodeFromXml(Element element) {
		setWorkingHours(Integer.valueOf(element.getElementsByTagName("workingHours").item(0).getTextContent()));
		}
	
	}


