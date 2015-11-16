package models.animals;

import com.alexasapps.zoowsome.R;

public class SeaTurtle extends Aquatic {

    public SeaTurtle() {
        super(7.5, 0.5);
        setNrOfLegs(4);
        setName("Nemo");
        setAvgSwimDepth(20);
        setWt(waterType.FRESHWATER);
    }

    public SeaTurtle(String name, int swimDepth, waterType waterType, double maintenanceCost, double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(4);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setWt(waterType);
    }

    int image = R.drawable.seaturtle;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.SeaTurtle);
	}
	*/
}
