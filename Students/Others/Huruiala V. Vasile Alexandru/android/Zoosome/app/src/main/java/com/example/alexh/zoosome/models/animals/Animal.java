package com.example.alexh.zoosome.models.animals;
/*
import static com.example.alexh.zoosome.repositories.EntityRepository.createNode;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
*/

import org.w3c.dom.Element;

import com.example.alexh.zoosome.models.interfaces.XML_Parsable;

import java.util.ArrayList;

public abstract class Animal implements Killer, XML_Parsable {
    private static final String DEFAULT_NAME = null;
    private static final int DEFAULT_NUMBER_OF_LEGS = 0;
    private static final double DEFAULT_MAINTENANCE_COST_MIN = 0.1D;
    private static final double DEFAULT_MAINTENANCE_COST_MAX = 8.0D;
    private static final double DEFAULT_DANGER_PERCENTAGE_MIN = 0.0D;
    private static final double DEFAULT_DANGER_PERCENTAGE_MAX = 1.0D;
    private static final boolean DEFAULT_TAKEN_CARE_OF = false;

    private static final String FIELD_NAME_NAME = "Name";
    private static final String FIELD_NAME_NUMBER_OF_LEGS = "Number of legs";
    private static final String FIELD_NAME_MAINTENANCE_COST = "Maintenance cost";
    private static final String FIELD_NAME_DANGER_PERC = "Danger percentage";
    private static final String FIELD_NAME_TAKEN_CARE_OF = "Taken care of";

    // SQLite resources
    // Animal table
    public static final String TABLE_ANIMAL_NAME = "animal_table";
    public static final String TABLE_ANIMAL_COL_ID = "id_animal";
    public static final String TABLE_ANIMAL_COL_NAME = "name";
    public static final String TABLE_ANIMAL_COL_NUMBER_OF_LEGS = "noOfLegs";
    public static final String TABLE_ANIMAL_COL_MAINTENANCE_COST = "maintenanceCost";
    public static final String TABLE_ANIMAL_COL_DANGER_PERCENTAGE = "dangerPercentage";
    public static final String TABLE_ANIMAL_COL_TAKEN_CARE_OF = "takenCareOf";

    public static final String TABLE_ANIMAL_COL_ID_MODIFIERS = "INTEGER PRIMARY KEY";
    public static final String TABLE_ANIMAL_COL_NAME_MODIFIERS = "TEXT";
    public static final String TABLE_ANIMAL_COL_NUMBER_OF_LEGS_MODIFIERS = "INTEGER";
    public static final String TABLE_ANIMAL_COL_MAINTENANCE_COST_MODIFIERS = "DOUBLE";
    public static final String TABLE_ANIMAL_COL_DANGER_PERCENTAGE_MODIFIERS = "DOUBLE";
    public static final String TABLE_ANIMAL_COL_TAKEN_CARE_OF_MODIFIERS = "VARCHAR(5)";

    // These are used to make sure every subclass table has the same name for common columns
    // Class table
    public static final String TABLE_CLASS_COL_ID = "id_class";

    public static final String TABLE_CLASS_COL_ID_MODIFIERS = "INTEGER PRIMARY KEY";

    // Species table
    public static final String TABLE_SPECIES_COL_ID = "id_species";

    public static final String TABLE_SPECIES_COL_ID_MODIFIERS = "INTEGER PRIMARY KEY";

    private String name;
    private int noOfLegs;
    private double maintenanceCost;
    private double dangerPerc;

    private boolean takenCareOf;

    protected Animal() {
        this.name = DEFAULT_NAME;
        this.noOfLegs = DEFAULT_NUMBER_OF_LEGS;
        this.maintenanceCost = DEFAULT_MAINTENANCE_COST_MIN;
        this.dangerPerc = DEFAULT_DANGER_PERCENTAGE_MIN;
        this.takenCareOf = DEFAULT_TAKEN_CARE_OF;
    }

    protected Animal(final String animalName, final int numberOfLegs, final double maintenaceCosts,
                     final double dangerPercentage) {
        this.name = animalName;
        this.noOfLegs = numberOfLegs;

        if (maintenaceCosts < DEFAULT_MAINTENANCE_COST_MIN) {
            this.maintenanceCost = DEFAULT_MAINTENANCE_COST_MIN;
        } else if (maintenaceCosts > DEFAULT_MAINTENANCE_COST_MAX) {
            this.maintenanceCost = DEFAULT_MAINTENANCE_COST_MAX;
        } else {
            this.maintenanceCost = maintenaceCosts;
        }

        if (dangerPercentage < DEFAULT_DANGER_PERCENTAGE_MIN) {
            this.dangerPerc = DEFAULT_DANGER_PERCENTAGE_MIN;
        } else if (dangerPercentage > DEFAULT_DANGER_PERCENTAGE_MAX) {
            this.dangerPerc = DEFAULT_DANGER_PERCENTAGE_MAX;
        } else {
            this.dangerPerc = dangerPercentage;
        }

        this.takenCareOf = DEFAULT_TAKEN_CARE_OF;
    }

    public boolean kill() {
        return (Math.random() < (this.dangerPerc + getPredisposition()));
    }

    public double getPredisposition() {
        return 0.0D;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String animalName) {
        this.name = animalName;
    }

    public int getNoOfLegs() {
        return this.noOfLegs;
    }

    public void setNoOfLegs(final int numberOfLegs) {
        this.noOfLegs = numberOfLegs;
    }

    public double getMaintenanceCost() {
        return this.maintenanceCost;
    }

    public void setMaintenenceCost(double newMaintenanceCost) {
        this.maintenanceCost = newMaintenanceCost;
    }

    public double getDangerPerc() {
        return this.dangerPerc;
    }

