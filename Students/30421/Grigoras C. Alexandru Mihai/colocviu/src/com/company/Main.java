package com.company;

import com.sun.javafx.collections.SortableList;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;

public class Main {



    public static void  PrintTrains(ArrayList<Train> trains){
        for (Train i: trains) {
            System.out.printf("%s %d %d\n", i.name, i.numberOfWagons, i.computeProfit());
        }
    }

    public static void main(String[] args) {
        TrainStation trainStation = new TrainStation();
        trainStation.receiving("CFR1", "passenger");
        trainStation.receiving("CFR2", "passenger");
        trainStation.receiving("CFR3", "passenger");
        trainStation.receiving("CFR4", "passenger");
        trainStation.receiving("CFR5", "passenger");
        trainStation.receiving("CFR6", "cargo");
        trainStation.receiving("CFR7", "cargo");
        trainStation.receiving("CFR8", "cargo");
        trainStation.receiving("CFR9", "cargo");
        trainStation.receiving("CFR10", "cargo");
        trainStation.receiving("CFR11", "cargo");

        trainStation.departing("CFR1");

        PrintTrains(trainStation.getTrains());

        System.out.println(trainStation.checkifTrain("CFR1"));
    }
}
