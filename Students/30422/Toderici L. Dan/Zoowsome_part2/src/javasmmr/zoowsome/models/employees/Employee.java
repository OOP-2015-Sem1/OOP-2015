package javasmmr.zoowsome.models.employees;
import java.math.BigDecimal;
import java.util.Random;

public abstract class Employee 
{
	private String name;
	private long id;
	private BigDecimal salary;
	private Boolean isDead;
	
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
	
	public Boolean getIsDead() {
		return isDead;
	}
	public void setIsDead(Boolean isDead) {
		this.isDead = isDead;
	}
	
	public Employee(String name, long id, BigDecimal salary)
	{
		this.name = name;
		this.id = id;
		this.salary = salary;
		this.isDead = false;
	}
	
	public Employee()
	{
		Random randomNumber = new Random();
		
		this.setId((long) randomNumber.nextInt(100000)*1);
		this.setName("EmployeeNb "+id);
		
		BigDecimal salary = BigDecimal.valueOf((long) randomNumber.nextInt(10000)*500);
		this.setSalary(salary);
		this.setIsDead(false);
		
	}

}
