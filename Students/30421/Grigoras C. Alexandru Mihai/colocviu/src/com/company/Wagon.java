package com.company;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Zuklar on 13-Jan-16.
 */
public class Wagon {
    // private final UUID ID; // Am incercat cu UUID, dar nu stiu ca sa ii generez valoarea
    private final int ID;
    private String type;
    private int ticketPrice = 100;
    private int maxNumberOfPassengers = 100;
    public int  numberOfPassengers;
    private CargonItem cargonItem;

    public Wagon(int id, String type)
    {

        ID = id;
        this.type = type;

        if (type.equals("passenger")){
            Random random = new Random();
            int numberOfPassengers = -1;
            while (numberOfPassengers < 1)
            {
                numberOfPassengers = random.nextInt(100);
            }
            this.numberOfPassengers = numberOfPassengers;
        }
        else{
            cargonItem = new CargonItem("a", 50);
        }
    }

    public int getprofit()
    {
        if (type.equals("passenger")){
            return ticketPrice * numberOfPassengers;
        }
        else{
            return cargonItem.profit * cargonItem.numberofitems;
        }
    }
}
