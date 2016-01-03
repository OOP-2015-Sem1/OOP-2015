package com.example.alexh.zoosome.services.factories.employees;

import com.example.alexh.zoosome.models.employees.Employee;

public abstract class EmployeeFactory {
    public abstract Employee getEmployee(String type) throws Exception;

    public abstract Employee getRandomEmployeeOfType(String type) throws Exception;
}
