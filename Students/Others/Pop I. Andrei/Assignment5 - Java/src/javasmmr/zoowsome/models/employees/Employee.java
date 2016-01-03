package javasmmr.zoowsome.models.employees;

import static javasmmr.zoowsome.repositories.AnimalRepository.createNode;

import java.math.BigDecimal;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.interfaces.XML_Parsable;

public abstract class Employee implements XML_Parsable{

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
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "id", String.valueOf(this.id));
		createNode(eventWriter, "salary", String.valueOf(this.salary));
		createNode(eventWriter, "isDead", String.valueOf(this.isDead));
		}
	
	public void decodeFromXml(Element element) {
		setName(element.getElementsByTagName("name").item(0).getTextContent()) ;
		setId(Long.valueOf(element.getElementsByTagName("id").item(0).getTextContent()));
		//setSalary(BigDecimal.valueOf((double)(element.getElementsByTagName("salary").item(0).getTextContent())));
		setDead(Boolean.valueOf(element.getElementsByTagName("isDead").item(0).getTextContent()));
		
	}

}
