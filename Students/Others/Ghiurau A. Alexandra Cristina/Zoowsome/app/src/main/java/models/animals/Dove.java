package models.animals;

import com.alexasapps.zoowsome.R;

public class Dove extends Bird {

    public Dove() {
        super(6, 0.3);
        setName("Sansa");
        setNrOfLegs(2);
        setAvgFlightAltitude(100);
        setMigrates(false);
    }

    public Dove(String name, int avgFlightAltit, boolean migrates, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(2);
        setAvgFlightAltitude(avgFlightAltit);
        setMigrates(migrates);
    }

    int image = R.drawable.dove;

    public int getImage() {
        return image;
    }
    /*
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Birds.Dove);
	}
	*/
}
