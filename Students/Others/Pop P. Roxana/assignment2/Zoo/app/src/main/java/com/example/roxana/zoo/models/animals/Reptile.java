package com.example.roxana.zoo.models.animals;

import org.w3c.dom.Element;

/**
 * Created by Roxana on 10/25/2015.
 */
public abstract class Reptile extends Animal {

    private boolean laysEggs;

    public Reptile(double maintenanceCost, double dangerPerc){
        super(maintenanceCost,dangerPerc);
    }

    public Boolean getLaysEggs(){
        return laysEggs;
    }

    public void setLaysEggs(boolean laysEggs){
        this.laysEggs = laysEggs;
    }

    //decoding from xml
    public void decodeFromXml(Element element) {

        super.decodeFromXml(element);
        setLaysEggs(Boolean.valueOf(element.getElementsByTagName("laysEggs").item(0).getTextContent()));
    }

    public void getAnimalAttributes(){
        System.out.println("Species: Reptile ");
        System.out.println("Name: "+getName());
        System.out.println("Number of legs: "+getNrOfLegs());
        System.out.println("Maintenance cost: "+super.maintenanceCost);
        System.out.println("Danger: "+(super.dangerPerc*100)+"%");
        System.out.println("Extra details:");
        System.out.println((getLaysEggs()) ? "It lays eggs" : "It does not lay eggs");
        System.out.println();
    }

    @Override
    public String toString() {

        String s ="Species: Reptile \n";
        s=s+"Number of legs: "+getNrOfLegs()+"\n";
        s=s+"Maintenance cost: "+super.maintenanceCost+"\n";
        s=s+"Danger: "+(super.dangerPerc*100)+"%\n";
        s=s+"Extra details:\n";
        s=s+((getLaysEggs())?"It lays eggs":"It does not lay eggs")+"\n";
        return s;
    }
}

