package models.animals;

import java.util.List;

public class Goldfish extends Aquatic {
	private static long counter;
	private final long id = counter++;

	public Goldfish(int avgSwimDepth, WaterType waterType, int nrOfLegs, String name, double maintenanceCost,
			double dangerPerc, boolean takenCareOf) {
		super(nrOfLegs, name, maintenanceCost, dangerPerc, takenCareOf, avgSwimDepth, waterType);
	}

	// Keep the order from above;
	public Goldfish(List<String> attributes) {
		super(Integer.valueOf(attributes.get(2)), attributes.get(3), Double.valueOf(attributes.get(4)),
				Double.valueOf(attributes.get(5)), Boolean.valueOf(attributes.get(6)),
				Integer.valueOf(attributes.get(0)), WaterType.valueOf(attributes.get(1)));
	}

	public Goldfish() {
		super(0.5, 0);
		setName("Goldfish" + id);
		setNrOfLegs(0);
		setAvgSwimDepth(1);
		setWaterType(WaterType.FRESHWATER);
	}

	public Goldfish(String name) {
		super(0.5, 0);
		setName(name);
		setNrOfLegs(0);
		setAvgSwimDepth(1);
		setWaterType(WaterType.FRESHWATER);
	}

	public Goldfish(int legs, int depth, WaterType water) {
		super(0.5, 0);
		setName("Goldfish" + id);
		setNrOfLegs(legs);
		setAvgSwimDepth(depth);
		setWaterType(water);
	}

/*	public void encodeToXml(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXml(eventWriter);
		createNode(eventWriter, Constants.XML_TAGS.DISCRIMINANT, Constants.Animals.Aquatics.Goldfish);
	}*/
}
