package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Butterfly extends Insect {

    private int image = R.drawable.butterfly;

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

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Butterfly);
//    }
}
