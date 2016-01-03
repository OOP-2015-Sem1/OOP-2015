package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Spider extends Insect {

    int image= R.drawable.spider;

    public int getImage(){
        return image;
    }

    public Spider(Integer nrOfLegs, String name, boolean canFly, boolean isDangerous,double maintenanceCost, double dangerPerc) {

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setCanFly(canFly);
        setIsDangerous(isDangerous);
    }

    public Spider() {
        this(new Integer(16), "spider", false, true, 5, 0.7);
    }

    public double getPredisposition(){

        DateAndTime time = new DateAndTime();

        if (time.getHour()>23||time.getHour()<6){

            return 0.25;

        }
        else{

            return 0;
        }

    }

    public String getAnimalType(){
        return new String("spider");
    };
}

