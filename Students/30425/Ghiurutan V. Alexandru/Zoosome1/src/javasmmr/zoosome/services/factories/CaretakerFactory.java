package javasmmr.zoosome.services.factories;

import javasmmr.zoosome.models.employees.Caretaker;
import javasmmr.zoosome.models.employees.Employee;

public class CaretakerFactory extends EmployeeFactory {
	@Override
	public Employee getEmployee(String type) throws Exception {
		if (Constants.Employees.Caretaker.equals(type)) {
			return new Caretaker();
		} else {
			throw new Exception("Invalid employee name.");
		}

	}
}
