package com.company;

import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;

/**
 * Created by Zuklar on 13-Jan-16.
 */
public class TrainStation {
    private ArrayList<Train> trains = new ArrayList<Train>();

    public void departing(String Name)
    {
        for (Train i: trains) {
            if (i.name.equals(Name)){
                trains.remove(i);
                break;
            }

        }
    }
    public void receiving(String Name, String wagontype){
        trains.add(new Train(Name,wagontype));
    }

    public ArrayList<Train> getTrains(){
        return trains;
    }

    public boolean checkifTrain(String name)
    {
        for (Train i:trains){
            if (i.name.equals(name))
                return true;
        }
        return false;
    }
}
