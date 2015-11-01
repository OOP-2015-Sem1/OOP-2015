package javasmmr.zoosome.models.employees;

import javasmmr.zoosome.models.animals.Animal;
import javasmmr.zoosome.services.factories.Constants;

public class Caretaker extends Employee implements Caretaker_I {
	private double workingHours;

	public Caretaker() {
		this(0.8);
	}

	public Caretaker(double workingHours) {
		super();
		this.setWorkingHours(workingHours);
	}

	@Override
	public String takeCareOf(Animal animal) {
		if (animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		} else if (this.getWorkingHours() < animal.getMaintenanceCost()) {
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		} else {
			animal.setTakenCareOf(true);
			this.setWorkingHours(this.workingHours - animal.getMaintenanceCost());
			return Constants.Employees.Caretakers.TCO_SUCCESS;
		}
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	public double getWorkingHours() {
		return this.workingHours;
	}

}
