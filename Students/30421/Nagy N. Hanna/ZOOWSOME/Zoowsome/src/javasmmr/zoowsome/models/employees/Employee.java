package javasmmr.zoowsome.models.employees;



import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;

import org.w3c.dom.Element;

import javasmmr.zoowsome.models.interfaces.XML_Parsable;
import javasmmr.zoowsome.repositories.EmployeeRepository;

public abstract class Employee extends EmployeeRepository implements XML_Parsable{
	
	private String name;
	private Long id;
	private Long salary;
	private Boolean isalive=true;
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setID(Long id){
		this.id=id;
	}
	
	public void setSalary(Long salary){
		this.salary=salary;
	}

	public String getName(){
		return name;
	}
	public Long getSalary(){
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
	
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "id", String.valueOf(this.id));
		createNode(eventWriter, "salary", String.valueOf(this.salary));
		createNode(eventWriter, "isAlive", String.valueOf(this.isalive));
		
		}
	
	public void decodeFromXml(Element element) {
		setName(element.getElementsByTagName("name").item(0).getTextContent());
		setID(Long.valueOf(element.getElementsByTagName("id").item(0).getTextContent()));
		setSalary(Long.valueOf(element.getElementsByTagName("salary").item(0).getTextContent()));
		setIsAlive(Boolean.valueOf(element.getElementsByTagName("isAlive").item(0).getTextContent()));
		}

}
