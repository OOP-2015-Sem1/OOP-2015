package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/
import com.example.alexh.zoosome.repositories.SQLiteZoosomeDatabase;

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Insect extends Animal {
	private static final boolean DEFAULT_CAN_FLY = false;
	private static final boolean DEFAULT_IS_DANGEROUS = false;

	public static final String NAME = "Insect";

	private static final String FIELD_NAME_CAN_FLY = "Can fly";
	private static final String FIELD_NAME_IS_DANGEROUS = "Is Dangerous";

    // SQLite resources
    public static final String TABLE_INSECT_NAME = NAME.toLowerCase();
    public static final String TABLE_INSECT_COL_ID = TABLE_CLASS_COL_ID;
    public static final String TABLE_INSECT_COL_CAN_FLY = "canFly";
    public static final String TABLE_INSECT_COL_IS_DANGEROUS = "isDangerous";

    public static final String TABLE_INSECT_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
    public static final String TABLE_INSECT_COL_CAN_FLY_MODIFIERS = SQLiteZoosomeDatabase.BOOLEAN_MODIFIER;
    public static final String TABLE_INSECT_COL_IS_DANGEROUS_MODIFIERS = SQLiteZoosomeDatabase.BOOLEAN_MODIFIER;

    private boolean canFly;
	private boolean isDangerous;

	protected Insect() {
		super();
		this.canFly = DEFAULT_CAN_FLY;
		this.isDangerous = DEFAULT_IS_DANGEROUS;
	}

	protected Insect(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final boolean flyer, final boolean dangerous) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
		this.canFly = flyer;
		this.isDangerous = dangerous;
	}

	public Insect(ArrayList<String> parameters) {
        this(
                parameters.get(0),
                Integer.parseInt(parameters.get(1)),
                Double.parseDouble(parameters.get(2)),
                Double.parseDouble(parameters.get(3)),
                Boolean.parseBoolean(parameters.get(5)),
                Boolean.parseBoolean(parameters.get(6))
        );
        super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
    }

	public boolean getCanFly() {
		return this.canFly;
	}

	public void setCanFly(final boolean flyer) {
		this.canFly = flyer;
	}

	public boolean getIsDangerous() {
		return this.isDangerous;
	}

	public void setIsDangerous(final boolean dangerous) {
		this.isDangerous = dangerous;
	}

	@Override
	public ArrayList<String> getClassFieldValues() {
		ArrayList<String> fieldValues = new ArrayList<>();

		fieldValues.add(String.valueOf(this.getCanFly()));
		fieldValues.add(String.valueOf(this.getIsDangerous()));

		return fieldValues;
	}

	@Override
	public ArrayList<String> getClassFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<>();

		fieldNames.add(FIELD_NAME_CAN_FLY);
		fieldNames.add(FIELD_NAME_IS_DANGEROUS);

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

		fieldInsertColumnNames.add(TABLE_INSECT_COL_CAN_FLY);
		fieldInsertColumnNames.add(TABLE_INSECT_COL_IS_DANGEROUS);

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
		createNode(eventWriter, "canFly", String.valueOf(this.getCanFly()));
		createNode(eventWriter, "isDangerous", String.valueOf(this.getIsDangerous()));
	}
*/
	public void decodeFromXML(Element element) {
		super.decodeFromXML(element);
		setCanFly(Boolean.valueOf(element.getElementsByTagName("canFly").item(0).getTextContent()));
		setIsDangerous(Boolean.valueOf(element.getElementsByTagName("isDangerous").item(0).getTextContent()));
	}
}
