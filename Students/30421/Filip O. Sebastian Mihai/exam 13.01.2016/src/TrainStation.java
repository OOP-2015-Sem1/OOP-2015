import java.util.ArrayList;
import java.util.UUID;

public class TrainStation {
	public ArrayList<Train> trainstat = new ArrayList<Train>();
	TrainStation(int numberoftrains){
		for(int i=0; i< numberoftrains; i++){
			Train tr = new Train(1,"passangers","123");
			trainstat.add(tr);
		}
	}
}
