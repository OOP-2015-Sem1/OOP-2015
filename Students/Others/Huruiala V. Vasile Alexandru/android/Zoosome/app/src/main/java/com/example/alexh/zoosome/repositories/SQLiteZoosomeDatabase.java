package com.example.alexh.zoosome.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.models.animals.Aquatic;
import com.example.alexh.zoosome.models.animals.Bird;
import com.example.alexh.zoosome.models.animals.Insect;
import com.example.alexh.zoosome.models.animals.Mammal;
import com.example.alexh.zoosome.models.animals.Reptile;
import com.example.alexh.zoosome.models.animals.Siege;
import com.example.alexh.zoosome.services.factories.Constants;

/**
 * Created by alexh on 20.11.2015.
 */
public class SQLiteZoosomeDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Zoosome.db";

    public SQLiteZoosomeDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Animal table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s DOUBLE, %s DOUBLE, %s VARCHAR(5))",
                Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID,
                Constants.Animals.TABLE_ANIMAL_COL_NAME, Constants.Animals.TABLE_ANIMAL_COL_NO_OF_LEGS,
                Constants.Animals.TABLE_ANIMAL_COL_MAINTENANCE_COST, Constants.Animals.TABLE_ANIMAL_COL_DANGER_PERCENTAGE,
                Constants.Animals.TABLE_ANIMAL_COL_TAKEN_CARE_OF));

        // Mammal table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s DOUBLE, %s DOUBLE, FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Mammal.TABLE_MAMMAL, Constants.Animals.Mammal.TABLE_MAMMAL_COL_ID, Constants.Animals.Mammal.TABLE_MAMMAL_COL_SPECIES,
                Constants.Animals.Mammal.TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE, Constants.Animals.Mammal.TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR,
                Constants.Animals.Mammal.TABLE_MAMMAL_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

        // Reptile table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s VARCHAR(5), FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Reptile.TABLE_REPTILE, Constants.Animals.Reptile.TABLE_REPTILE_COL_ID, Constants.Animals.Reptile.TABLE_REPTILE_COL_SPECIES,
                Constants.Animals.Reptile.TABLE_REPTILE_COL_LAYS_EGGS,
                Constants.Animals.Reptile.TABLE_REPTILE_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

        // Bird table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s VARCHAR(5), %s INTEGER, FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Bird.TABLE_BIRD, Constants.Animals.Bird.TABLE_BIRD_COL_ID, Constants.Animals.Bird.TABLE_BIRD_COL_SPECIES,
                Constants.Animals.Bird.TABLE_BIRD_COL_MIGRATES, Constants.Animals.Bird.TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE,
                Constants.Animals.Bird.TABLE_BIRD_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

        // Aquatic table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s VARCHAR(10), FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Aquatic.TABLE_AQUATIC, Constants.Animals.Aquatic.TABLE_AQUATIC_COL_ID, Constants.Animals.Aquatic.TABLE_AQUATIC_COL_SPECIES,
                Constants.Animals.Aquatic.TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH, Constants.Animals.Aquatic.TABLE_AQUATIC_COL_WATER_TYPE,
                Constants.Animals.Aquatic.TABLE_AQUATIC_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

        // Insect table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s VARCHAR(5), %s VARCHAR(5), FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Insect.TABLE_INSECT, Constants.Animals.Insect.TABLE_INSECT_COL_ID, Constants.Animals.Insect.TABLE_INSECT_COL_SPECIES,
                Constants.Animals.Insect.TABLE_INSECT_COL_CAN_FLY, Constants.Animals.Insect.TABLE_INSECT_COL_IS_DANGEROUS,
                Constants.Animals.Insect.TABLE_INSECT_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

        // Siege table
        db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s VARCHAR(5), FOREIGN KEY (%s) REFERENCES %s (%s))",
                Constants.Animals.Siege.TABLE_SIEGE, Constants.Animals.Siege.TABLE_SIEGE_COL_ID, Constants.Animals.Siege.TABLE_SIEGE_COL_SPECIES,
                Constants.Animals.Siege.TABLE_SIEGE_COL_RANGE, Constants.Animals.Siege.TABLE_SIEGE_COL_IS_MOBILE,
                Constants.Animals.Siege.TABLE_SIEGE_COL_ID, Constants.Animals.TABLE_ANIMAL, Constants.Animals.TABLE_ANIMAL_COL_ID));

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.TABLE_ANIMAL);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Mammal.TABLE_MAMMAL);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Reptile.TABLE_REPTILE);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Bird.TABLE_BIRD);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Aquatic.TABLE_AQUATIC);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Insect.TABLE_INSECT);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.Siege.TABLE_SIEGE);
        onCreate(db);
    }

    public boolean insertData(Animal animal) {
        SQLiteDatabase db = this.getWritableDatabase();

        int classIndex = Constants.indexOfClass(animal);
        int speciesIndex = Constants.indexOfSpecies(animal);

        // Adding data to animal_table
        ContentValues contentValuesAnimal = new ContentValues();
        contentValuesAnimal.put(Constants.Animals.TABLE_ANIMAL_COL_NAME, animal.getName());
        contentValuesAnimal.put(Constants.Animals.TABLE_ANIMAL_COL_NO_OF_LEGS, animal.getNoOfLegs());
        contentValuesAnimal.put(Constants.Animals.TABLE_ANIMAL_COL_MAINTENANCE_COST, animal.getMaintenanceCost());
        contentValuesAnimal.put(Constants.Animals.TABLE_ANIMAL_COL_DANGER_PERCENTAGE, animal.getDangerPerc());
        contentValuesAnimal.put(Constants.Animals.TABLE_ANIMAL_COL_TAKEN_CARE_OF, animal.getTakenCareOf());
        db.insert(Constants.Animals.TABLE_ANIMAL, null, contentValuesAnimal);

        // Adding data to a class table
        ContentValues contentValuesClass = new ContentValues();
        // Select the table name
        String tableName = Constants.Animals.TABLE_NAMES[classIndex];
        // Select the class table key
        contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][1], Constants.nameOfSpecies(animal));
        // Select the first field key
        switch (classIndex) {
            case 0:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Mammal) animal).getNormalBodyTemp());
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][3], ((Mammal) animal).getPercBodyHair());
                break;
            case 1:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Reptile) animal).getLaysEggs());
                break;
            case 2:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Bird) animal).getMigrates());
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][3], ((Bird) animal).getAvgFlightAltitude());
                break;
            case 3:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Aquatic) animal).getAvgSwimDepth());
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][3], ((Aquatic) animal).getWaterType().toString());
                break;
            case 4:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Insect) animal).getCanFly());
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][3], ((Insect) animal).getIsDangerous());
                break;
            case 5:
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][2], ((Siege) animal).getRange());
                contentValuesClass.put(Constants.Animals.TABLE_COLS[classIndex][3], ((Siege) animal).getIsMobile());
                break;
        }
        // Add the data to the table
        db.insert(tableName, null, contentValuesClass);




        long result = db.insert(TABLE_NAME, null, contentValuesAnimal);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] s = {id};

        db.delete(Constants.Animals.TABLE_ANIMAL, "ID = ?", s);
        db.delete(Constants.Animals.Mammal.TABLE_MAMMAL, "ID = ?", s);
        db.delete(Constants.Animals.Reptile.TABLE_REPTILE, "ID = ?", s);
        db.delete(Constants.Animals.Bird.TABLE_BIRD, "ID = ?", s);
        db.delete(Constants.Animals.Aquatic.TABLE_AQUATIC, "ID = ?", s);
        db.delete(Constants.Animals.Insect.TABLE_INSECT, "ID = ?", s);
        db.delete(Constants.Animals.Siege.TABLE_SIEGE, "ID = ?", s);
    }
}
