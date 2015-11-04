package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Shark extends Aquatic {

    int image= R.drawable.shark;

    public int getImage(){
        return image;
    }

    public Shark(Integer nrOfLegs, String name,Integer avgSwimDepth, Water waterType,double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setAvgSwimDepth(avgSwimDepth);
        setWaterType(waterType);
    }

    public Shark() {
        this(new Integer(0), "shark0", new Integer(100), Water.SALTWATER, 5, 0.95);
    }

    public double getPredisposition(){

        DateAndTime time = new DateAndTime();

        if (time.getHour()>8&&time.getHour()<10){//say that's when the caretaker has to feed it
            return 0.20;
        }
        else{
            return 0;
        }
    }

    public String getAnimalType(){
        return new String("shark");
    };
}

