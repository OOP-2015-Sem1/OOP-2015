package ship;

public class Passenger implements Carriable {
	private String name;

	public void setName(String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}
}
