package com.gellert.zoowsome.repositories;

import org.w3c.dom.Element;

import com.gellert.zoowsome.models.employees.Caretaker;
import com.gellert.zoowsome.models.employees.Employee;
import com.gellert.zoowsome.services.factories.Constants;

public class EmployeeRepository extends EntityRepository<Employee> {
	private static final String XML_FILENAME = "Employees.xml";

	public EmployeeRepository() {
		super(XML_FILENAME, Constants.XML_TAGS.EMPLOYEE);
	}

	@Override
	protected Employee getEntityFromXmlElement(Element element) {
		String discriminant = element.getElementsByTagName(Constants.XML_TAGS.DISCRIMINANT).item(0).getTextContent();
		switch (discriminant) {
		case Constants.Employees.Caretaker:
			Employee caretaker = new Caretaker();
			caretaker.decodeFromXml(element);
			return caretaker;
		default:
			break;
		}
		return null;
	}
}
