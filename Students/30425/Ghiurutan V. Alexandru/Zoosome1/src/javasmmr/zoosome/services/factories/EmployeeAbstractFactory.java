package javasmmr.zoosome.services.factories;

public class EmployeeAbstractFactory {

	public EmployeeFactory getEmployeeFactory(String type) throws Exception {
		if (Constants.Employees.Caretaker.equals(type)) {
			return new CaretakerFactory();
		} else {
			throw new Exception("Invalid Employee name.");
		}
	}
}
