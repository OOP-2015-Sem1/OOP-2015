package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.employees.Employee;

//Similar to what SpeciesFactory does.
public abstract class EmployeeFactory {
	public abstract Employee getEmployee(String type) throws Exception;
}
