package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Dolphin extends Aquatic {

    private int image = R.drawable.dolphin;

    public Dolphin(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName("Flipper");
        setAvgSwimDepth(90);
        setIsDangerous(false);
    }

    public Dolphin(String name, int swimDepth, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setIsDangerous(false);
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Dolphin);
//    }
}
