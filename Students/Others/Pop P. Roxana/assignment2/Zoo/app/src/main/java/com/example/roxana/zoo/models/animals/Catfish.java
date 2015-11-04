package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Catfish extends Aquatic {

    int image= R.drawable.catfish;

    public int getImage(){
        return image;
    }

    public Catfish(Integer nrOfLegs, String name,Integer avgSwimDepth, Water waterType, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setAvgSwimDepth(avgSwimDepth);
        setWaterType(waterType);
    }

    public Catfish() {
        this(new Integer(0), "catfish", new Integer(100), Water.FRESHWATER, 3, 0);
    }

    public String getAnimalType(){
        return new String("catfish");
    };
}


