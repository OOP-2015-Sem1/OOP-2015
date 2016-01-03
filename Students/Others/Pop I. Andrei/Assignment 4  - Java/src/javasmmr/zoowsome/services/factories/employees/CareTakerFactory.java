package javasmmr.zoowsome.services.factories.employees;

import javasmmr.zoowsome.models.constants.Constants;
import javasmmr.zoowsome.models.employees.CareTaker;

public class CareTakerFactory extends Factory{
	
	public CareTaker getCareTaker(String type) throws Exception{
		if(Constants.Employees.CareTakers.equals(type))
			return new CareTaker();
		else
			throw new Exception("Invalid position exception");
	}
}
