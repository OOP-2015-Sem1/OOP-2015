package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Sparrow extends Bird {

    int image= R.drawable.sparrow;

    public int getImage(){
        return image;
    }

    public Sparrow(Integer nrOfLegs, String name, Boolean migrates, Integer avgFlightAltitude, double maintenanceCost,
                   double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setMigrates(migrates);
        setAvgFlightAltitude(avgFlightAltitude);
    }

    public Sparrow() {
        this(new Integer(2), "sparrow", true, new Integer(100), 4, 0);
    }

    public String getAnimalType(){
        return new String("sparrow");
    };
}
