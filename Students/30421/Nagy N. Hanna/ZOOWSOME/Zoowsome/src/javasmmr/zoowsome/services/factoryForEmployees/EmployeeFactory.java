package javasmmr.zoowsome.services.factoryForEmployees;

import javasmmr.zoowsome.services.factoryForAnimals.Constants;

//import javasmmr.zoowsome.models.employees.Caretaker;
//import javasmmr.zoowsome.models.employees.Employee;


public class EmployeeFactory{
	
public EmployeeAbstractFactory getEmployeeSpeciesFactory(String type) {
		
		if (Constants.Employees.Caretaker.equals(type)) {
			return new CaretakerFactory();
		}
		else return null;

	
	
}
}
