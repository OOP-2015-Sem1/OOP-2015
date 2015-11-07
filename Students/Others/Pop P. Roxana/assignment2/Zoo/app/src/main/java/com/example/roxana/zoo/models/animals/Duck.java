package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Duck extends Bird {

    int image= R.drawable.duck;

    public int getImage(){
        return image;
    }

    public Duck(Integer nrOfLegs, String name, Boolean migrates, Integer avgFlightAltitude, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setMigrates(migrates);
        setAvgFlightAltitude(avgFlightAltitude);
    }

    public Duck() {
        this(new Integer(16), "duck", true, new Integer(100), 3.5, 0);
    }

    public String getAnimalType(){
        return new String("duck");
    };
}
