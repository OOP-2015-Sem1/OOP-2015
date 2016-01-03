package models.animals;

import com.alexasapps.zoowsome.R;

public class Nightingale extends Bird {

    public Nightingale() {
        super(7, 0.6);
        setName("Zazu"); // ..from Lion King
        setNrOfLegs(2);
        setAvgFlightAltitude(85);
        setMigrates(true);
    }

    public Nightingale(String name, int avgFlightAltit, boolean migrates, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(2);
        setAvgFlightAltitude(avgFlightAltit);
        setMigrates(migrates);
    }

    int image = R.drawable.night;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Nightingale);
	}
	*/
}
