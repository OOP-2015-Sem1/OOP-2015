package model.carriable;

public class Passenger implements Carriable {
	private final String name;

	public Passenger(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public boolean equals(Object o) {
		Passenger other = (Passenger) o;
		return name.equals(other.getName());
	}

}
