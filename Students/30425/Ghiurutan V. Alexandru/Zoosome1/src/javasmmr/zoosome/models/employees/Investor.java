package javasmmr.zoosome.models.employees;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoosome.repositories.EmployeeRepository;
import javasmmr.zoosome.services.factories.Constants;

public class Investor extends Employee {
	private double workingHours;

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	public double getWorkingHours() {
		return this.workingHours;
	}

	public Investor() {
		this(0.8);
	}

	public Investor(double workingHours) {
		super();
		this.setWorkingHours(workingHours);
	}

	@Override
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		EmployeeRepository.createNode(eventWriter, "workingHours", String.valueOf(this.getWorkingHours()));
		EmployeeRepository.createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Employees.Investor);
	}

	@Override
	public void decodeFromXml(Element element) {
		super.decodeFromXml(element);
		this.setWorkingHours(Double.valueOf(element.getElementsByTagName("workingHours").item(0).getTextContent()));
	}
}
