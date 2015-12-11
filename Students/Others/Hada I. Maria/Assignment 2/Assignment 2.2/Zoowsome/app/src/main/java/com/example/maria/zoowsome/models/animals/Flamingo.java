package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Flamingo extends Bird {

    private int image = R.drawable.flamingo;

    public Flamingo(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(2);
        setName("Pink Flame");
        setMigrates(true);
        setAvgFlightAltitude(20); // no idea
    }

    public Flamingo(String name, int flightAltitude, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(2);
        setName(name);
        setMigrates(true);
        setAvgFlightAltitude(flightAltitude);
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Flamingo);
//    }
}
