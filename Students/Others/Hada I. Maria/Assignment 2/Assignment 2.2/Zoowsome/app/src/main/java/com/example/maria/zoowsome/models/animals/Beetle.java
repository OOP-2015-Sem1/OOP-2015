package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Beetle extends Insect {

    private int image = R.drawable.beetle;

    public Beetle(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(6);
        setName("Heracross");
        setCanFly(true);
        setIsDangerous(false);

    }

    public Beetle(String name, boolean dangerous, boolean fly, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(6);
        setName(name);
        setCanFly(fly);
        setIsDangerous(dangerous);

    }

    public int getImage() {
        return image;
    }
//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.Beetle);
//    }
}
