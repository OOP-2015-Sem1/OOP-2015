package models.animals;

import com.alexasapps.zoowsome.R;

public class SeaHorse extends Aquatic {

    public SeaHorse() {
        super(7.5, 0.5);
        setNrOfLegs(12);
        setName("Dory");
        setAvgSwimDepth(20);
        setWt(waterType.FRESHWATER);
    }

    public SeaHorse(String name, int swimDepth, waterType waterType, int nrOflegs, double maintenanceCost,
                    double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(nrOflegs);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setWt(waterType);
    }

    int image = R.drawable.seahorse;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.SeaHorse);
	}
	*/
}
