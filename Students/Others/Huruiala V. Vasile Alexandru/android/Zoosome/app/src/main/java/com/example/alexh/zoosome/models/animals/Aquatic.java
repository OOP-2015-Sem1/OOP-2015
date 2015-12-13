package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Aquatic extends Animal {
	private static final int DEFAULT_AVG_SWIM_DEPTH = 0;
	private static final WaterType DEFAULT_WATER_TYPE = WaterType.getWater(0);

	private static final String FIELD_NAME_AVERAGE_SWIM_DEPTH = "Average swim depth";
	private static final String FIELD_NAME_WATER_TYPE = "Water type";

	// SQLite resources
	public static final String TABLE_AQUATIC_NAME = "aquatic_table";
	public static final String TABLE_AQUATIC_COL_ID = TABLE_CLASS_COL_ID;
	public static final String TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH = "averageSwimDepth";
	public static final String TABLE_AQUATIC_COL_WATER_TYPE = "waterType";

	public static final String TABLE_AQUATIC_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
	public static final String TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH_MODIFIERS = "INTEGER";
	public static final String TABLE_AQUATIC_COL_WATER_TYPE_MODIFIERS = "VARCHAR(10)";

	private int avgSwimDepth;
	private WaterType waterType;

	protected Aquatic() {
		super();
		this.avgSwimDepth = DEFAULT_AVG_SWIM_DEPTH;
		this.waterType = DEFAULT_WATER_TYPE;
	}

	protected Aquatic(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final int swimDepth, final WaterType wType) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
		this.avgSwimDepth = swimDepth;
		this.waterType = wType;
	}

	protected Aquatic(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final int swimDepth, final int waterCode) {
		this(animalName, numberOfLegs, maintenanceCost, dangerPerc, swimDepth, WaterType.getWater(waterCode));
	}

	public Aquatic(ArrayList<String> parameters) {
		this(
				parameters.get(0),
				Integer.parseInt(parameters.get(1)),
				Double.parseDouble(parameters.get(2)),
				Double.parseDouble(parameters.get(3)),
				Integer.parseInt(parameters.get(5)),
				WaterType.getWater(parameters.get(6))
		);
		super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
	}

	public int getAvgSwimDepth() {
		return this.avgSwimDepth;
	}

	public void setAvgSwimDepth(final int swimDepth) {
		this.avgSwimDepth = swimDepth;
	}

	public WaterType getWaterType() {
		return this.waterType;
	}

	public void setWaterType(final WaterType wType) {
		this.waterType = wType;
	}

	@Override
	public ArrayList<String> getClassFieldValues() {
		ArrayList<String> fieldValues = new ArrayList<>();

		fieldValues.add(String.valueOf(this.getAvgSwimDepth()));
		fieldValues.add(String.valueOf(this.getWaterType()));

		return fieldValues;
	}

	@Override
	public ArrayList<String> getClassFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<>();

		fieldNames.add(FIELD_NAME_AVERAGE_SWIM_DEPTH);
		fieldNames.add(FIELD_NAME_WATER_TYPE);

		return fieldNames;
	}

	@Override
	public ArrayList[] getClassFieldNamesAndValues() {
		ArrayList[] fieldNamesAndValues = new ArrayList[2];

		fieldNamesAndValues[0] = this.getClassFieldNames();
		fieldNamesAndValues[1] = this.getClassFieldValues();

		return fieldNamesAndValues;
	}

	@Override
	public ArrayList<String> getClassFieldInsertColumnNames() {
		ArrayList<String> fieldInsertColumnNames = new ArrayList<>();

		fieldInsertColumnNames.add(TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH);
		fieldInsertColumnNames.add(TABLE_AQUATIC_COL_WATER_TYPE);

		return fieldInsertColumnNames;
	}

	@Override
	public ArrayList[] getClassFieldInsertColumnNamesAndValues() {
		ArrayList[] fieldInsertColumnNamesAndValues = new ArrayList[2];

		fieldInsertColumnNamesAndValues[0] = this.getClassFieldInsertColumnNames();
		fieldInsertColumnNamesAndValues[1] = this.getClassFieldValues();

		return fieldInsertColumnNamesAndValues;
	}

/*
	public void encodeToXML(XMLEventWriter eventWriter) throws XMLStreamException {
		super.encodeToXML(eventWriter);
		createNode(eventWriter, "avgSwimDepth", String.valueOf(this.getAvgSwimDepth()));
		createNode(eventWriter, "waterType", String.valueOf(this.getWaterType()));
	}
*/
	public void decodeFromXML(Element element) {
		super.decodeFromXML(element);
		setAvgSwimDepth(Integer.valueOf(element.getElementsByTagName("avgSwimDepth").item(0).getTextContent()));
		setWaterType(WaterType.getWater(element.getElementsByTagName("waterType").item(0).getTextContent()));
	}
}
