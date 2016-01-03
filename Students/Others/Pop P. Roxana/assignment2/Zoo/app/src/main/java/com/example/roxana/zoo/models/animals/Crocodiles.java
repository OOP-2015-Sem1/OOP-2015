package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Crocodiles extends Reptile {

    int image= R.drawable.crocodile;

    public int getImage(){
        return image;
    }

    public Crocodiles(Integer nrOfLegs, String name, boolean laysEggs, double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setLaysEggs(laysEggs);
    }

    public Crocodiles() {
        this(new Integer(4), "croco ", true, 5, 0.70);
    }

    public double getPredisposition(){

        DateAndTime time = new DateAndTime();
        double predisposition = 0;

        if (time.getHour()>8||time.getHour()<20){

            predisposition =  0.10;

        }
        if (time.getMonth()>4&&time.getMonth()<8){//between May and September

            predisposition+=0.15;
        }
        return predisposition;
    }

    public String getAnimalType(){
        return new String("crocodile");
    };
}
