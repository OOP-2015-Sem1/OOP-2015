package train;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	Object MyTrainStation;
	TrainStation MyTrainStation=new TrainStation();
	Object MyTrain;

	public void SortByName() {
	}

	public void SortByProfit() {
	}

	public void TrainDetails(String trainname) {

		if (MyTrainStation.Train.Name == trainname)
			MyTrain = MyTrainStation.Train;
		System.out.println(Train.Name);
		System.out.println(Train.TrainProfit);
		System.out.println(Train.nrOfWagons);
	}
}
