package pack;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

public class Train implements Comparator{

	protected LinkedList<Wagon> wagons = new LinkedList<Wagon>();
	private String[] passangerOrCargo= new String[]{"passanger", "cargo"};

	private String name;

	public Train(String name){
		this.name= name;
	}
	
	public void getWagons(LinkedList<Wagon> wagons){
		this.wagons=wagons;
	}
	
	
	public String getName(){
		return this.name;
	}
	public void getWagonsName(){
		//return this.name;
		Iterator<Wagon> it= wagons.iterator();
		while(it.hasNext()){
			
			Wagon w= (Wagon) (it.next());
			//return w.getName();
		}
	}


	public void computeTotalProfit() {

	}


	
public int compare(Object arg0, Object arg1) {
		
		Train train0= (Train) arg0;
		Train train1= (Train) arg1;
		
		return train0.getName().compareTo(train1.getName());
	}

}
