package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Cockroach extends Insect {

    int image= R.drawable.cockroach;

    public int getImage(){
        return image;
    }

    public Cockroach(Integer nrOfLegs, String name, boolean canFly, boolean isDangerous, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setCanFly(canFly);
        setIsDangerous(isDangerous);
    }

    public Cockroach() {
        this(new Integer(100), "cockroach", false, false, 2, 0.1);
    }

    public String getAnimalType(){
        return new String("cockroach");
    };
}
