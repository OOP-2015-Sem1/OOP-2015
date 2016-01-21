package trains;

import java.util.ArrayList;
import java.util.Set;

public class TrainStation {
	
	public ArrayList<Train> trainStation;
	//I know it should be unique but the Set doesn't work
	
	public TrainStation(){
		this.trainStation=new ArrayList<Train>();
		
	}
	
	public void createTrainStation(){
		 Train passangerTrain=new Train();
		 passangerTrain.createPassangerTrain();
		 
		 this.trainStation.add(passangerTrain);
	}
	
	
	public ArrayList<Train> getTrainStation() {
		return trainStation;
	}

	public void setTrainStation(ArrayList<Train> trainStation) {
		this.trainStation = trainStation;
	}
	public void createStation(){
		
	}


	

}
