package stationAdministration;

import java.util.ArrayList;
import java.util.UUID;

import carriables.Carriable;
import carriables.Passenger;

/**
 * Created by vladrusu on 13/01/16.
 */
public class Wagon {
    private final String id;
    private Boolean passengerType;
    private ArrayList<Carriable> objects;
    private int numberOfObjects;
    private final int maximumNumberOfPassengers = 100;
    private final int ticketPrice = 100;

    public Wagon(ArrayList<Carriable> objects) {
        id = new UUID(0, 10000).toString();
        Class classOfObject = objects.get(0).getClass();
        for (Carriable object:objects) {
            if (object.getClass() != classOfObject) {
//                this = null;
                return;
            }
        }
        if (classOfObject == Passenger.class) {
            if (objects.size() >= this.maximumNumberOfPassengers) {
//                this = null;
                return;
            }
            this.passengerType = true;
        } else {
            this.passengerType = false;
        }
        this.objects = objects;
        this.numberOfObjects = objects.size();
    }

    public String getId() {
        return this.id;
    }

    public int getNumberOfObjects() {
        return this.numberOfObjects;
    }

    private int getProfitForPassengerType() {
        return this.numberOfObjects * this.ticketPrice;
    }

    private int getProfitForCargoItemType() {
        int profit = 0;
        for (Carriable item:this.objects) {
            profit = profit + item.getProfit();
        }
        return profit;
    }

    public int getProfit() {
        if (this.passengerType) {
            return this.getProfitForPassengerType();
        } else {
            return this.getProfitForCargoItemType();
        }
    }
}
