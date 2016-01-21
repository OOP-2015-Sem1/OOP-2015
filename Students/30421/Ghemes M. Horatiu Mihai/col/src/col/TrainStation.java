package col;

import java.util.ArrayList;
import java.util.List;

public class TrainStation {

	public int nrOfTrains=0;
	public static List<Train> station = new ArrayList<Train>();
	public static void main(String[] args) {
		//create 5 passangers
		Passanger p1=new Passanger("Ionut");
		Passanger p2=new Passanger("Ionut");
		Passanger p3=new Passanger("Ionut");
		Passanger p4=new Passanger("Ionut");
		Passanger p5=new Passanger("Ionut");
		//create 3 wagons
		Wagon w1=new Wagon(10);  //here should be the UUID thingie
		Wagon w2=new Wagon(15);
		Wagon w3=new Wagon(2);
		
		//fill them
		w1.addPassangers(p1);
		w2.addPassangers(p2);
		w3.addPassangers(p3);
		w3.addPassangers(p4);
		w3.addPassangers(p5);
		
		//create a train
		Train t=new Train("Trenulet 1");
		//add the wagons
		t.addWagon(w1);
		t.addWagon(w2);
		t.addWagon(w2); //as per example, this addition will not count because Wagon w2 has already been added
		t.addWagon(w3);
		//add the train to the station
		station.add(t);
		//check on the train 
		
		checkOnTrain(t);
		

	}
	public void depart(Train t)   
	{
		t.isDeparting=true;   //train is on the move
		nrOfTrains--;
	}
	public void recieve(Train t)   
	{
		t.isDeparting=false;            // train is in station
		nrOfTrains++;
	}

	public static void checkWheterOrNotIsInStation( Train t)
	{
		if (t.isDeparting)
			System.out.println("Train is currently not in the station");
		else 
			System.out.println("Train is currently in the station");
	}
	public static void checkOnTrain(Train t)
	{
		System.out.printf("Train name: %s \n" ,t.name);
		System.out.printf("Number of wagons: %d \n" ,t.noOfWagons);
		System.out.printf("Train's profit : %d \n" ,t.totalProfit);
	}
		
}
