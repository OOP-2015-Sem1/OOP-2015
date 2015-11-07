package com.example.roxana.zoo.models.animals;

import org.w3c.dom.Element;

/**
 * Created by Roxana on 10/25/2015.
 */
public abstract class Insect extends Animal {

    private boolean canFly;
    private boolean isDangerous;

    public Insect(double maintenanceCost, double dangerPerc){
        super(maintenanceCost,dangerPerc);
    }

    public boolean getCanFly() {
        return canFly;
    }

    public void setCanFly(boolean canFly) {
        this.canFly = canFly;
    }

    public boolean getIsDangerous() {
        return isDangerous;
    }

    public void setIsDangerous(boolean isDangerous) {
        this.isDangerous = isDangerous;
    }

    public void decodeFromXml(Element element) {

        super.decodeFromXml(element);
        setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
        setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
    }

    public void getAnimalAttributes(){
        System.out.println("Species: Insect ");
        System.out.println("Name: "+getName());
        System.out.println("Number of legs: "+getNrOfLegs());
        System.out.println("Maintenance cost: "+super.maintenanceCost);
        System.out.println("Danger: "+(super.dangerPerc*100)+"%");
        System.out.println("Extra details:");
        System.out.println((getIsDangerous())?"It is dangerous":"It is not dangerous");
        System.out.println((getCanFly())?"It can fly":"It simply can't fly");
        System.out.println();
    }

    @Override
    public String toString() {

        String s ="Species: Insect \n";
        s=s+"Number of legs: "+getNrOfLegs()+"\n";
        s=s+"Maintenance cost: "+super.maintenanceCost+"\n";
        s=s+"Danger: "+(super.dangerPerc*100)+"%\n";
        s=s+"Extra details:<br>";
        s=s+((getIsDangerous())?"It is dangerous":"It is not dangerous")+"\n";
        s=s+((getCanFly())?"It can fly":"It simply can't fly")+"\n";
        return s;
    }

}
