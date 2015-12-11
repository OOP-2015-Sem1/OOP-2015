package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Swordfish extends Aquatic {

    private int image = R.drawable.swordfish;

    public Swordfish(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName("Needle");
        setAvgSwimDepth(600);
        setIsDangerous(true);
    }

    public Swordfish(String name, boolean dangerous, int swimDepth, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setIsDangerous(dangerous);
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Swordfish);
//    }
}
