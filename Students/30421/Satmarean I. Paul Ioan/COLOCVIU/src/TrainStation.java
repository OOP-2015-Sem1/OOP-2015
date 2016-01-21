import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by Paul on 13.01.2016.
 */
public class TrainStation {
    private HashSet<Train> trains;

    public TrainStation(){
        trains = new HashSet<Train>();
    }

    public void receiveTrain(Train train){
        if (!trains.contains(train)) {
            trains.add(train);
        }
    }

    public boolean checkTrain(Train train){
        return trains.contains(train);
    }

    public void departTrain(Train train){
        Iterator<Train> t = trains.iterator();
        while(t.next()!=null){
            if (t.equals(train)){
                t.remove();
                return;
            }
        }

    }

    public HashSet<Train> getTrains() {
        return trains;
    }
}
