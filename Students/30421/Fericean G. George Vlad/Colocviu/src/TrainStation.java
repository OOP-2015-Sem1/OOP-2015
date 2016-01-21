import java.util.HashSet;
import java.util.Set;

public class TrainStation<String> {
	public int nrTrains;
	Set trains = new HashSet();
	Train train1=new Train();
	public void getTrain()
	{
		trains.add(train1);
	}
	public void departTrain()
	{
		trains.remove(train1);
	}
	public void checkTrain()
	{
		boolean containsElement = trains.contains(train1);
	}
	
}
