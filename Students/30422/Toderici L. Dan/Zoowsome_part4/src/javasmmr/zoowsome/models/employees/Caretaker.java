package javasmmr.zoowsome.models.employees;

import java.math.BigDecimal;
import java.util.Random;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.services.factories.Constants;
import static javasmmr.zoowsome.repositories.EmployeeRepository.createNode;

public class Caretaker extends Employee implements Caretaker_I {

	private double workingHours;

	public Caretaker(String name, long id, BigDecimal salary) {
		super(name, id, salary);	
	}

	public Caretaker()
	{
		super();
		//System.out.println("New employee "+this.getId());
		Random randomNumber = new Random();
		this.setWorkingHours(randomNumber.nextInt(56)*10);

	}
	public String takeCareOf(Animal animal) {

		if(animal.kill())
		{
			return Constants.Employee.Caretakers.TCO_KILLED;
		}
		if(this.workingHours < animal.getMaintenanceCost())
		{
			return Constants.Employee.Caretakers.TCO_NO_TIME;
		}

		animal.setTakenCareOf(true);
		this.setWorkingHours(this.getWorkingHours() - animal.getMaintenanceCost());
		return Constants.Employee.Caretakers.TCO_SUCCESS;
	}


	public double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException 
	{
		super.encodeToXml(eventWriter);
		createNode(eventWriter, "workingHours", String.valueOf(getWorkingHours()));
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Employee.Caretaker);
	}
	public void decodeFromXml(Element element) 
	{
		super.decodeFromXml(element);
		setWorkingHours(Double.valueOf(element.getElementsByTagName("workingHours").item(0).getTextContent()));
	}

}
