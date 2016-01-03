package javasmmr.zoowsome.models.employees;

import java.math.BigDecimal;

public abstract class Employee {
	
	private String name;
	private Long id;
	private BigDecimal salary;
	private Boolean isalive=true;
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setID(Long id){
		this.id=id;
	}
	
	public void setSalary(BigDecimal salary){
		this.salary=salary;
	}

	public String getName(){
		return name;
	}
	public BigDecimal getSalary(){
		return salary;
	}
	public Long getId(){
		return id;
	}
	public Boolean getIsAlive(){
		return isalive;
	}
	
	public void setIsAlive(Boolean isAlive){
		this.isalive=isAlive;
	}

}
