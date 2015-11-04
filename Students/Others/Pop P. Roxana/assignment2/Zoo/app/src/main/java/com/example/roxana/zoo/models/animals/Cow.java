package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Cow extends Mammal {

    int image= R.drawable.cow;

    public int getImage(){
        return image;
    }

    public Cow(Integer nrOfLegs, String name, float normalBodyTemp, float hairPerc, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setNormalBodyTemp(normalBodyTemp);
        setPercBodyHair(hairPerc);
    }

    public Cow() {
        this(new Integer(4), "cow0", 37, 0.8f, 6.7, 0.2);
    }

    public String getAnimalType(){
        return new String("cow");
    };
}

