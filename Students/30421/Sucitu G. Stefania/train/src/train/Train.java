package train;

import java.util.LinkedList;

public class Train {
	LinkedList Train = new LinkedList<Wagon>();
	String Name = "";
	String TrainType = "";
	int TrainProfit = 0;
	int nrOfWagons=0;

	public void addWagon(Wagon wagon) {
		if (TrainType == wagon.Type){
			Train.add(wagon);
			nrOfWagons++;
	}}

	public void CreateTrain(Wagon wagon) {
		if (Train == null) {
			Train.add(wagon);
			TrainType = wagon.Type;
		} else
			Train.add(wagon);
	}

	public int TotalProfit() {
		for (Object Wagon : Train) {
			TrainProfit += Wagon.Profit;
		}
	}

}
