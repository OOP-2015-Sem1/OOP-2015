package services.factories.employees;


import models.employees.CareTaker;
import models.employees.Employee;
import services.factories.Constants;

public class CareTakerFactory extends EmployeeAbstractFactory {
	

	@Override
	public Employee getEmployeeFactory(String type) throws Exception {
		if (Constants.Employees.Caretaker.equals(type)) {
			return new CareTaker();
		} else {
			System.out.println("Invalid employee exception");
			return null;
		}
	}

}
