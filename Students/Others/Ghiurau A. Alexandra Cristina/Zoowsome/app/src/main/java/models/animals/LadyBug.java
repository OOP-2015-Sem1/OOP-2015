package models.animals;

import com.alexasapps.zoowsome.R;

public class LadyBug extends Insect {

    public LadyBug() {
        super(1, 0.01);
        setName("Coccinellidae");
        setNrOfLegs(12); // I have no idea
        setCanFly(true);
        setDangerous(false);
    }

    public LadyBug(String name, boolean canFly, int nrOfLegs, boolean isDangerous, double maintenanceCost,
                   double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setName(name);
        setNrOfLegs(nrOfLegs);
        setCanFly(canFly);
        setDangerous(isDangerous);
    }

    int image = R.drawable.ladybug;

    public int getImage() {
        return image;
    }
    /*
	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Insects.LadyBug);
	}
	*/
}
