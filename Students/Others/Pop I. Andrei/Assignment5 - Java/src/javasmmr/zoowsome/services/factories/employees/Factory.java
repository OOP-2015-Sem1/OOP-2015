package javasmmr.zoowsome.services.factories.employees;

import javasmmr.zoowsome.models.employees.CareTaker;
//import javasmmr.zoowsome.models.employees.Employee;

public abstract class Factory extends CareTaker {
	
	public abstract CareTaker getCareTaker(String type) throws Exception;
	
}
