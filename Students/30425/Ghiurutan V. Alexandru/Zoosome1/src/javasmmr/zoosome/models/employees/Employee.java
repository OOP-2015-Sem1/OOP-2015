package javasmmr.zoosome.models.employees;

import java.math.BigDecimal;

public abstract class Employee {
	private String name;
	private long id;// has to be unique
	private BigDecimal salary;
	private boolean isDead;

	// We will use basically a single constructor for initialization.
	public Employee() {
		this(uniqueId());
	}

	public Employee(long id) {
		this("Employee " + id, id, new BigDecimal("1000"));
	}

	public Employee(String name, long id, BigDecimal salary) {
		this.setName(name);
		this.setId(id);
		this.setSalary(salary);
		this.setIsDead(isDead);
	}

	public String getName() {
		return this.name;
	}

	public long getId() {
		return this.id;
	}

	public BigDecimal getSalary() {
		return this.salary;
	}

	public boolean isDead() {
		return this.isDead;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSalary(BigDecimal salary) {
		this.salary = salary;
	}

	public void setIsDead(boolean isDead) {
		this.isDead = isDead;
	}

	/*
	 * Method used for generating unique id's.
	 */
	private static long uniqueId() {
		return (long) (Math.random() * 1000000000000L);
	}
}
