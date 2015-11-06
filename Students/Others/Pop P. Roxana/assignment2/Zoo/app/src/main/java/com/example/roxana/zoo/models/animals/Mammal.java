package com.example.roxana.zoo.models.animals;

import org.w3c.dom.Element;

/**
 * Created by Roxana on 10/25/2015.
 */
public abstract class Mammal extends Animal {

    //attributes
    private  float normalBodyTemp ;
    private float percBodyHair ;

    public Mammal(double maintenanceCost, double dangerPerc){
        super(maintenanceCost,dangerPerc);
    }

    //setters and getters
    public float getNormalBodyTemp(){
        return normalBodyTemp;
    }

    public void setNormalBodyTemp(float normalBodyTemp){
        this.normalBodyTemp = normalBodyTemp;
    }

    public float getPercBodyHair(){
        return percBodyHair;
    }

    public void setPercBodyHair(float percBodyHair){
        this.percBodyHair = percBodyHair;
    }

    //decoding from xml
    public void decodeFromXml(Element element) {

        super.decodeFromXml(element);
        setNormalBodyTemp(Float.valueOf(element.getElementsByTagName("normalBodyTemp").item(0).getTextContent()));
        setPercBodyHair(Float.valueOf(element.getElementsByTagName("percBodyHair").item(0).getTextContent()));
    }

    //attributes printing
    public void getAnimalAttributes(){
        System.out.println("Species: Mammal ");
        System.out.println("Name: "+getName());
        System.out.println("Number of legs: "+getNrOfLegs());
        System.out.println("Maintenance cost: "+super.maintenanceCost);
        System.out.println("Danger: "+(super.dangerPerc*100)+"%");
        System.out.println("Extra details:");
        System.out.println("It has a normal temp of: "+getNormalBodyTemp());
        System.out.println("It is covered in hair to a percentage of "+getPercBodyHair());
        System.out.println();
    }

    @Override
    public String toString() {

        String s ="Species: Mammal \n";
        s=s+"Number of legs: "+getNrOfLegs()+"\n";
        s=s+"Maintenance cost: "+super.maintenanceCost+"\n";
        s=s+"Danger: "+(super.dangerPerc*100)+"%\n";
        s=s+"Extra details:\n";
        s=s+"It has a normal temp of: "+getNormalBodyTemp()+"\n";
        s=s+"It is covered in hair to a percentage of "+getPercBodyHair()+"\n";
        return s;
    }

}

