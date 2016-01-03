package javasmmr.zoosome.models.employees;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.services.factories.Constants;
import javasmmr.zoosome.repositories.EmployeeRepository;

public class Caretaker extends Employee implements Caretaker_I {
	private double workingHours;

	public Caretaker() {
		this(0.8);
	}

	public Caretaker(double workingHours) {
		super();
		this.setWorkingHours(workingHours);
	}

	@Override
	public String takeCareOf(Animal animal) {
		if (animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		} else if (this.getWorkingHours() < animal.getMaintenanceCost()) {
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		} else {
			animal.setTakenCareOf(true);
			this.setWorkingHours(this.workingHours - animal.getMaintenanceCost());
			return Constants.Employees.Caretakers.TCO_SUCCESS;
		}
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	public double getWorkingHours() {
		return this.workingHours;
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		EmployeeRepository.createNode(eventWriter, "workingHours", String.valueOf(this.getWorkingHours()));
		EmployeeRepository.createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Employees.Caretaker);
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setWorkingHours(Double.valueOf(element.getElementsByTagName("workingHours").item(0).getTextContent()));
	}
}
