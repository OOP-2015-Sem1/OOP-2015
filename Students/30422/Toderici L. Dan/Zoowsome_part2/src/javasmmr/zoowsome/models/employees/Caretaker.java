package javasmmr.zoowsome.models.employees;

import java.math.BigDecimal;
import java.util.Random;

import javasmmr.zoowsome.models.animals.Animal;
import javasmmr.zoowsome.services.factories.Constants;


public class Caretaker extends Employee implements Caretaker_I {

	private double workingHours;

	public Caretaker(String name, long id, BigDecimal salary) {
		super(name, id, salary);	
	}

	public Caretaker()
	{
		super();
		//System.out.println("New employee "+this.getId());
		Random randomNumber = new Random();
		this.setWorkingHours(randomNumber.nextInt(56)*10);

	}
	public String takeCareOf(Animal animal) {

		if(animal.kill())
		{
			return Constants.Employee.Caretakers.TCO_KILLED;
		}
		if(this.workingHours < animal.getMaintenanceCost())
		{
			return Constants.Employee.Caretakers.TCO_NO_TIME;
		}

		animal.setTakenCareOf(true);
		this.setWorkingHours(this.getWorkingHours() - animal.getMaintenanceCost());
		return Constants.Employee.Caretakers.TCO_SUCCESS;
	}


	public double getWorkingHours() {
		return workingHours;
	}

	public void setWorkingHours(double workingHours) {
		this.workingHours = workingHours;
	}

}
