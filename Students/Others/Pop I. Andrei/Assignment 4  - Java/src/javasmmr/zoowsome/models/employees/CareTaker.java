package javasmmr.zoowsome.models.employees;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.models.constants.Constants;

public class CareTaker extends Employee
                       implements CareTaker_1{
	
	private double workingHours;
	
	public CareTaker() {
		super();
		this.workingHours = 3.4;
	}

	public double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

	@Override
	public String takeCareOf(Animal animal) {
		
		if(animal.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		}
		else if(animal.getMaintenanceCost() > this.workingHours)
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		else {
			animal.setTakenCareOf(true);
			this.workingHours -= animal.getMaintenanceCost();
			return Constants.Employees.Caretakers.TCO_SUCCESS;
		}
		
	}
}
