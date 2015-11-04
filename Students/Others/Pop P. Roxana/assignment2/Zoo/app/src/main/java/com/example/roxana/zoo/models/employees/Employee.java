package com.example.roxana.zoo.models.employees;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Roxana on 10/25/2015.
 */
public abstract class Employee{

    String name;
    Long id;
    BigDecimal salary;
    Boolean isDead = true;

    public Employee(){
    }

    public Employee(String name, BigDecimal salary, boolean isDead){

        setName(name);
        setId();
        setSalary(salary);
        setIsDead(isDead);

    }

    public void setName(String name){
        this.name = new String(name);
    }

    public String getName(){
        return name;
    }

    public Long getId(){
        return id;
    }
    public void setId(){

        Random generator = new Random();
        String nr = String.format("%d", generator.nextInt(2) + 1 );;
        for (int i=0;i<12;i++){
            nr =nr + String.format("%d", generator.nextInt(10));
        }
        id = Long.parseLong(nr);
    }

    public void setId(Long id){

        this.id = id;
    }

    public BigDecimal getSalary(){
        return salary;
    }

    public void setSalary(BigDecimal salary){

        this.salary = salary;
    }

    public boolean getIsDead(){
        return isDead;
    }

    public void setIsDead(boolean isDead){

        this.isDead = isDead;
    }

    public abstract void printAllAttributes();
}
