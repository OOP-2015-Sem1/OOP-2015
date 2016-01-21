package stationAdministration;

import java.util.ArrayList;

/**
 * Created by vladrusu on 13/01/16.
 */
public class Train implements Comparable<Train> {
    private int numberOfLinkedWagons;
    private ArrayList<Wagon> linkedWagons;
    private String name;
    private Boolean passengerTrain;

    public Train(String name, ArrayList<Wagon> wagons) {
        this.numberOfLinkedWagons = wagons.size();
        this.linkedWagons = wagons;
        this.name = name;
    }

    public int getNumberOfLinkedWagons() {
        return this.numberOfLinkedWagons;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void appendWagon(Wagon wagon) {
        this.linkedWagons.add(wagon);
        this.numberOfLinkedWagons = this.numberOfLinkedWagons + 1;
    }

    public void appendWagons(ArrayList<Wagon> wagons) {
        this.linkedWagons.addAll(wagons);
    }

    public Boolean isForPassengers() {
        return this.passengerTrain;
    }

    public int getProfit() {
        return 0;
    }

    @Override
    public int compareTo(Train another) {
        if (this.name.compareTo(another.name) == 0) {
            return this.getProfit() - another.getProfit();
        }
        return this.name.compareTo(another.name);
    }
}
