package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

import java.util.Calendar;
import java.util.TimeZone;

public class Tiger extends Mammal {

    private int image = R.drawable.tiger;

    public Tiger(double cost, double danger) {
        super(cost, danger);
        setNormalBodyTemperature(37);
        setPercBodyHair(90);
        setNrOfLegs(4);
        setName("Wally");
    }

    public Tiger(String name, double cost, double danger) {
        super(cost, danger);
        setNormalBodyTemperature(37);
        setPercBodyHair(90);
        setNrOfLegs(4);
        setName(name);
    }

    public double getPredisposition() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        if ((dayOfMonth >= 10) && (dayOfMonth <= 17)) {
            return 0.30;
        }
        return 0;
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Mammals.Tiger);
//    }
}
