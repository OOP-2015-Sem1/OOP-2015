package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Dolphin extends Aquatic {

    int image= R.drawable.dolphin;

    public int getImage(){
        return image;
    }

    public Dolphin(Integer nrOfLegs, String name,Integer avgSwimDepth, Water waterType, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setAvgSwimDepth(avgSwimDepth);
        setWaterType(waterType);
    }

    public Dolphin() {
        this(new Integer(0), "dolphin", new Integer(100), Water.SALTWATER, 4, 0);
    }

    public String getAnimalType(){
        return new String("dolphin");
    };
}

