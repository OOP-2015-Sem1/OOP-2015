package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/
import com.example.alexh.zoosome.repositories.SQLiteZoosomeDatabase;

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Reptile extends Animal {
	private static final boolean DEFAULT_LAYS_EGGS = false;

	public static final String NAME = "Reptile";

	private static final String FIELD_NAME_LAYS_EGGS = "Lays eggs";

    // SQLite resources
	public static final String TABLE_REPTILE_NAME = NAME.toLowerCase();
	public static final String TABLE_REPTILE_COL_ID = TABLE_CLASS_COL_ID;
	public static final String TABLE_REPTILE_COL_LAYS_EGGS = "laysEggs";

    public static final String TABLE_REPTILE_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
	public static final String TABLE_REPTILE_COL_LAYS_EGGS_MODIFIERS = SQLiteZoosomeDatabase.BOOLEAN_MODIFIER;

	private boolean laysEggs;

	protected Reptile() {
		super();
		this.laysEggs = DEFAULT_LAYS_EGGS;
	}

	protected Reptile(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final boolean hasEggs) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
		this.laysEggs = hasEggs;
	}

	public Reptile(ArrayList<String> parameters) {
        this(
                parameters.get(0),
                Integer.parseInt(parameters.get(1)),
                Double.parseDouble(parameters.get(2)),
                Double.parseDouble(parameters.get(3)),
                Boolean.parseBoolean(parameters.get(5))
        );
        super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
    }

	public boolean getLaysEggs() {
		return this.laysEggs;
	}

	public void setLaysEggs(final boolean hasEggs) {
		this.laysEggs = hasEggs;
	}

	@Override
	public ArrayList<String> getClassFieldValues() {
		ArrayList<String> fieldValues = new ArrayList<>();

		fieldValues.add(String.valueOf(this.getLaysEggs()));

		return fieldValues;
	}

	@Override
	public ArrayList<String> getClassFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<>();

		fieldNames.add(FIELD_NAME_LAYS_EGGS);

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

		fieldInsertColumnNames.add(TABLE_REPTILE_COL_LAYS_EGGS);

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
		createNode(eventWriter, "laysEggs", String.valueOf(this.getLaysEggs()));
	}
*/
	public void decodeFromXML(Element element) {
		super.decodeFromXML(element);
		setLaysEggs(Boolean.valueOf(element.getElementsByTagName("laysEggs").item(0).getTextContent()));
	}
}
