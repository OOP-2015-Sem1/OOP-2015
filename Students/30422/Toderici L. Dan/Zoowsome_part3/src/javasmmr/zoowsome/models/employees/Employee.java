package javasmmr.zoowsome.models.employees;
import java.math.BigDecimal;
import java.util.Random;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import static javasmmr.zoowsome.repositories.EmployeeRepository.createNode;

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
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException 
	{
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "id", String.valueOf(this.id));
		createNode(eventWriter, "salary", String.valueOf(this.salary));
		createNode(eventWriter, "isDead", String.valueOf(this.isDead));
	}
	public void decodeFromXml(Element element) 
	{
		setName(element.getElementsByTagName("name").item(0).getTextContent());
		setId(Long.valueOf(element.getElementsByTagName("id").item(0).getTextContent()));
		setSalary(BigDecimal.valueOf(Double.valueOf(element.getElementsByTagName("salary").item(0).getTextContent())));
		setIsDead(Boolean.valueOf(element.getElementsByTagName("isDead").item(0).getTextContent()));
	}
	

}
