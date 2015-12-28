package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/

import com.example.alexh.zoosome.repositories.SQLiteZoosomeDatabase;

import org.w3c.dom.Element;

import java.util.ArrayList;

public abstract class Bird extends Animal {
    private static final boolean DEFAULT_MIGRATES = false;
    private static final int DEFAULT_AVG_FLIGHT_ALTITUDE = 0;

    public static final String NAME = "Bird";

    private static final String FIELD_NAME_MIGRATES = "Migrates";
    private static final String FIELD_NAME_AVERAGE_FLIGHT_ALTITUDE = "Average flight altitude";

    // SQLite resources
    public static final String TABLE_BIRD_NAME = NAME.toLowerCase();
    public static final String TABLE_BIRD_COL_ID = TABLE_CLASS_COL_ID;
    public static final String TABLE_BIRD_COL_MIGRATES = "migrates";
    public static final String TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE = "averageFlightAltitude";

    public static final String TABLE_BIRD_COL_ID_MODIFIERS = TABLE_CLASS_COL_ID_MODIFIERS;
    public static final String TABLE_BIRD_COL_MIGRATES_MODIFIERS = SQLiteZoosomeDatabase.BOOLEAN_MODIFIER;
    public static final String TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE_MODIFIERS = SQLiteZoosomeDatabase.INTEGER_MODIFIER;

    private boolean migrates;
    private int avgFlightAlitude;

    protected Bird() {
        super();
        this.migrates = DEFAULT_MIGRATES;
        this.avgFlightAlitude = DEFAULT_AVG_FLIGHT_ALTITUDE;
    }

    protected Bird(final String animalName, final int numberOfLegs, final double maintenanceCost,
                   final double dangerPerc, final boolean doesMigrate, final int flightAlt) {
        super(animalName, numberOfLegs, maintenanceCost, dangerPerc);
        this.migrates = doesMigrate;
        this.avgFlightAlitude = flightAlt;
    }

    public Bird(ArrayList<String> parameters) {
        this(
                parameters.get(0),
                Integer.parseInt(parameters.get(1)),
                Double.parseDouble(parameters.get(2)),
                Double.parseDouble(parameters.get(3)),
                Boolean.parseBoolean(parameters.get(5)),
                Integer.parseInt(parameters.get(6))
        );
        super.setTakenCareOf(Boolean.parseBoolean(parameters.get(4)));
    }

    public boolean getMigrates() {
        return this.migrates;
    }

    public void setMigrates(final boolean doesMigrate) {
        this.migrates = doesMigrate;
    }

    public int getAvgFlightAltitude() {
        return this.avgFlightAlitude;
    }

    public void setAvgFlightAltitude(final int flightAlt) {
        this.avgFlightAlitude = flightAlt;
    }

    @Override
    public ArrayList<String> getClassFieldValues() {
        ArrayList<String> fieldValues = new ArrayList<>();

        fieldValues.add(String.valueOf(this.getMigrates()));
        fieldValues.add(String.valueOf(this.getAvgFlightAltitude()));

        return fieldValues;
    }

    @Override
    public ArrayList<String> getClassFieldNames() {
        ArrayList<String> fieldNames = new ArrayList<>();

        fieldNames.add(FIELD_NAME_MIGRATES);
        fieldNames.add(FIELD_NAME_AVERAGE_FLIGHT_ALTITUDE);

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

        fieldInsertColumnNames.add(TABLE_BIRD_COL_MIGRATES);
        fieldInsertColumnNames.add(TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE);

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
            createNode(eventWriter, "migrates", String.valueOf(this.getMigrates()));
            createNode(eventWriter, "avgFlightAltitude", String.valueOf(this.getAvgFlightAltitude()));
        }
    */
    public void decodeFromXML(Element element) {
        super.decodeFromXML(element);
        setMigrates(Boolean.valueOf(element.getElementsByTagName("migrates").item(0).getTextContent()));
        setAvgFlightAltitude(
                Integer.valueOf(element.getElementsByTagName("avgFlightAltitude").item(0).getTextContent()));
    }
}
