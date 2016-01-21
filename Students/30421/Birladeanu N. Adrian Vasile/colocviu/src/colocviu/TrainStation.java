package colocviu;

import java.util.ArrayList;
import java.util.Random;

public class TrainStation{

	private ArrayList<Train> trains;
	public static final int COMPARE_BY_NAME=0;
	public static final int COMPARE_BY_PROFIT=1;
	public static int compare;
	private static int maxNumberOfTrains=6;
	
	public TrainStation(){
		trains=new ArrayList<Train>();
		Random random=new Random();
		int numberOfTrains=random.nextInt(maxNumberOfTrains)+1;
		for(int i=0; i<numberOfTrains; i++){
			Train train=new Train();
			trains.add(train);
		}
		orderByProfit();
		System.out.println();
		orderByName();
	}
	
	public void displayTrain(Train train){
		System.out.println(train.getName()+" "+train.getNumberOfWagons()+" "+train.getProfit());
	}

	public void orderByName(){
		System.out.println("Display by name");
		Object[] trainArray2=trains.toArray();
		int size=trains.size();
		Train[] trainArray=new Train[size];
		for(int i=0; i<size; i++){
			trainArray[i]=(Train) trainArray2[i];
		}
		compare=COMPARE_BY_NAME;
		boolean toCompare=true;
		while(toCompare==true){
			toCompare=false;
			for(int i=0; i<size-1; i++){
				int compare=trainArray[i].compareTo(trainArray[i+1]);
				if(compare==0){
					Train aux=trainArray[i];
					trainArray[i]=trainArray[i+1];
					trainArray[i+1]=aux;
					toCompare=true;
				}
			}
		}
		for(int i=0; i<size; i++){
				displayTrain(trainArray[i]);
			}
	}
	
	public void orderByProfit(){
		System.out.println("Display by profit");
		Object[] trainArray2=trains.toArray();
		int size=trains.size();
		Train[] trainArray=new Train[size];
		for(int i=0; i<size; i++){
			trainArray[i]=(Train) trainArray2[i];
		}
		compare=COMPARE_BY_PROFIT;
		boolean toCompare=true;
		while(toCompare==true){
			toCompare=false;
		for(int i=0; i<size-1; i++){
			int compare=trainArray[i].compareTo(trainArray[i+1]);
			if(compare==-1){
				Train aux=trainArray[i];
				trainArray[i]=trainArray[i+1];
				trainArray[i+1]=aux;
				toCompare=true;
			}
		}
		}
		for(int i=0; i<size; i++){
			displayTrain(trainArray[i]);
		}
	}
	
	
}