    public void setDangerPerc(double newDangerPerc) {
        this.dangerPerc = newDangerPerc;
    }

    public boolean getTakenCareOf() {
        return this.takenCareOf;
    }

    public void setTakenCareOf(boolean care) {
        this.takenCareOf = care;
    }

    public ArrayList<String> getAnimalFieldNames() {
        ArrayList<String> fieldNames = new ArrayList<>();

        fieldNames.add(FIELD_NAME_NAME);
        fieldNames.add(FIELD_NAME_NUMBER_OF_LEGS);
        fieldNames.add(FIELD_NAME_MAINTENANCE_COST);
        fieldNames.add(FIELD_NAME_DANGER_PERC);
        fieldNames.add(FIELD_NAME_TAKEN_CARE_OF);

        return fieldNames;
    }

    public abstract ArrayList<String> getClassFieldNames();

    public ArrayList<String> getAnimalFieldValues() {
        ArrayList<String> fieldValues = new ArrayList<>();

        fieldValues.add(this.getName());
        fieldValues.add(String.valueOf(this.getNoOfLegs()));
        fieldValues.add(String.valueOf(this.getMaintenanceCost()));
        fieldValues.add(String.valueOf(this.getDangerPerc()));
        fieldValues.add(String.valueOf(this.getTakenCareOf()));

        return fieldValues;
    }

    public abstract ArrayList<String> getClassFieldValues();

    public ArrayList[] getAnimalFieldNamesAndValues() {
        ArrayList[] namesAndValues = new ArrayList[2];

        namesAndValues[0] = this.getAnimalFieldNames();
        namesAndValues[1] = this.getAnimalFieldValues();

        return namesAndValues;
    }

    public abstract ArrayList[] getClassFieldNamesAndValues();

    public ArrayList<String> getAnimalFieldInsertColumnNames() {
        ArrayList<String> fieldColumnNames = new ArrayList<>();

        fieldColumnNames.add(TABLE_ANIMAL_COL_NAME);
        fieldColumnNames.add(TABLE_ANIMAL_COL_NUMBER_OF_LEGS);
        fieldColumnNames.add(TABLE_ANIMAL_COL_MAINTENANCE_COST);
        fieldColumnNames.add(TABLE_ANIMAL_COL_DANGER_PERCENTAGE);
        fieldColumnNames.add(TABLE_ANIMAL_COL_TAKEN_CARE_OF);

        return fieldColumnNames;
    }

    public abstract ArrayList<String> getClassFieldInsertColumnNames();

    public ArrayList[] getAnimalFieldInsertColumnNamesAndValues() {
        ArrayList[] namesAndValues = new ArrayList[2];

        namesAndValues[0] = this.getAnimalFieldInsertColumnNames();
        namesAndValues[1] = this.getAnimalFieldValues();

        return namesAndValues;
    }

    public abstract ArrayList[] getClassFieldInsertColumnNamesAndValues();

    public ArrayList<String> getAllFieldValues() {
        ArrayList<String> fieldValues = new ArrayList<>();

        fieldValues.addAll(this.getAnimalFieldValues());
        fieldValues.addAll(this.getClassFieldValues());

        return fieldValues;
    }

    public ArrayList<String> getAllFieldNames() {
        ArrayList<String> fieldNames = new ArrayList<>();

        fieldNames.addAll(this.getAnimalFieldNames());
        fieldNames.addAll(this.getClassFieldNames());

        return fieldNames;
    }

    public ArrayList[] getAllFieldNamesAndValues() {
        ArrayList[] fieldNamesAndValues = new ArrayList[2];

        fieldNamesAndValues[0] = this.getAllFieldNames();
        fieldNamesAndValues[1] = this.getAllFieldValues();

        return fieldNamesAndValues;
    }

    public ArrayList<String> getAllFieldInsertColumnNames() {
        ArrayList<String> fieldInsertColumnNames = new ArrayList<>();

        fieldInsertColumnNames.addAll(this.getAnimalFieldInsertColumnNames());
        fieldInsertColumnNames.addAll(this.getClassFieldInsertColumnNames());

        return fieldInsertColumnNames;
    }

    public ArrayList[] getAllFieldInsertColumnNamesAndValues() {
        ArrayList[] fieldInsertColumnNamesAndValues = new ArrayList[2];

        fieldInsertColumnNamesAndValues[0] = this.getAllFieldInsertColumnNames();
        fieldInsertColumnNamesAndValues[1] = this.getAllFieldValues();

        return fieldInsertColumnNamesAndValues;
    }

    /*
	public void encodeToXML(XMLEventWriter eventWriter) throws XMLStreamException {
		createNode(eventWriter, "noOfLegs", String.valueOf(this.noOfLegs));
		createNode(eventWriter, "name", String.valueOf(this.name));
		createNode(eventWriter, "maintenanceCost", String.valueOf(this.maintenanceCost));
		createNode(eventWriter, "dangerPerc", String.valueOf(this.dangerPerc));
		createNode(eventWriter, "takenCareOf", String.valueOf(this.takenCareOf));
	}
    */
	public void decodeFromXML(Element element) {
		setNoOfLegs(Integer.valueOf(element.getElementsByTagName("noOfLegs").item(0).getTextContent()));
		setName(element.getElementsByTagName("name").item(0).getTextContent());
		setMaintenenceCost(Double.valueOf(element.getElementsByTagName("maintenanceCost").item(0).getTextContent()));
		setDangerPerc(Double.valueOf(element.getElementsByTagName("dangerPerc").item(0).getTextContent()));
		setTakenCareOf(Boolean.valueOf(element.getElementsByTagName("takenCareOf").item(0).getTextContent()));
	}

}
