package javasmmr.zoowsome.services.factories.employees;

import javasmmr.zoowsome.models.constants.Constants;

public class EmployeeFactory{
	
	public Factory getFactory(String type) throws Exception{
		
		if(Constants.Employees.CareTakers.equals(type))
			return new CareTakerFactory();
		else
			throw new Exception("Invalid employee exception");
		
		
		
	}

}
