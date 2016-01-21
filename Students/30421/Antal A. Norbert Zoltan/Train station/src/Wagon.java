import java.util.List;
import java.util.ArrayList;


public class Wagon <T> {
	private final String UUID;
	protected List<Carriable> carry = new ArrayList<Carriable>();
	private final int maxPass;
	
	public Wagon(String UUID, int maxPass){
		this.UUID = UUID;
		this.maxPass = maxPass;
	}
	
	public int calcProfit(){
		return 0;
	}
}
