package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Owl extends Bird {

    private int image = R.drawable.owl;

    public Owl(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(2);
        setName("Hedwig");
        setMigrates(true);
        setAvgFlightAltitude(1000);
    }

    public Owl(String name, boolean migrate, int flightAltitude, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(2);
        setName(name);
        setMigrates(migrate);
        setAvgFlightAltitude(flightAltitude);
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Owl);
//    }
}
