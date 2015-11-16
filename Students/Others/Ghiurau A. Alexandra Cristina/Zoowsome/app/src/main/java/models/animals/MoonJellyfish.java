package models.animals;

import com.alexasapps.zoowsome.R;

public class MoonJellyfish extends Aquatic {

    public enum colourType {
        BLUE, RED, GREEN, GRAY, PINK
    }

    colourType colour;

    public MoonJellyfish() {
        super(7, 0.3);
        setNrOfLegs(9);
        setName("MoonJellyfishX");
        setAvgSwimDepth(21);
        setWt(waterType.FRESHWATER);
        setColour(colourType.BLUE);
    }

    public MoonJellyfish(String name, int swimDepth, waterType waterType, colourType colourT, double maintenanceCost,
                         double dangerPerc) {
        super(maintenanceCost, dangerPerc);
        setNrOfLegs(4);
        setName(name);
        setAvgSwimDepth(swimDepth);
        setWt(waterType);
        setColour(colourT); // or simply setColour(getCoulur()) ?
    }

    private void setColour(colourType colour) {
        this.colour = colour;
    }

    int image = R.drawable.jellyfish;

    public int getImage() {
        return image;
    }

	/*
    public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.MoonJellyfish);
	}
	*/

}
