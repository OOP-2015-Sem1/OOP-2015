package javasmmr.zoowsome.models.employees;

import java.math.*;
import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.*;

public class Caretaker extends Employee implements Caretaker_I {
	private double workingHours;

	public void setWorkingHours(double hours) {
		this.workingHours = hours;
	}

	public double getWorkingHours() {
		return workingHours;
	}

	public Caretaker(String name, Long id, BigDecimal salary, double workingHours) {
		super(name, id, salary);
		this.workingHours = workingHours;
	}

	public String takenCareOf(Animal a) {
		if (a.kill()) {
			return Constants.Employees.Caretakers.TCO_KILLED;
		}
		if (this.workingHours < a.getMaintenanceCost()) {
			return Constants.Employees.Caretakers.TCO_NO_TIME;
		}
		a.setTakenCareOf(true);
		setWorkingHours(workingHours - a.getMaintenanceCost());
		return Constants.Employees.Caretakers.TCO_SUCCESS;
	}

}