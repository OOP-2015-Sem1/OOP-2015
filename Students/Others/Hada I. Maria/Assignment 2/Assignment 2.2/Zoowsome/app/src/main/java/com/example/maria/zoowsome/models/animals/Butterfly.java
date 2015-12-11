package com.example.maria.zoowsome.models.animals;

public class Butterfly extends Insect {

    public Butterfly(double cost, double danger) {
        super(cost, danger);
        setCanFly(true);
        setIsDangerous(false);
        setNrOfLegs(6);
        setName("Butterfree");
    }

    public Butterfly(String name, double cost, double danger) {
        super(cost, danger);
        setCanFly(true);
        setIsDangerous(false);
        setNrOfLegs(6);
        setName(name);
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Butterfly);
//    }
}
