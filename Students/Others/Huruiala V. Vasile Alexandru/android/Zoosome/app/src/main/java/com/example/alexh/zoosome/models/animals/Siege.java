package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/
import com.example.alexh.zoosome.repositories.SQLiteZoosomeDatabase;

import org.w3c.dom.Element;

import java.util.ArrayList;

//The secret 6th class the pdf doesn't tell you about
public abstract class Siege extends Animal {
	private static final int DEFAULT_RANGE = 0;
	private static final boolean DEFAULT_IS_MOBILE = false;

	public static final String NAME = "Siege";

	private static final String FIELD_NAME_RANGE = "Migrates";
	private static final String FIELD_NAME_IS_MOBILE = "Average flight altitude";

    // SQLite resources
    public static final String TABLE_SIEGE_NAME = NAME.toLowerCase();
    public static final String TABLE_SIEGE_COL_ID = TABLE_CLASS_COL_ID;
    public static final String TABLE_SIEGE_COL_RANGE = "range";
    public static final String TABLE_SIEGE_COL_IS_MOBILE = "isMobile";

    public static final String TABLE_SIEGE_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
    public static final String TABLE_SIEGE_COL_RANGE_MODIFIERS = SQLiteZoosomeDatabase.INTEGER_MODIFIER;
    public static final String TABLE_SIEGE_COL_IS_MOBILE_MODIFIERS = SQLiteZoosomeDatabase.BOOLEAN_MODIFIER;

    private int range;
	private boolean isMobile;

	protected Siege() {
		super();
		this.range = DEFAULT_RANGE;
		this.isMobile = DEFAULT_IS_MOBILE;
	}

	protected Siege(final String animalName, final int numberOfLegs, final double maintenanceCost,
			final double dangerPerc, final int firingRange, final boolean mobile) {
		super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
		this.range = firingRange;
		this.isMobile = mobile;
	}

	public Siege(ArrayList<String> parameters) {
        this(
                parameters.get(0),
                Integer.parseInt(parameters.get(1)),
                Double.parseDouble(parameters.get(2)),
                Double.parseDouble(parameters.get(3)),
                Integer.parseInt(parameters.get(5)),
                Boolean.parseBoolean(parameters.get(6))
        );
        super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
    }

	public int getRange() {
		return this.range;
	}

	public void setRange(final int firingRange) {
		this.range = firingRange;
	}

	public boolean getIsMobile() {
		return this.isMobile;
	}

	public void setIsMobile(final boolean mobile) {
		this.isMobile = mobile;
	}

	@Override
	public ArrayList<String> getClassFieldValues() {
		ArrayList<String> fieldValues = new ArrayList<>();

		fieldValues.add(String.valueOf(this.getRange()));
		fieldValues.add(String.valueOf(this.getIsMobile()));

		return fieldValues;
	}

	@Override
	public ArrayList<String> getClassFieldNames() {
		ArrayList<String> fieldNames = new ArrayList<>();

		fieldNames.add(FIELD_NAME_RANGE);
		fieldNames.add(FIELD_NAME_IS_MOBILE);

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

		fieldInsertColumnNames.add(TABLE_SIEGE_COL_RANGE);
		fieldInsertColumnNames.add(TABLE_SIEGE_COL_IS_MOBILE);

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
		createNode(eventWriter, "range", String.valueOf(this.getRange()));
		createNode(eventWriter, "isMobile", String.valueOf(this.getIsMobile()));
	}
*/
	public void decodeFromXML(Element element) {
		super.decodeFromXML(element);
		setRange(Integer.valueOf(element.getElementsByTagName("range").item(0).getTextContent()));
		setIsMobile(Boolean.valueOf(element.getElementsByTagName("isMobile").item(0).getTextContent()));
	}
}
