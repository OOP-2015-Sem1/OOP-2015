package javasmmr.zoowsome.services.factoryForEmployees;

import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.models.employees.Employee;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class CaretakerFactory extends EmployeeAbstractFactory{
	
	public Employee getEmployee(String type) {

		if (Constants.Employees.Caretaker.equals(type)) {
			return new Caretaker();
		}
		else return null;
	}
}
