package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

public class Hummingbird extends Bird {

    private int image = R.drawable.hummingbird;

    public Hummingbird(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(2);
        setName("Hummy");
        setMigrates(true);
        setAvgFlightAltitude(300);
    }

    public Hummingbird(String name, int flightAltitude, boolean migrate, double cost, double danger) {
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
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Hummingbird);
//    }
}
