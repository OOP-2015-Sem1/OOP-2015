package services.factories.employees;

import models.employees.Caretaker;
import services.factories.Constants;

public class CaretakerFactory extends EmployeeAbstractFactory {

	@Override
	public Caretaker getEmployee(String type) throws Exception {
		if (Constants.Employees.Caretaker.equals(type)) {
			return new Caretaker();
		} else {
			throw new Exception("Invalid type!");
		}
	}
}