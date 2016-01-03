package com.solid.s.good;

public class EmployeeTimetrackingService {

	public String reportHours(GoodEmployee employee) {
		return String.format("%s worked %d hours.\n", employee.name,
				employee.hours);
	}

}
