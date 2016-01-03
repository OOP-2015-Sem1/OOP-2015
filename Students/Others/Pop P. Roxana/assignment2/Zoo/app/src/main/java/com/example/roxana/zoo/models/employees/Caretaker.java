package com.example.roxana.zoo.models.employees;

import com.example.roxana.zoo.Constants;
import com.example.roxana.zoo.models.animals.Animal;

import java.math.BigDecimal;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Caretaker extends Employee implements Caretaker_I {

    double workingHours;

    public Caretaker() {

        this.setName("caretaker");
        this.setId();
        this.setSalary(new BigDecimal(70));
        this.setIsDead(false);

    }

    public Caretaker(String name, Long id, BigDecimal salary, boolean isDead, double workingHours) {

        super(name,salary,isDead);
        this.setId(id);
        this.setWorkingHours(workingHours);

    }

    public void setWorkingHours(double workingH) {
        workingHours = workingH;
    }

    public Double getWorkingHours() {
        return workingHours;
    }

    // implementing the method from the interface
    public String takeCareOf(Animal animal) {

        if (animal.kill()) {
            return Constants.Employees.Caretakers.TCO_KILLED;
        } else if (((Double.valueOf(this.workingHours)).compareTo(animal.maintenanceCost)) < 0) {
            return Constants.Employees.Caretakers.TCO_NO_TIME;
        } else {
            animal.takenCareOf = true;
            workingHours -= animal.maintenanceCost;
            return Constants.Employees.Caretakers.TCO_SUCCESS;
        }
    }

    public void printAllAttributes() {

        System.out.println("Name: " + getName());
        System.out.println("Id: " + getId());
        System.out.println("Salary " + getSalary());
        System.out.println((getIsDead()) ? "The employee is dead" : "The employee is still alive");
        System.out.println("Working hours: " + getWorkingHours());

    }

    public String toString() {
        String s;
        s = "Name: " + getName() + "<br>Id: " + getId() + "<br>Salry " + getSalary() + "<br>"
                + ((getIsDead()) ? "The employee is dead" : "The employee is still alive") + "<br>Working hours: "
                + getWorkingHours() + "<br>";
        return s;
    }

}