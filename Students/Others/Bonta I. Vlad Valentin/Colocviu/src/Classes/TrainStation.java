package Classes;
import java.util.ArrayList;

public class TrainStation {
	private ArrayList<Train> trains;
	public TrainStation(ArrayList<Train> trains){
		this.trains = trains;
	}
	
	public TrainStation(){
		this.trains = new ArrayList<Train>();
	}
	
	public Boolean checkTrainInStation(Train train){
		if (trains.contains(train))
			return true;
		return false;		
	}
	
	public void addNewTrain(Train train){
		if (!trains.contains(train))
			trains.add(train);
	}
	
	public void departTrain(Train train){
		trains.remove(train);
	}
	
	public void trainsSortedByName(){
		System.out.println(this.trains.size());
		for (int i = 0; i < trains.size();i++){
			String train1Name;
			String train2Name;

			for (int j = i + 1; j < trains.size();j++){
				train1Name = trains.get(i).getName();
				train2Name = trains.get(j).getName();
				if (train1Name.compareTo(train2Name) > 0){
					Train train1 = trains.get(i);
					Train train2 = trains.get(j);
					trains.remove(i);
					trains.add(i, train2);
					trains.remove(j);
					trains.add(j, train1);
				}
			}
		}
		System.out.println("Trains from train station: ");

		for (int i = 0; i < trains.size();i++){
			System.out.println(trains.get(i).getName());
		}
	}
	
	public void trainsSortedByProfit(){
		for (int i = 0; i < trains.size();i++){
			int train1Profit;
			int train2Profit;

			for (int j = i + 1; j < trains.size();j++){
				train1Profit = trains.get(i).computeTrainProfit();
				train2Profit = trains.get(j).computeTrainProfit();
				if (train1Profit > train2Profit){
					Train train1 = trains.get(i);
					Train train2 = trains.get(j);
					trains.remove(i);
					trains.add(i, train2);
					trains.remove(j);
					trains.add(j, train1);
				}
			}
		}
		System.out.println("Trains from train station: ");

		for (int i = 0; i < trains.size();i++){
			System.out.println("Name: " + trains.get(i).getName() + " Profit: " +  trains.get(i).computeTrainProfit());
		} 
	}
	
	
}
