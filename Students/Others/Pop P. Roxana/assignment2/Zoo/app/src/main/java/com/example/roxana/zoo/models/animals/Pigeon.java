package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Pigeon extends Bird {

    int image= R.drawable.pigeon;

    public int getImage(){
        return image;
    }

    public Pigeon(Integer nrOfLegs, String name, Boolean migrates, Integer avgFlightAltitude,double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setMigrates(migrates);
        setAvgFlightAltitude(avgFlightAltitude);
    }

    public Pigeon() {
        this(new Integer(2), "pigeon", false, new Integer(100), 4.2, 0);
    }

    public String getAnimalType(){
        return new String("pigeon");
    };
}