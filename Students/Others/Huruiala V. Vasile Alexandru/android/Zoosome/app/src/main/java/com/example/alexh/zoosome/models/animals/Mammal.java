package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/
import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Mammal extends Animal {
	private static final double DEFAULT_NORMAL_BODY_TEMPERATURE = 0.0D;
	private static final double DEFAULT_PERCENTAGE_BODY_HAIR = 0.0D;

	private static final String FIELD_NAME_NORMAL_BODY_TEMPERATURE = "Normal body temperature";
	private static final String FIELD_NAME_PERCENTAGE_BODY_HAIR = "Percentage body hair";

	// SQLite resources
	public static final String TABLE_MAMMAL_NAME = "mammal_table";
	public static final String TABLE_MAMMAL_COL_ID = TABLE_CLASS_COL_ID;
	public static final String TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE = "normalBodyTemp";
	public static final String TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR = "percBodyHair";

	public static final String TABLE_MAMMAL_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
	public static final String TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE_MODIFIERS = "DOUBLE";
	public static final String TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR_MODIFIERS = "DOUBLE";


	private double normalBodyTemp;
	private double percBodyHair;

	protected Mammal() {
		super();
		this.normalBodyTemp = DEFAULT_NORMAL_BODY_TEMPERATURE;
		this.percBodyHair = DEFAULT_PERCENTAGE_BODY_HAIR;
	}

	protected Mammal(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final double normalTemp, final double muchHair) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
		this.normalBodyTemp = normalTemp;
		this.percBodyHair = muchHair;
	}

	public Mammal(ArrayList<String> parameters) {
		this(
				parameters.get(0),
				Integer.parseInt(parameters.get(1)),
				Double.parseDouble(parameters.get(2)),
				Double.parseDouble(parameters.get(3)),
				Double.parseDouble(parameters.get(5)),
				Double.parseDouble(parameters.get(6))
		);
		super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
	}

	public double getNormalBodyTemp() {
		return this.normalBodyTemp;
	}

	public void setNormalBodyTemp(final double normalTemp) {
		this.normalBodyTemp = normalTemp;
	}

	public double getPercBodyHair() {
		return this.percBodyHair;
	}

	public void setPercBodyHair(final double muchHair) {
		this.percBodyHair = muchHair;
	}

	@Override
	public ArrayList<String> getClassFieldValues() {
		ArrayList<String> fieldValues = new ArrayList<>();

		fieldValues.add(String.valueOf(this.getNormalBodyTemp()));
		fieldValues.add(String.valueOf(this.getPercBodyHair()));

		return fieldValues;
	}

	@Override
	public ArrayList<String> getClassFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<>();

		fieldNames.add(FIELD_NAME_NORMAL_BODY_TEMPERATURE);
		fieldNames.add(FIELD_NAME_PERCENTAGE_BODY_HAIR);

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

		fieldInsertColumnNames.add(TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE);
		fieldInsertColumnNames.add(TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR);

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
		createNode(eventWriter, "normalBodyTemp", String.valueOf(this.getNormalBodyTemp()));
		createNode(eventWriter, "percBodyHair", String.valueOf(this.getPercBodyHair()));
	}
*/
	public void decodeFromXML(Element element) {
		super.decodeFromXML(element);
		setNormalBodyTemp(Double.valueOf(element.getElementsByTagName("normalBodyTemp").item(0).getTextContent()));
		setPercBodyHair(Double.valueOf(element.getElementsByTagName("percBodyHair").item(0).getTextContent()));
	}
}
