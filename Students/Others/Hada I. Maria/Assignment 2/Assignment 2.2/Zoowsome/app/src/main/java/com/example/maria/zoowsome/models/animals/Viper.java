package com.example.maria.zoowsome.models.animals;

import com.example.maria.zoowsome.R;

import java.util.Calendar;
import java.util.TimeZone;

public class Viper extends Reptile {

    private int image = R.drawable.viper;

    public Viper(double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName("Nagini");
        setLaysEggs(true);
    }

    public Viper(String name, boolean eggs, double cost, double danger) {
        super(cost, danger);
        setNrOfLegs(0);
        setName(name);
        setLaysEggs(eggs);
    }

    public double getPredisposition() {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        if ((hourOfDay >= 15) && (hourOfDay <= 22)) {
            return 0.15;
        }
        return 0;
    }

    public int getImage() {
        return image;
    }

//    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
//        super.encodeToXml(eventWriter);
//        createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Reptiles.Viper);
//    }
}
