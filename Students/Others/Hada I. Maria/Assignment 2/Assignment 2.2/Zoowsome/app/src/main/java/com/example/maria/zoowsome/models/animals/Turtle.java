package com.example.maria.zoowsome.models.animals;

public class Turtle extends Reptile {

    public Turtle(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(4);
        setName("Forrest G");
        setLaysEggs(true);
    }

    public Turtle(String name, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(4);
        setName(name);
        setLaysEggs(true);
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Reptiles.Turtle);
//    }
}

