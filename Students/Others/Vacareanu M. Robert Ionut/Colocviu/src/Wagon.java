import java.util.LinkedList;
import java.util.UUID;

public class Wagon <T extends Carriable> {

	final String ID = UUID.randomUUID().toString();
	private LinkedList<T> carriable;
	int wagonLimit = Integer.MAX_VALUE; // set when T is passenger
	
	Wagon() {
		carriable = new LinkedList<>();
	}
	
	public int getProfit(){
		if(carriable.size()>0){
		 return carriable.size() * carriable.get(0).getProfit();
		} else return 0;
	}
	
	public void setLimit(int wagonLimit){
		this.wagonLimit = wagonLimit;
	}
	
	public LinkedList<T> getCarriable(){
		return this.carriable;
	}
	
	public void addCarriable(T carriable) {
		if(this.carriable.size() < wagonLimit){
			this.carriable.add(carriable);
		}
		
	}
	
}
