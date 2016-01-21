package stationAdministration;

import java.util.ArrayList;

/**
 * Created by vladrusu on 13/01/16.
 */
public class TrainStation {
    private int numberOfTrains;
    private ArrayList<Train> trains;

    public TrainStation() {
        this.numberOfTrains = 0;
    }

    public int getNumberOfTrains() {
        return this.numberOfTrains;
    }

    public ArrayList<Train> getTrains() {
        return this.trains;
    }

    public void receiveTrain(Train train) {
        this.trains.add(train);
        this.numberOfTrains = this.trains.size();
    }

    public void departTrain(Train train) {
        this.trains.remove(train);
        this.numberOfTrains = this.trains.size();
    }

    Boolean trainIsInStation(Train train) {
        return this.trains.contains(train);
    }
}
