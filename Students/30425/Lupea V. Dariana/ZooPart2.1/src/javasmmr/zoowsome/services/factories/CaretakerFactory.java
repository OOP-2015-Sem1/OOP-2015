package javasmmr.zoowsome.services.factories;

import javasmmr.zoowsome.Constants;
import javasmmr.zoowsome.DataGenerator;
import javasmmr.zoowsome.models.employees.Caretaker;
import javasmmr.zoowsome.models.employees.Employee;
import java.math.*;

public class CaretakerFactory extends EmployeeAbstractFactory {
	public Employee getEmployee(String type) throws Exception {
		if (Constants.employees.Employee.Caretaker.equals(type)) {
			DataGenerator data = new DataGenerator();
			String name = data.nameGenerator();
			Long id = data.idGen();
			BigDecimal salary = data.salaryGen();
			double workingHours = data.workingHoursGen();
			return new Caretaker(name, id, salary, workingHours);
		} else {
			throw new Exception("Invalid animal exception!");
		}
	}

}
