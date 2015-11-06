package com.example.roxana.zoo.models.animals;

import com.example.roxana.zoo.R;

/**
 * Created by Roxana on 10/25/2015.
 */
public class Monkey extends Mammal{

    int image= R.drawable.monkey;

    public int getImage(){
        return image;
    }

    public Monkey(Integer nrOfLegs, String name, float normalBodyTemp, float hairPerc, double maintenanceCost, double dangerPerc){

        super(maintenanceCost,dangerPerc);
        setNrOfLegs(nrOfLegs);
        setName(name);
        setNormalBodyTemp(normalBodyTemp);
        setPercBodyHair(hairPerc);
    }

    public Monkey (){
        this(new Integer(2), "monkey", 37, 0.75f, 5, 0.15);
    }

    public double getPredisposition(){

        DateAndTime time = new DateAndTime();

        if (time.getMonth()==6&&time.getHour()>=12&&time.getHour()<14){//july from 12-14
            //say it gets vrazy due to the heat
            return 0.05;
        }
        else{
            return 0;
        }
    }

    public String getAnimalType(){
        return new String("monkey");
    };
}
