package pack;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.text.html.HTMLDocument.Iterator;

public class MainController {

	
	public void run(){
		TrainStation station=new TrainStation();
		HashSet<Train> trains= new HashSet<Train>();
		
		trains.add(new Train("trainB"));
		trains.add(new Train("trainA"));
		trains.add(new Train("trainC"));
		
		ArrayList<Train> trainArray= new ArrayList<Train>();
		trainArray.addAll(trains);
		
		System.out.println("before sorting ");
		for(Train tr: trainArray){
			System.out.println(tr.getName());
		}
		
		trainArray.sort(new SortByName());
		System.out.println("after sorting ");
		for(Train tr: trainArray){
			System.out.println(tr.getName());
		}
		
		station.trains.addAll(trains);
		
		java.util.Iterator<Train> it= trains.iterator();
		while(it.hasNext()){
			Train train= (Train) it.next();
			
			train.getWagons(generateWagons("passenger",4));
			
			
		}
		
	}
	
	public LinkedList<Wagon> generateWagons(String type, int nr){
		LinkedList<Wagon> wagons = new LinkedList<Wagon>();
		if(type.equals("cargo")){
			for(int i=0; i<nr; i++){
				
				int randomProfit= (int) (Math.random()*10);
				wagons.add(new Cargo(new CargoItem("CargoItem1",randomProfit )));
			}
		}
		else{
				for(int i=0; i<nr; i++){
					int randomN= (int) (Math.random()*10);
					wagons.add(new Passenger(randomN+"wagon"));
			}
		}
		return wagons;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MainController().run();
		
		
		
		
	}

}
