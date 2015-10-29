package javasmmr.zoowsome.models.employees;

import java.math.BigDecimal;

public abstract class Employee {

	private String name;
	private long id;
	private BigDecimal salary= new BigDecimal(0);
	private boolean isDead = false;

	
	public Employee(String name, BigDecimal salary, boolean isDead){
		this.name = name;
		this.salary.add(salary);
		this.isDead = isDead;
		this.id = getRandomId();
	}
	
	public Employee(){
		BigDecimal temp = new BigDecimal("1234.4567");
		setName("Andrer");
		setId(getRandomId());
		setSalary(temp);
		setDead(false);
	}
	
	
	public long getRandomId() {
		return (long) (Math.random() * 1234567891234l);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public BigDecimal getSalary() {
		return salary;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

}
