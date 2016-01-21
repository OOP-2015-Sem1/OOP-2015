package colocviu;

import java.util.LinkedList;
import java.util.Random;

import Wagons.*;

public class Train implements Comparable<Train> {

	public static final int PASSENGER=0;
	public static final int CARGO=1;
	private static final int MAX_WAGONS=100;
	private String name;
	private int type;
	private LinkedList<Wagon> wagons;
	private int profit;
	
	public Train(){
		wagons=new LinkedList<Wagon>();
		Random random=new Random();
		type=random.nextInt(2);
		int numberOfWagons=random.nextInt(MAX_WAGONS)+1;
		for(int i=0; i<numberOfWagons; i++){
			if(type==PASSENGER){
				Wagon wagon=new PassengerWagon(); 
				wagons.add(wagon);
			}
			else {
				Wagon wagon=new CargoWagon();
				wagons.add(wagon);
			}
		}
		int nameType=random.nextInt(2);
		int number=1000+random.nextInt(9000);
		if(nameType==0){
			name=new String("IR"+number);
		}
		else {
			name=new String("IC"+number);
		}
		this.profit=this.setProfit();
	}
	
	public String getName(){
		return this.name;
	}
	
	public int setProfit(){
		int numberOfWagons=wagons.size();
		int profit=0;
		for(int i=0; i<numberOfWagons; i++){
			Wagon newWagon=wagons.get(i);
			profit+=newWagon.getTotalProfit();
			
		}
		return profit;
	}
	
	public int getProfit(){
		return this.profit;
	}
	
	public int getNumberOfWagons(){
		return wagons.size();
	}

	@Override
	public int compareTo(Train arg0) {
		if(TrainStation.compare==TrainStation.COMPARE_BY_NAME){
			return this.name.compareTo(arg0.name);
		}
		else {
			if(this.profit>arg0.profit){
				return 1;
			}
			else return -1;
		}
	}

	
}
