package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Tiger extends Mammal {

    int image= R.drawable.tiger;

    public int getImage(){
        return image;
    }

    public Tiger(Integer nrOfLegs, String name, float normalBodyTemp, float hairPerc, double maintenanceCost,
                 double dangerPerc) {

        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setNormalBodyTemp(normalBodyTemp);
        setPercBodyHair(hairPerc);
    }

    public Tiger() {
        this(new Integer(4), "tiger", 37, 0.9f, 6, 0.9);
    }

    public String getAnimalType(){
        return new String("tiger");
    };
}

