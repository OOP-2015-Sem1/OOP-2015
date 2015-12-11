package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.models.interfaces.XML_Parsable;

import org.w3c.dom.Element;

import java.util.Random;

public abstract class Animal implements XML_Parsable {

    private int nrOfLegs;
    private String name;
    private double maintenanceCost;
    private double dangerPerc;
    private boolean takenCareOf;

    public Animal(double cost, double danger) {
        maintenanceCost = cost;
        dangerPerc = danger;
        setTakenCareOf(false);
    }

    public void setNrOfLegs(int legs) {
        nrOfLegs = legs;
    }

    public int getNrOfLegs() {
        return nrOfLegs;
    }

    public void setName(String theName) {
        name = theName;
    }

    public String getName() {
        return name;
    }

    public void setMaintenanceCost(double cost) {
        maintenanceCost = cost;
    }

    public double getMaintenanceCost() {
        return maintenanceCost;
    }

    public void setDangerPerc(double danger) {
        dangerPerc = danger;
    }

    public double getDangerPerc() {
        return dangerPerc + getPredisposition();
    }

    public boolean kill() {
        Random r = new Random();
        int random = r.nextInt(2);
        boolean kill = false;
        if (random < dangerPerc + getPredisposition()) {
            kill = true;
        } else {
            kill = false;
        }
        return kill;
    }

    public void setTakenCareOf(boolean takenCare) {
        takenCareOf = takenCare;
    }

    public boolean getTakenCareOf() {
        return takenCareOf;
    }

    public double getPredisposition() {
        return 0;
    }

   /* public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
        createNode(eventWriter, "nrOfLegs", String.valueOf(this.nrOfLegs));
        createNode(eventWriter, "name", String.valueOf(this.name));
        createNode(eventWriter, "maintenanceCost", String.valueOf(this.maintenanceCost));
        createNode(eventWriter, "dangerPerc", String.valueOf(this.dangerPerc));
        createNode(eventWriter, "takenCareOf", String.valueOf(this.takenCareOf));
    }
*/
    public void decodeFromXml(Element element) {
        setNrOfLegs(Integer.valueOf(element.getElementsByTagName("nrOfLegs").item(0).getTextContent()));
        setName(element.getElementsByTagName("name").item(0).getTextContent());
        setMaintenanceCost(Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
        setDangerPerc(Double.valueOf(element.getElementsByTagName("dangerPerc").item(0).getTextContent()));
        setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("takenCareOf").item(0).getTextContent()));
    }
}
