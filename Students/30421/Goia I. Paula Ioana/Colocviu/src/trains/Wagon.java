package trains;
import java.util.ArrayList;
import java.util.UUID;

import source.CargoItem;
import source.Carriable;
import source.Passanger;

public class Wagon<T> {
	


	public final String ID;
	public ArrayList<Carriable> wagonElements;
	public final int PASSANGER_TICKET_PRICE=100;
	public final int MAX_NR_PASSANGERS=100;
	
	public Wagon(){
		this.wagonElements= new ArrayList<Carriable>();
		this.ID=UUID.randomUUID().toString();
	}
	
	
	public int computeProfitForPassangerWagon(){
		
		int numberOfPassangers=this.wagonElements.size();
		int profit=numberOfPassangers*this.PASSANGER_TICKET_PRICE;
		return profit;
	}

	public void addPasangers(){
		for(int i=0;i<100;i++)
		 this.wagonElements.add(new Passanger());
	}
	
	public void addCargo(){
		for(int i=0;i<100;i++)
		 this.wagonElements.add(new CargoItem());
	}

	
	public ArrayList<Carriable> getWagonElements() {
		return wagonElements;
	}

	public void setWagonElements(ArrayList<Carriable> wagonElements) {
		this.wagonElements = wagonElements;
	}
	
	
	

	
	
}
