package javasmmr.zoosome.models.employees;

import javasmmr.zoosome.models.interfaces.XML_Parsable;
import javasmmr.zoosome.repositories.EmployeeRepository;

import java.math.BigDecimal;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

public abstract class Employee implements XML_Parsable {
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

	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		EmployeeRepository.createNode(eventWriter, "name", String.valueOf(this.getName()));
		EmployeeRepository.createNode(eventWriter, "id", String.valueOf(this.getId()));
		EmployeeRepository.createNode(eventWriter, "salary", String.valueOf(this.getSalary()));
		EmployeeRepository.createNode(eventWriter, "isDead", String.valueOf(this.isDead()));

	}

	public void decodeFromXml(Element element) {
		this.setName(String.valueOf(element.getElementsByTagName("name").item(0).getTextContent()));
		this.setId(Long.valueOf(element.getElementsByTagName("id").item(0).getTextContent()));
		this.setSalary(
				BigDecimal.valueOf(Double.valueOf(element.getElementsByTagName("salary").item(0).getTextContent())));
		this.setIsDead(Boolean.valueOf(element.getElementsByTagName("isDead").item(0).getTextContent()));

	}
}
