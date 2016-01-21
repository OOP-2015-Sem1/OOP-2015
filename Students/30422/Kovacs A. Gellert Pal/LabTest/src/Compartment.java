import java.util.ArrayList;
import java.util.UUID;

public class Compartment {
	private final String ID;
	ArrayList<Carriable> carry;
	
	public Compartment() {
		this.ID = UUID.randomUUID().toString();
		carry = new ArrayList<Carriable>();
	}
	
	public int getProfit() {
		return 0;
	}
	
	public String getID() {
		return this.ID;
	}
}
