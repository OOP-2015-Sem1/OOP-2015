package Classes;

import java.util.LinkedList;

public class Train {
	private String name;
	private LinkedList<Wagon> wagons;
	
	public Train(String name, LinkedList<Wagon> wagons){
		this.name = name;

		this.wagons = wagons;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int computeTrainProfit(){
		int result = 0;

		for(int i = 0; i<wagons.size(); i++){
			result += wagons.get(i).computeProfit();
		}
		return result;
	}
	
	public void addWagon(Wagon wagon){
		this.wagons.add(wagon);
	}
		
	public void getTrainDetails(){
		System.out.println("Name: " + this.name + "\nNumber of wagons: " +
				this.wagons.size() + "\nProfit: " + this.computeTrainProfit());
	}
}
