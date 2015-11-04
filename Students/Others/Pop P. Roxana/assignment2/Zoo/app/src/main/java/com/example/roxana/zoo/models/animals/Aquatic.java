package com.example.roxana.zoo.models.animals;

import org.w3c.dom.Element;

/**
 * Created by Roxana on 10/25/2015.
 */
public abstract class Aquatic extends Animal {

    public Aquatic(double maintenanceCost, double dangerPerc) {//do I need this?
        super(maintenanceCost, dangerPerc);
    }

    //attributes
    private Integer avgSwimDepth;
    private Water waterType;
    //setters and getters
    public Integer getAvgSwimDepth() {
        return avgSwimDepth;
    }

    public void setAvgSwimDepth(Integer avgSwimDepth) {
        this.avgSwimDepth = avgSwimDepth;
    }

    public Water getWaterType() {
        return waterType;
    }

    public void setWaterType(Water waterType) {
        this.waterType = waterType;
    }

    public void getAnimalAttributes(){
        System.out.println("Species: Aquatic ");
        System.out.println("Name: "+getName());
        System.out.println("Number of legs: "+getNrOfLegs());
        System.out.println("Maintenance cost: "+super.maintenanceCost);
        System.out.println("Danger: "+(super.dangerPerc*100)+"%");
        System.out.println("Extra details:");
        System.out.println("Its average swim depth is: "+getAvgSwimDepth());
        System.out.println("It lives in "+getWaterType());
        System.out.println();
    }

    @Override
    public String toString() {

        String s ="Species: Aquatic \n";
        s=s+"Number of legs: "+getNrOfLegs()+"\n";
        s=s+"Maintenance cost: "+super.maintenanceCost+"\n";
        s=s+"Danger: "+(super.dangerPerc*100)+"%\n";
        s=s+"Extra details:\n";
        s=s+"Its average swim depth is: "+getAvgSwimDepth()+"\n";
        s=s+"It lives in "+getWaterType()+"\n";
        return s;
    }

    //decoding from xml
    public void decodeFromXml(Element element) {

        super.decodeFromXml(element);//should I put it here? :?
        setAvgSwimDepth(Integer.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
        //setWaterType(Water.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
    }

}
