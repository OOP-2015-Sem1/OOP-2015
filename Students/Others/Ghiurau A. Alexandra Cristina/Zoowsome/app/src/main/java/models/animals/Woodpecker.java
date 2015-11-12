package models.animals;

import com.alexasapps.zoowsome.R;

public class Woodpecker extends Bird {
    public Woodpecker() {
        super(7.5, 0.5);
        setName("Woody");
        setNrOfLegs(2);
        setAvgFlightAltitude(95);
        setMigrates(true);
    }

    public Woodpecker(String name, int avgFlightAltit, boolean migrates, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(2);
        setAvgFlightAltitude(avgFlightAltit);
        setMigrates(migrates);
    }

    int image = R.drawable.woody;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Woodpecker);
	}
	*/
}
