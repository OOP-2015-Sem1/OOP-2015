import java.util.LinkedList;
import java.util.UUID;

public class Ship {
	private LinkedList<Compartment> allCompartmentsInShip =  new LinkedList<Compartment>();
	private String name = UUID.randomUUID().toString();
	private int profit;
	
	public Ship(){
	
	}
	
	private void computeProfit(){
		for(int i=0;i<=allCompartmentsInShip.size();i++){
			//*ceva
		}
	}
	
	private String getName(){
		return name;
	}
}
