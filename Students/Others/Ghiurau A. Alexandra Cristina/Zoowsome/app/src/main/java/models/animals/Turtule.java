package models.animals;

import com.alexasapps.zoowsome.R;

/**
 * ..typo mistake, it should've been *turtle*
 *
 * @author Alexa
 */
public class Turtule extends Reptile {

    public Turtule() {
        super(7.5, 0.5);
        setNrOfLegs(4);
        setName("Donatello"); // Ninja Turtules..
        setLaysEggs(true);
    }

    public Turtule(String name, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(4);
        setName(name);
        setLaysEggs(true);
    }


    int image = R.drawable.turtle;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Reptiles.Turtule);
	}
	*/
}
