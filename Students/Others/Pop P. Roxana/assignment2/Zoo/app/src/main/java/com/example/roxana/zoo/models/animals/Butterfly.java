package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Butterfly extends Insect {

    int image= R.drawable.butterfly;

    public int getImage(){
        return image;
    }


    public Butterfly(Integer nrOfLegs, String name, boolean canFly, boolean isDangerous, double maintenanceCost, double dangerPerc) {

        super( maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setCanFly(canFly);
        setIsDangerous(isDangerous);
    }

    public Butterfly() {
        this(new Integer(16), "butterfly", true, false, 2, 0);
    }

    public String getAnimalType(){
        return new String("butterfly");
    };
}

