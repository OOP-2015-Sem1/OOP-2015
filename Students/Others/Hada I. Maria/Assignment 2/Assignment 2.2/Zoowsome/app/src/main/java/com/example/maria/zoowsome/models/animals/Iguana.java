package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Iguana extends Reptile {

    private int image = R.drawable.iguana;

    public Iguana(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(4);
        setName("Stan");
        setLaysEggs(true);
    }

    public Iguana(String name, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(4);
        setName(name);
        setLaysEggs(true);
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Reptiles.Iguana);
//    }
}
