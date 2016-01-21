package models;

public class Passenger implements Carriable{
	private String name;
public Passenger(String name) {
		setName(name);
	}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
}
