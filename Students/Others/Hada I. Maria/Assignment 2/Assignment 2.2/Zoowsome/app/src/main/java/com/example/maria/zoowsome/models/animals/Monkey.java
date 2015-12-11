package com.example.maria.zoowsome.models.animals;

public class Monkey extends Mammal {

    public Monkey(double cost, double danger) {
        super(cost, danger);
        setNormalBodyTemperature(39);
        setPercBodyHair(95);
        setNrOfLegs(2);
        setName("Chewy");
    }

    public Monkey(String name, double cost, double danger) {
        super(cost, danger);
        setNormalBodyTemperature(39);
        setPercBodyHair(95);
        setNrOfLegs(2);
        setName(name);
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Mammals.Monkey);
//    }
}
