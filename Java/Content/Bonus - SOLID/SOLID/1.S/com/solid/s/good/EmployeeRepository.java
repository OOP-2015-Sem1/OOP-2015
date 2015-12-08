package com.solid.s.good;

public class EmployeeRepository {

	public void save(GoodEmployee employee) {
		System.out.printf("%s saved to database.\n", employee.name);
	}

}
