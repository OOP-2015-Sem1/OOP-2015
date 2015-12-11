package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Monkey extends Mammal {

    private int image = R.drawable.monkey;

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

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Mammals.Monkey);
//    }
}
