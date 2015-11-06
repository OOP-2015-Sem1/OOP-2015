package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Turtle extends Reptile {

    int image= R.drawable.turtle;

    public int getImage(){
        return image;
    }

    public Turtle(Integer nrOfLegs, String name, boolean laysEggs, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setLaysEggs(laysEggs);
    }

    public Turtle() {
        this(new Integer(4), "turtle", true, 2, 0.01);
    }

    public String getAnimalType(){
        return new String("turtle");
    };
}
