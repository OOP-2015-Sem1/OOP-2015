package com.example.alexh.zoosome.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.services.factories.Constants;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class SQLiteZoosomeDatabase extends SQLiteOpenHelper {

    public static final String INTEGER_MODIFIER = "INTEGER";
    public static final String DOUBLE_MODIFIER = "DOUBLE";
    public static final String BOOLEAN_MODIFIER = "VARCHAR(5)";
    public static final String WATERTYPE_MODIFIER = "VARCHAR(10)";
    public static final String STRING_MODIFIER = "TEXT";
    public static final String PRIMARY_KEY_MODIFIER = "PRIMARY KEY";
    public static final String AUTOINCREMENT_MODIFIER = "AUTOINCREMENT";
    public static final String SPACER = " ";

    private static final String DATABASE_NAME = "Zoosome.db";

    public SQLiteZoosomeDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);

        // Drop and rebuild the tables if any expected table is missing
        try {
            this.checkAllTables();
        } catch (Exception e) {
            try {
                dropAllTables(getWritableDatabase());
            } catch (Exception ignored) {
            }
            onCreate(getWritableDatabase());
        }
    }

    private void checkAllTables() throws Exception {
        if (this.getNumberOfAnimals() == this.readAllAnimals().size()) {
            Log.d("SQL", "WUT?");
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SQL", "onCreate");
        // Animal table
        StringBuilder animalTableQuery = new StringBuilder();

        animalTableQuery.append(String.format("CREATE TABLE %s (", Constants.Animals.TABLE_ANIMAL_NAME));

        for (int indexTableAnimalColumn = 0; indexTableAnimalColumn < Constants.Animals.TABLE_ANIMAL_COLS.length; indexTableAnimalColumn++) {
            animalTableQuery.append(String.format("%s %s",
                    Constants.Animals.TABLE_ANIMAL_COLS[indexTableAnimalColumn],
                    Constants.Animals.TABLE_ANIMAL_COLS_MODIFIERS[indexTableAnimalColumn]));

            if (indexTableAnimalColumn < Constants.Animals.TABLE_CLASS_COLS.length - 1) {
                animalTableQuery.append(", ");
            } else {
                animalTableQuery.append(")");
            }
        }

        db.execSQL(animalTableQuery.toString());

        // Class tables
        for (int indexClassTable = 0; indexClassTable < Constants.Animals.TABLE_CLASS_NAMES.length; indexClassTable++) {
            StringBuilder classTableQuery = new StringBuilder();
            classTableQuery.append(String.format("CREATE TABLE %s (", Constants.Animals.TABLE_CLASS_NAMES[indexClassTable]));

            for (int indexClassTableColumn = 0; indexClassTableColumn < Constants.Animals.TABLE_CLASS_COLS[indexClassTable].length; indexClassTableColumn++) {
                classTableQuery.append(String.format("%s %s, ",
                        Constants.Animals.TABLE_CLASS_COLS[indexClassTable][indexClassTableColumn],
                        Constants.Animals.TABLE_CLASS_COLS_MODIFIERS[indexClassTable][indexClassTableColumn]));
            }

            classTableQuery.append(String.format("FOREIGN KEY (%s) REFERENCES %s (%s))",
                    Constants.Animals.TABLE_CLASS_COL_ID,
                    Constants.Animals.TABLE_ANIMAL_NAME,
                    Constants.Animals.TABLE_ANIMAL_COL_ID));

            db.execSQL(classTableQuery.toString());
        }

        // Species tables
        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            for (int indexSpecies = 0; indexSpecies < Constants.Animals.SPECIES_NAME[indexClass].length; indexSpecies++) {
                db.execSQL(String.format("CREATE TABLE %s (%s %s, FOREIGN KEY (%s) REFERENCES %s (%s))",
                        Constants.Animals.getTableSpeciesName(indexClass, indexSpecies),
                        Constants.Animals.TABLE_SPECIES_COL_ID,
                        Constants.Animals.TABLE_SPECIES_COL_ID_MODIFIERS,
                        Constants.Animals.TABLE_SPECIES_COL_ID,
                        Constants.Animals.TABLE_CLASS_NAMES[indexClass],
                        Constants.Animals.TABLE_CLASS_COL_ID));
            }
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.dropAllTables(db);
        onCreate(db);
    }

    private void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.TABLE_ANIMAL_NAME);
        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.TABLE_CLASS_NAMES[indexClass]);
        }
        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            for (int indexSpecies = 0; indexSpecies < Constants.Animals.SPECIES_NAME[indexClass].length; indexSpecies++) {
                db.execSQL("DROP TABLE IF EXISTS " + Constants.Animals.getTableSpeciesName(indexClass, indexSpecies));
            }
        }
    }

    public void insertAnimal(Animal animal) {
        SQLiteDatabase db = this.getWritableDatabase();

        int classIndex = Constants.Animals.indexOfClass(animal);
        int speciesIndex = Constants.Animals.indexOfSpecies(animal);

        // Adding data to animal table
        ContentValues contentValuesAnimal = new ContentValues();

        ArrayList[] fieldAnimalColumnNamesAndValues = animal.getAnimalFieldInsertColumnNamesAndValues();
        for (int index = 0; index < fieldAnimalColumnNamesAndValues[0].size(); index++) {
            contentValuesAnimal.put((String) fieldAnimalColumnNamesAndValues[0].get(index), (String) fieldAnimalColumnNamesAndValues[1].get(index));
        }

        db.insert(Constants.Animals.TABLE_ANIMAL_NAME, null, contentValuesAnimal);

        // Getting the next id
        String stringID = String.valueOf(1);
        try {
            Cursor cursor = db.rawQuery(String.format("SELECT MAX(%s) FROM %s",
                    Constants.Animals.TABLE_ANIMAL_COL_ID,
                    Constants.Animals.TABLE_ANIMAL_NAME), null);
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                stringID = String.valueOf(Integer.parseInt(cursor.getString(0)));
            }
            cursor.close();
        } catch (Exception e) {
            stringID = String.valueOf(1);
        }


        // Adding data to a class table
        ContentValues contentValuesClass = new ContentValues();
        contentValuesClass.put(Constants.Animals.TABLE_CLASS_COL_ID, String.valueOf(stringID));

        ArrayList[] fieldSpeciesColumnNamesAndValues = animal.getClassFieldInsertColumnNamesAndValues();
        for (int index = 0; index < fieldSpeciesColumnNamesAndValues[0].size(); index++) {
            contentValuesClass.put((String) fieldSpeciesColumnNamesAndValues[0].get(index), (String) fieldSpeciesColumnNamesAndValues[1].get(index));
        }

        db.insert(Constants.Animals.TABLE_CLASS_NAMES[classIndex], null, contentValuesClass);


        // Adding data to a species table
        ContentValues contentValuesSpecies = new ContentValues();
        contentValuesSpecies.put(Constants.Animals.TABLE_SPECIES_COL_ID, String.valueOf(stringID));

        db.insert(Constants.Animals.getTableSpeciesName(classIndex, speciesIndex), null, contentValuesSpecies);
    }

    public void insertAnimals(ArrayList<Animal> animals) {
        for (Animal animal : animals) {
            insertAnimal(animal);
        }
    }

    public int getNumberOfAnimals() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(" + Constants.Animals.TABLE_ANIMAL_COL_ID + ") FROM " + Constants.Animals.TABLE_ANIMAL_NAME, null);
        int animalCount = 0;

        Log.d("SQL_na", "" + cursor.getCount());
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            animalCount = Integer.parseInt(cursor.getString(0));
        }
        cursor.close();

        return animalCount;
    }

    /**
     * Returns a Cursor with all of the animals of the specified species
     */
    public Cursor getSpecies(int indexClass, int indexSpecies) {
        SQLiteDatabase db = this.getWritableDatabase();

        StringBuilder speciesQuery = new StringBuilder();
        // Select part
        speciesQuery.append("SELECT ");
        // Animal columns
        for (int indexAnimalColumn = 0; indexAnimalColumn < Constants.Animals.TABLE_ANIMAL_COLS.length; indexAnimalColumn++) {
            speciesQuery.append(String.format("%s.%s, ", Constants.Animals.TABLE_ANIMAL_NAME, Constants.Animals.TABLE_ANIMAL_COLS[indexAnimalColumn]));
        }
        // Class columns
        for (int indexClassColumn = 0; indexClassColumn < Constants.Animals.TABLE_CLASS_COLS[indexClass].length; indexClassColumn++) {
            speciesQuery.append(String.format("%s.%s", Constants.Animals.TABLE_CLASS_NAMES[indexClass], Constants.Animals.TABLE_CLASS_COLS[indexClass][indexClassColumn]));
            if (indexClassColumn < Constants.Animals.TABLE_CLASS_COLS[indexClass].length - 1) {
                speciesQuery.append(", ");
            } else {
                speciesQuery.append(" ");
            }
        }
        // From part
        speciesQuery.append(String.format("FROM %s JOIN %s ON %s.%s = %s.%s JOIN %s ON %s.%s = %s.%s",
                Constants.Animals.TABLE_ANIMAL_NAME,
                Constants.Animals.TABLE_CLASS_NAMES[indexClass],
                Constants.Animals.TABLE_ANIMAL_NAME,
                Constants.Animals.TABLE_ANIMAL_COL_ID,
                Constants.Animals.TABLE_CLASS_NAMES[indexClass],
                Constants.Animals.TABLE_CLASS_COL_ID,
                Constants.Animals.getTableSpeciesName(indexClass, indexSpecies),
                Constants.Animals.TABLE_ANIMAL_NAME,
                Constants.Animals.TABLE_ANIMAL_COL_ID,
                Constants.Animals.getTableSpeciesName(indexClass, indexSpecies),
                Constants.Animals.TABLE_SPECIES_COL_ID));

        return db.rawQuery(speciesQuery.toString(), null);
    }

    public ArrayList<Animal> readAllAnimals() {
        ArrayList<Animal> readAnimals = new ArrayList<>();

        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            for (int indexSpecies = 0; indexSpecies < Constants.Animals.SPECIES_NAME[indexClass].length; indexSpecies++) {
                Cursor species = this.getSpecies(indexClass, indexSpecies);

                while (species.moveToNext()) {
                    ArrayList<String> constructorParameters = new ArrayList<>();
                    // Starts at 1 to omit the id (which is 0)
                    for (int indexAnimalColumn = 1; indexAnimalColumn < Constants.Animals.TABLE_ANIMAL_COLS.length; indexAnimalColumn++) {
                        constructorParameters.add(species.getString(indexAnimalColumn));
                        //Log.d("SQL_an", indexAnimalColumn + ": " + species.getString(indexAnimalColumn));
                    }
                    // Same as the loop above
                    for (int indexClassColumn = 1; indexClassColumn < Constants.Animals.TABLE_CLASS_COLS[indexClass].length; indexClassColumn++) {
                        constructorParameters.add(species.getString(indexClassColumn + Constants.Animals.TABLE_CLASS_COLS.length));
                        //Log.d("SQL_an", (indexClassColumn + Constants.Animals.TABLE_CLASS_COLS.length) +  ": " + species.getString(indexClassColumn + Constants.Animals.TABLE_CLASS_COLS.length));
                    }

                    try {
                        readAnimals.add((Animal) Constants.Animals.ANIMAL_SPECIES_CLASSES[indexClass][indexSpecies].getConstructor(ArrayList.class).newInstance(constructorParameters));
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }

                }

                species.close();
            }
        }

        return readAnimals;
    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] s = {id};

        // Animal table
        db.delete(Animal.TABLE_ANIMAL_NAME, "ID = ?", s);

        // Class tables
        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            db.delete(Constants.Animals.TABLE_CLASS_NAMES[indexClass], "ID = ?", s);
        }

        // Species tables
        for (int indexClass = 0; indexClass < Constants.Animals.CLASSES_NAME.length; indexClass++) {
            for (int indexSpecies = 0; indexSpecies < Constants.Animals.SPECIES_NAME[indexClass].length; indexSpecies++) {
                db.delete(Constants.Animals.getTableSpeciesName(indexClass, indexSpecies), "ID = ?", s);
            }
        }
    }
}
