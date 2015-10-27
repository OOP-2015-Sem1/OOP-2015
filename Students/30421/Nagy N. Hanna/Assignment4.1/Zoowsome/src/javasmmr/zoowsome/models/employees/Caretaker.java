package javasmmr.zoowsome.models.employees;

import javasmmr.zoowsome.models.Caretaker_I;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.services.factoryForAnimals.Constants;

public class Caretaker extends Employee implements Caretaker_I {

	private Double workingHours;

	public void setWorkingHours(Double workingHours) {
		this.workingHours = workingHours;
	}

	public Double getWorkingHours() {
		return workingHours;
	}

	@Override
	public String takeCareof(Animal animal) {

		if (animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		}
		if (this.workingHours < animal.getMaintenanceCost()) {
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		}
		// Set the animal takenCareOf flag to true

		animal.setTakenCareOf(true);
		// Subtract the maintenance cost from the caretakers working hours
		this.workingHours = this.workingHours - animal.getMaintenanceCost();
		return Constants.Employees.Caretakers.TCO_SUCCESS;
	
	}

}
