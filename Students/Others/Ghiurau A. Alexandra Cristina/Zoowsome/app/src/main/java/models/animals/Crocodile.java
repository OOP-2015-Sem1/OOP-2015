package models.animals;

import com.alexasapps.zoowsome.R;

public class Crocodile extends Reptile {

    public Crocodile() {
        super(7, 1);
        setNrOfLegs(4);
        setName("Lizard / Curt Connors");
        setLaysEggs(true);
    }

    public Crocodile(String name, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(4);
        setName(name);
        setLaysEggs(true);
    }

    int image = R.drawable.croco;

    public int getImage() {
        return image;
    }
    /*
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Reptiles.Crocodile);
	}
	*/
}
