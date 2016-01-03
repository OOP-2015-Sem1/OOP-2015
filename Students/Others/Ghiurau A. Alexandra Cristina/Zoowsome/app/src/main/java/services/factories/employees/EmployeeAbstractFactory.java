package services.factories.employees;


import models.employees.Employee;

public abstract class EmployeeAbstractFactory {
	
	public abstract Employee getEmployeeFactory(String type) throws Exception;
}
