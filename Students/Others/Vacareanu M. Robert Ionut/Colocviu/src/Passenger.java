import java.util.Random;

public class Passenger implements Carriable {
	private final String name;
	
	Passenger() {
		Random random = new Random();
		name = "Passenger" + random.nextInt(10000);
	}
	
	Passenger(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getProfit() {
		return TICKET_PRICE;
	}
	

}
