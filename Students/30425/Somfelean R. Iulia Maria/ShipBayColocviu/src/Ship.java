import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Ship {

	private List compartaments = new LinkedList<Compartment>();
	private String name;
	private boolean hasPassenger;
	private boolean hasCargo;
	
	public Ship(boolean isPassenger, boolean isCargo){
		
		this.name = UUID.randomUUID().toString();
		this.hasPassenger = isPassenger;
		this.hasCargo = !hasPassenger;
	}
	
	private int computeProfit(){
		int sumProfit = 0;
		Iterator<Compartment> i = null;

		
		while(i.hasNext()){
			sumProfit += i.next().getProfit();
		}
			return 0;
	}
}
