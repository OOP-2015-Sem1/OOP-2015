package com.gellert.zoowsome.services.factories;

import com.gellert.zoowsome.models.employees.Employee;

public abstract class EmployeeFactory {
	public abstract Employee getEmployee(String type) throws Exception;
}
