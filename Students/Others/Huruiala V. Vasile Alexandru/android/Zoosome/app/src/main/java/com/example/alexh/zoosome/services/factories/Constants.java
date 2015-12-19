package com.example.alexh.zoosome.services.factories;

import android.graphics.Point;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.models.animals.Aquatic;
import com.example.alexh.zoosome.models.animals.Scorpion;
import com.example.alexh.zoosome.models.animals.Bass;
import com.example.alexh.zoosome.models.animals.Bear;
import com.example.alexh.zoosome.models.animals.Beetle;
import com.example.alexh.zoosome.models.animals.Bird;
import com.example.alexh.zoosome.models.animals.Clownfish;
import com.example.alexh.zoosome.models.animals.Crocodile;
import com.example.alexh.zoosome.models.animals.Dove;
import com.example.alexh.zoosome.models.animals.Dragon;
import com.example.alexh.zoosome.models.animals.Dragonfly;
import com.example.alexh.zoosome.models.animals.Eagle;
import com.example.alexh.zoosome.models.animals.Hummingbird;
import com.example.alexh.zoosome.models.animals.Insect;
import com.example.alexh.zoosome.models.animals.Kangaroo;
import com.example.alexh.zoosome.models.animals.Lionfish;
import com.example.alexh.zoosome.models.animals.Lizard;
import com.example.alexh.zoosome.models.animals.Mammal;
import com.example.alexh.zoosome.models.animals.Mangonel;
import com.example.alexh.zoosome.models.animals.Mantis;
import com.example.alexh.zoosome.models.animals.Ostrich;
import com.example.alexh.zoosome.models.animals.Ram;
import com.example.alexh.zoosome.models.animals.Reptile;
import com.example.alexh.zoosome.models.animals.Rhinoceros;
import com.example.alexh.zoosome.models.animals.Spider;
import com.example.alexh.zoosome.models.animals.Siege;
import com.example.alexh.zoosome.models.animals.Surgeonfish;
import com.example.alexh.zoosome.models.animals.Trebuchet;
import com.example.alexh.zoosome.models.animals.Turtle;
import com.example.alexh.zoosome.models.animals.WaterType;
import com.example.alexh.zoosome.models.animals.Whale;

public final class Constants {

    public static final class Jobs {
        public static final String CARETAKER = "Caretaker";

        public static final String[] JOBS = {CARETAKER};

        public static int getCode(String job) {

            for (int i = 0; i < JOBS.length; i++) {
                if (JOBS[i].equals(job)) {
                    return i;
                }
            }

            return -1;
        }
    }

    public static final class Animals {

        public static final String FIELD_HELP_NAME = "A name";
        public static final String FIELD_HELP_POSITIVE_INTEGER = "A positive integer";
        public static final String FIELD_HELP_MAINTENANCE_DOUBLE = "A real number from 0.1 to 8.0";
        public static final String FIELD_HELP_INCLUSIVE_SUBUNITARY = "A real number form 0 to 1";
        public static final String FIELD_HELP_DOUBLE = "A real number";
        public static final String FIELD_HELP_BOOLEAN = "0 for false, 1 for true";
        public static final String FIELD_HELP_WATERTYPE = "0 for saltwater, 1 for freshwater";

        public static final String CONSTRUCTOR_CLASS_HELP_STRING = "String";
        public static final String CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER = "Positive integer";
        public static final String CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE = "Maintenance double";
        public static final String CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT = "Inclusive subunit";
        public static final String CONSTRUCTOR_CLASS_HELP_DOUBLE = "Double";
        public static final String CONSTRUCTOR_CLASS_HELP_BOOLEAN = "Boolean";
        public static final String CONSTRUCTOR_CLASS_HELP_WATERTYPE = "WaterType";

        // SQLite resources
        // Animal table
        public static final String TABLE_ANIMAL_NAME = Animal.TABLE_ANIMAL_NAME;
        public static final String TABLE_ANIMAL_COL_ID = Animal.TABLE_ANIMAL_COL_ID;
        public static final String TABLE_ANIMAL_COL_NAME = Animal.TABLE_ANIMAL_COL_NAME;
        public static final String TABLE_ANIMAL_COL_NUMBER_OF_LEGS = Animal.TABLE_ANIMAL_COL_NUMBER_OF_LEGS;
        public static final String TABLE_ANIMAL_COL_MAINTENANCE_COST = Animal.TABLE_ANIMAL_COL_MAINTENANCE_COST;
        public static final String TABLE_ANIMAL_COL_DANGER_PERCENTAGE = Animal.TABLE_ANIMAL_COL_DANGER_PERCENTAGE;
        public static final String TABLE_ANIMAL_COL_TAKEN_CARE_OF = Animal.TABLE_ANIMAL_COL_TAKEN_CARE_OF;
        public static final String[] TABLE_ANIMAL_COLS = {
                TABLE_ANIMAL_COL_ID,
                TABLE_ANIMAL_COL_NAME,
                TABLE_ANIMAL_COL_NUMBER_OF_LEGS,
                TABLE_ANIMAL_COL_MAINTENANCE_COST,
                TABLE_ANIMAL_COL_DANGER_PERCENTAGE,
                TABLE_ANIMAL_COL_TAKEN_CARE_OF};

        public static final String TABLE_ANIMAL_COL_ID_MODIFIERS = Animal.TABLE_ANIMAL_COL_ID_MODIFIERS;
        public static final String TABLE_ANIMAL_COL_NAME_MODIFIERS = Animal.TABLE_ANIMAL_COL_NAME_MODIFIERS;
        public static final String TABLE_ANIMAL_COL_NUMBER_OF_LEGS_MODIFIERS = Animal.TABLE_ANIMAL_COL_NUMBER_OF_LEGS_MODIFIERS;
        public static final String TABLE_ANIMAL_COL_MAINTENANCE_COST_MODIFIERS = Animal.TABLE_ANIMAL_COL_MAINTENANCE_COST_MODIFIERS;
        public static final String TABLE_ANIMAL_COL_DANGER_PERCENTAGE_MODIFIERS = Animal.TABLE_ANIMAL_COL_DANGER_PERCENTAGE_MODIFIERS;
        public static final String TABLE_ANIMAL_COL_TAKEN_CARE_OF_MODIFIERS = Animal.TABLE_ANIMAL_COL_TAKEN_CARE_OF_MODIFIERS;
        public static final String[] TABLE_ANIMAL_COLS_MODIFIERS = {
                TABLE_ANIMAL_COL_ID_MODIFIERS,
                TABLE_ANIMAL_COL_NAME_MODIFIERS,
                TABLE_ANIMAL_COL_NUMBER_OF_LEGS_MODIFIERS,
                TABLE_ANIMAL_COL_MAINTENANCE_COST_MODIFIERS,
                TABLE_ANIMAL_COL_DANGER_PERCENTAGE_MODIFIERS,
                TABLE_ANIMAL_COL_TAKEN_CARE_OF_MODIFIERS};

        // Class table
        public static final String TABLE_CLASS_COL_ID = Animal.TABLE_CLASS_COL_ID;

        public static final String TABLE_CLASS_COL_ID_MODIFIERS = Animal.TABLE_CLASS_COL_ID_MODIFIERS;

        // Species table
        public static final String TABLE_SPECIES_COL_ID = Animal.TABLE_SPECIES_COL_ID;

        public static final String TABLE_SPECIES_COL_ID_MODIFIERS = Animal.TABLE_SPECIES_COL_ID_MODIFIERS;

        public static final class Mammals {
            // Class related fields
            public static final String NAME = "Mammal";
            public static final Class CLASS = Mammal.class;

            // Species related fields
            public static final String BEAR = "Bear";
            public static final String KANGAROO = "Kangaroo";
            public static final String WHALE = "Whale";
            public static final String RHINOCEROS = "Rhinoceros";

            public static final String[] MAMMAL = {BEAR, KANGAROO, WHALE, RHINOCEROS};

            public static final Class[] MAMMAL_CLASSES = {Bear.class, Kangaroo.class, Whale.class, Rhinoceros.class};

            public static final Class[] MAMMAL_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, double.class, double.class};
            public static final String[] MAMMAL_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT};
            public static final String[] MAMMAL_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_DOUBLE,
                    FIELD_HELP_INCLUSIVE_SUBUNITARY};

            // SQLite resources
            public static final String TABLE_MAMMAL_NAME = Mammal.TABLE_MAMMAL_NAME;
            public static final String TABLE_MAMMAL_COL_ID = Mammal.TABLE_MAMMAL_COL_ID;
            public static final String TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE = Mammal.TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE;
            public static final String TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR = Mammal.TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR;
            public static final String[] TABLE_MAMMAL_COLS = {
                    TABLE_MAMMAL_COL_ID,
                    TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE,
                    TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR};

            public static final String TABLE_MAMMAL_COL_ID_MODIFIERS = Mammal.TABLE_MAMMAL_COL_ID_MODIFIERS;
            public static final String TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE_MODIFIERS = Mammal.TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE_MODIFIERS;
            public static final String TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR_MODIFIERS = Mammal.TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR_MODIFIERS;
            public static final String[] TABLE_MAMMAL_COLS_MODIFIERS = {
                    TABLE_MAMMAL_COL_ID_MODIFIERS,
                    TABLE_MAMMAL_COL_NORMAL_BODY_TEMPERATURE_MODIFIERS,
                    TABLE_MAMMAL_COL_PERCENTAGE_BODY_HAIR_MODIFIERS};
        }

        public static final class Reptiles {
            public static final String NAME = "Reptile";
            public static final Class CLASS = Reptile.class;

            public static final String LIZARD = "Lizard";
            public static final String TURTLE = "Turtle";
            public static final String CROCODILE = "Crocodile";
            public static final String DRAGON = "Dragon";

            public static final String[] REPTILE = {LIZARD, TURTLE, CROCODILE, DRAGON};

            public static final Class[] REPTILE_CLASSES = {Lizard.class, Turtle.class, Crocodile.class, Dragon.class};

            public static final Class[] REPTILE_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, boolean.class};
            public static final String[] REPTILE_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_BOOLEAN};
            public static final String[] REPTILE_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_BOOLEAN};

            // SQLite resources
            public static final String TABLE_REPTILE_NAME = Reptile.TABLE_REPTILE_NAME;
            public static final String TABLE_REPTILE_COL_ID = Reptile.TABLE_REPTILE_COL_ID;
            public static final String TABLE_REPTILE_COL_LAYS_EGGS = Reptile.TABLE_REPTILE_COL_LAYS_EGGS;
            public static final String[] TABLE_REPTILE_COLS = {
                    TABLE_REPTILE_COL_ID,
                    TABLE_REPTILE_COL_LAYS_EGGS};

            public static final String TABLE_REPTILE_COL_ID_MODIFIERS = Reptile.TABLE_REPTILE_COL_ID_MODIFIERS;
            public static final String TABLE_REPTILE_COL_LAYS_EGGS_MODIFIERS = Reptile.TABLE_REPTILE_COL_LAYS_EGGS_MODIFIERS;
            public static final String[] TABLE_REPTILE_COLS_MODIFIERS = {
                    TABLE_REPTILE_COL_ID_MODIFIERS,
                    TABLE_REPTILE_COL_LAYS_EGGS_MODIFIERS};
        }

        public static final class Birds {
            public static final String NAME = "Bird";
            public static final Class CLASS = Bird.class;

            public static final String DOVE = "Dove";
            public static final String OSTRICH = "Ostrich";
            public static final String EAGLE = "Eagle";
            public static final String HUMMINGBIRD = "Hummingbird";

            public static final String[] BIRD = {DOVE, OSTRICH, EAGLE, HUMMINGBIRD};

            public static final Class[] BIRD_CLASSES = {Dove.class, Ostrich.class, Eagle.class, Hummingbird.class};

            public static final Class[] BIRD_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, boolean.class, int.class};
            public static final String[] BIRD_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_BOOLEAN,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER};
            public static final String[] BIRD_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_BOOLEAN,
                    FIELD_HELP_POSITIVE_INTEGER};

            // SQLite resources
            public static final String TABLE_BIRD_NAME = Bird.TABLE_BIRD_NAME;
            public static final String TABLE_BIRD_COL_ID = Bird.TABLE_BIRD_COL_ID;
            public static final String TABLE_BIRD_COL_MIGRATES = Bird.TABLE_BIRD_COL_MIGRATES;
            public static final String TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE = Bird.TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE;
            public static final String[] TABLE_BIRD_COLS = {
                    TABLE_BIRD_COL_ID,
                    TABLE_BIRD_COL_MIGRATES,
                    TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE};

            public static final String TABLE_BIRD_COL_ID_MODIFIERS = Bird.TABLE_BIRD_COL_ID_MODIFIERS;
            public static final String TABLE_BIRD_COL_MIGRATES_MODIFIERS = Bird.TABLE_BIRD_COL_MIGRATES_MODIFIERS;
            public static final String TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE_MODIFIERS = Bird.TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE_MODIFIERS;
            public static final String[] TABLE_BIRD_COLS_MODIFIERS = {
                    TABLE_BIRD_COL_ID_MODIFIERS,
                    TABLE_BIRD_COL_MIGRATES_MODIFIERS,
                    TABLE_BIRD_COL_AVERAGE_FLIGHT_ALTITUDE_MODIFIERS};
        }

        public static final class Aquatics {
            public static final String NAME = "Aquatic";
            public static final Class CLASS = Aquatic.class;

            public static final String BASS = "Bass";
            public static final String CLOWNFISH = "Clownfish";
            public static final String SURGEONFISH = "Surgeonfish";
            public static final String LIONFISH = "Lionfish";

            public static final String[] AQUATIC = {BASS, CLOWNFISH, SURGEONFISH, LIONFISH};

            public static final Class[] AQUATIC_CLASSES = {Bass.class, Clownfish.class, Surgeonfish.class,
                    Lionfish.class};

            public static final Class[] AQUATIC_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, int.class, WaterType.class};
            public static final String[] AQUATIC_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER,
                    CONSTRUCTOR_CLASS_HELP_WATERTYPE};
            public static final String[] AQUATIC_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_WATERTYPE};

            // SQLite resources
            public static final String TABLE_AQUATIC_NAME = Aquatic.TABLE_AQUATIC_NAME;
            public static final String TABLE_AQUATIC_COL_ID = Aquatic.TABLE_AQUATIC_COL_ID;
            public static final String TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH = Aquatic.TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH;
            public static final String TABLE_AQUATIC_COL_WATER_TYPE = Aquatic.TABLE_AQUATIC_COL_WATER_TYPE;
            public static final String[] TABLE_AQUATIC_COLS = {
                    TABLE_AQUATIC_COL_ID,
                    TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH,
                    TABLE_AQUATIC_COL_WATER_TYPE};

            public static final String TABLE_AQUATIC_COL_ID_MODIFIERS = Aquatic.TABLE_AQUATIC_COL_ID_MODIFIERS;
            public static final String TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH_MODIFIERS = Aquatic.TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH_MODIFIERS;
            public static final String TABLE_AQUATIC_COL_WATER_TYPE_MODIFIERS = Aquatic.TABLE_AQUATIC_COL_WATER_TYPE_MODIFIERS;
            public static final String[] TABLE_AQUATIC_COLS_MODIFIERS = {
                    TABLE_AQUATIC_COL_ID_MODIFIERS,
                    TABLE_AQUATIC_COL_AVERAGE_SWIM_DEPTH_MODIFIERS,
                    TABLE_AQUATIC_COL_WATER_TYPE_MODIFIERS};
        }

        public static final class Insects {
            public static final String NAME = "Insect";
            public static final Class CLASS = Insect.class;

            public static final String BEETLE = "Beetle";
            public static final String SPIDER = "Spider";
            public static final String MANTIS = "Mantis";
            public static final String DRAGONFLY = "Dragonfly";

            public static final String[] INSECT = {BEETLE, SPIDER, MANTIS, DRAGONFLY};

            public static final Class[] INSECT_CLASSES = {Beetle.class, Spider.class, Mantis.class,
                    Dragonfly.class};

            public static final Class[] INSECT_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, boolean.class, boolean.class};
            public static final String[] INSECT_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_BOOLEAN,
                    CONSTRUCTOR_CLASS_HELP_BOOLEAN};
            public static final String[] INSECT_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_BOOLEAN,
                    FIELD_HELP_BOOLEAN};

            // SQLite resources
            public static final String TABLE_INSECT_NAME = Insect.TABLE_INSECT_NAME;
            public static final String TABLE_INSECT_COL_ID = Insect.TABLE_INSECT_COL_ID;
            public static final String TABLE_INSECT_COL_CAN_FLY = Insect.TABLE_INSECT_COL_CAN_FLY;
            public static final String TABLE_INSECT_COL_IS_DANGEROUS = Insect.TABLE_INSECT_COL_IS_DANGEROUS;
            public static final String[] TABLE_INSECT_COLS = {
                    TABLE_INSECT_COL_ID,
                    TABLE_INSECT_COL_CAN_FLY,
                    TABLE_INSECT_COL_IS_DANGEROUS};

            public static final String TABLE_INSECT_COL_ID_MODIFIERS = Insect.TABLE_INSECT_COL_ID_MODIFIERS;
            public static final String TABLE_INSECT_COL_CAN_FLY_MODIFIERS = Insect.TABLE_INSECT_COL_CAN_FLY_MODIFIERS;
            public static final String TABLE_INSECT_COL_IS_DANGEROUS_MODIFIERS = Insect.TABLE_INSECT_COL_IS_DANGEROUS_MODIFIERS;
            public static final String[] TABLE_INSECT_COLS_MODIFIERS = {
                    TABLE_INSECT_COL_ID_MODIFIERS,
                    TABLE_INSECT_COL_CAN_FLY_MODIFIERS,
                    TABLE_INSECT_COL_IS_DANGEROUS_MODIFIERS};
        }

        public static final class Sieges {
            public static final String NAME = "Siege";
            public static final Class CLASS = com.example.alexh.zoosome.models.animals.Siege.class;

            public static final String RAM = "Ram";
            public static final String MANGONEL = "Mangonel";
            public static final String SCORPION = "Scorpion";
            public static final String TREBUCHET = "Trebuchet";

            public static final String[] SIEGE = {RAM, MANGONEL, SCORPION, TREBUCHET};

            public static final Class[] SIEGE_CLASSES = {Ram.class, Mangonel.class, Scorpion.class, Trebuchet.class};

            public static final Class[] SIEGE_CONSTRUCTOR_CLASSES = {String.class, int.class, double.class,
                    double.class, int.class, boolean.class};
            public static final String[] SIEGE_CONSTRUCTOR_CLASSES_HELP = {CONSTRUCTOR_CLASS_HELP_STRING,
                    CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER, CONSTRUCTOR_CLASS_HELP_MAINTENANCE_DOUBLE,
                    CONSTRUCTOR_CLASS_HELP_INCLUSIVE_SUBUNIT, CONSTRUCTOR_CLASS_HELP_POSITIVE_INTEGER,
                    CONSTRUCTOR_CLASS_HELP_BOOLEAN};
            public static final String[] SIEGE_FIELD_HELP = {FIELD_HELP_NAME, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_MAINTENANCE_DOUBLE, FIELD_HELP_INCLUSIVE_SUBUNITARY, FIELD_HELP_POSITIVE_INTEGER,
                    FIELD_HELP_BOOLEAN};

            // SQLite resources
            public static final String TABLE_SIEGE_NAME = Siege.TABLE_SIEGE_NAME;
            public static final String TABLE_SIEGE_COL_ID = Siege.TABLE_SIEGE_COL_ID;
            public static final String TABLE_SIEGE_COL_RANGE = Siege.TABLE_SIEGE_COL_RANGE;
            public static final String TABLE_SIEGE_COL_IS_MOBILE = Siege.TABLE_SIEGE_COL_IS_MOBILE;
            public static final String[] TABLE_SIEGE_COLS = {
                    TABLE_SIEGE_COL_ID,
                    TABLE_SIEGE_COL_RANGE,
                    TABLE_SIEGE_COL_IS_MOBILE};

            public static final String TABLE_SIEGE_COL_ID_MODIFIERS = Siege.TABLE_SIEGE_COL_ID_MODIFIERS;
            public static final String TABLE_SIEGE_COL_RANGE_MODIFIERS = Siege.TABLE_SIEGE_COL_RANGE_MODIFIERS;
            public static final String TABLE_SIEGE_COL_IS_MOBILE_MODIFIERS = Siege.TABLE_SIEGE_COL_IS_MOBILE_MODIFIERS;
            public static final String[] TABLE_SIEGE_COLS_MODIFIERS = {
                    TABLE_SIEGE_COL_ID_MODIFIERS,
                    TABLE_SIEGE_COL_RANGE_MODIFIERS,
                    TABLE_SIEGE_COL_IS_MOBILE_MODIFIERS};
        }

        public static final String[] CLASSES_NAME = {Mammals.NAME, Reptiles.NAME, Birds.NAME, Aquatics.NAME,
                Insects.NAME, Sieges.NAME};

        public static final String[][] SPECIES_NAME = {Mammals.MAMMAL, Reptiles.REPTILE, Birds.BIRD, Aquatics.AQUATIC,
                Insects.INSECT, Sieges.SIEGE};

        public static final Class[] ANIMAL_CLASS_CLASSES = {Mammals.CLASS, Reptiles.CLASS,
                Birds.CLASS, Aquatics.CLASS, Insects.CLASS, Sieges.CLASS};

        public static final Class[][] ANIMAL_SPECIES_CLASSES = {Mammals.MAMMAL_CLASSES, Reptiles.REPTILE_CLASSES,
                Birds.BIRD_CLASSES, Aquatics.AQUATIC_CLASSES, Insects.INSECT_CLASSES, Sieges.SIEGE_CLASSES};

        public static final Class[][] ANIMAL_CONSTRUCTOR_CLASSES = {Mammals.MAMMAL_CONSTRUCTOR_CLASSES,
                Reptiles.REPTILE_CONSTRUCTOR_CLASSES, Birds.BIRD_CONSTRUCTOR_CLASSES, Aquatics.AQUATIC_CONSTRUCTOR_CLASSES,
                Insects.INSECT_CONSTRUCTOR_CLASSES, Sieges.SIEGE_CONSTRUCTOR_CLASSES};
        public static final String[][] ANIMAL_CONSTRUCTOR_CLASSES_HELP = {Mammals.MAMMAL_CONSTRUCTOR_CLASSES_HELP,
                Reptiles.REPTILE_CONSTRUCTOR_CLASSES_HELP, Birds.BIRD_CONSTRUCTOR_CLASSES_HELP,
                Aquatics.AQUATIC_CONSTRUCTOR_CLASSES_HELP, Insects.INSECT_CONSTRUCTOR_CLASSES_HELP,
                Sieges.SIEGE_CONSTRUCTOR_CLASSES_HELP};
        public static final String[][] ANIMAL_FIELD_HELP = {Mammals.MAMMAL_FIELD_HELP, Reptiles.REPTILE_FIELD_HELP,
                Birds.BIRD_FIELD_HELP, Aquatics.AQUATIC_FIELD_HELP, Insects.INSECT_FIELD_HELP, Sieges.SIEGE_FIELD_HELP};

        public static final Class[] GENERAL_CONSTRUCTOR_CLASSES = {String.class, String.class, String.class,
                String.class, String.class, String.class};

        // SQLite resources
        public static final String[] TABLE_CLASS_NAMES = {
                Mammals.TABLE_MAMMAL_NAME,
                Reptiles.TABLE_REPTILE_NAME,
                Birds.TABLE_BIRD_NAME,
                Aquatics.TABLE_AQUATIC_NAME,
                Insects.TABLE_INSECT_NAME,
                Sieges.TABLE_SIEGE_NAME};
        public static final String[][] TABLE_CLASS_COLS = {
                Mammals.TABLE_MAMMAL_COLS,
                Reptiles.TABLE_REPTILE_COLS,
                Birds.TABLE_BIRD_COLS,
                Aquatics.TABLE_AQUATIC_COLS,
                Insects.TABLE_INSECT_COLS,
                Sieges.TABLE_SIEGE_COLS};
        public static final String[][] TABLE_CLASS_COLS_MODIFIERS = {
                Mammals.TABLE_MAMMAL_COLS_MODIFIERS,
                Reptiles.TABLE_REPTILE_COLS_MODIFIERS,
                Birds.TABLE_BIRD_COLS_MODIFIERS,
                Aquatics.TABLE_AQUATIC_COLS_MODIFIERS,
                Insects.TABLE_INSECT_COLS_MODIFIERS,
                Sieges.TABLE_SIEGE_COLS_MODIFIERS};

        /**
         * Class finder. Returns the index of class
         */
        public static int indexOfSpecies(Animal a) {
            Class cls = a.getClass();
            for (int classIndex = 0; classIndex < Animals.ANIMAL_SPECIES_CLASSES.length; classIndex++) {
                for (int speciesIndex = 0; speciesIndex < Animals.ANIMAL_SPECIES_CLASSES[classIndex].length; speciesIndex++) {
                    if (cls.equals(Animals.ANIMAL_SPECIES_CLASSES[classIndex][speciesIndex])) {
                        return speciesIndex;
                    }
                }
            }
            return -1;
        }

        /**
         * Species finder. Returns the index of species
         */
        public static int indexOfClass(Animal a) {
            Class cls = a.getClass();
            for (int classIndex = 0; classIndex < Animals.ANIMAL_SPECIES_CLASSES.length; classIndex++) {
                for (int speciesIndex = 0; speciesIndex < Animals.ANIMAL_SPECIES_CLASSES[classIndex].length; speciesIndex++) {
                    if (cls.equals(Animals.ANIMAL_SPECIES_CLASSES[classIndex][speciesIndex])) {
                        return classIndex;
                    }
                }
            }
            return -1;
        }

        public static String nameOfClass(Animal a) {
            return Animals.CLASSES_NAME[indexOfClass(a)];
        }

        public static String nameOfSpecies(Animal a) {
            return Animals.SPECIES_NAME[indexOfClass(a)][indexOfSpecies(a)];
        }

        public static Class classFromNameOfClass(String nameOfClass) {
            int classIndex = indexOfStringIn1DStringArray(Animals.CLASSES_NAME, nameOfClass);
            return Animals.ANIMAL_CLASS_CLASSES[classIndex];
        }

        public static Class classFormNameOfSpecies(String nameOfSpecies) {
            Point classIndexes = indexesOfStringIn2DStringArray(Animals.SPECIES_NAME, nameOfSpecies);
            return Animals.ANIMAL_SPECIES_CLASSES[classIndexes.x][classIndexes.y];
        }

        // SQLite Resources
        public static String getTableSpeciesName(int classIndex, int speciesIndex) {
            return SPECIES_NAME[classIndex][speciesIndex].toLowerCase();
        }
    }

    public static final class Employees {
        public static final class Caretakers {
            public static final String TCO_SUCCESS = "SUCCESS";
            public static final String TCO_KILLED = "KILLED";
            public static final String TCO_NO_TIME = "NO_TIME";
        }
    }

    public static final class XML_TAGS {
        public static final String ANIMAL = "ANIMAL";
        public static final String EMPLOYEE = "EMPLOYEE";
        public static final String DISCRIMINANT = "DISCRIMINANT";
    }

    public static final class Frames {
        public static int MULT = 60;
        public static int WIDTH = 9 * MULT;
        public static int HEIGHT = 16 * MULT;

        public static void setResolution(int w, int h) {
            WIDTH = w;
            HEIGHT = h;
        }
    }

    /**
     * Returns the index of a String if it is included in a String[]
     */
    public static int indexOfStringIn1DStringArray(String[] arr, String e) {
        if (arr.length <= 0 || e.length() <= 0) {
            return -2;
        }

        for (int index = 0; index < arr.length; index++) {
            boolean subElement = true;

            if (!arr[index].equals(e)) {
                subElement = false;
            }

            if (subElement) {
                return index;
            }
        }
        return -1;
    }

    /**
     * Returns the index of a String[] if it is included in a String[][]
     */
    public static int indexOf1DStringArrayIn2DStringArray(String[][] mat, String[] arr) {
        if (mat.length <= 0 || arr.length <= 0) {
            return -2;
        } else if (mat[0].length <= 0) {
            return -3;
        }

        for (int index = 0; index < mat.length; index++) {

            boolean subArrayFound = true;
            for (int i = 0; i < mat[index].length && subArrayFound; i++) {
                if (!mat[index][0].equals(arr[0])) {
                    subArrayFound = false;
                }
            }

            if (subArrayFound) {
                return index;
            }
        }
        return -1;
    }

    public static Point indexesOfStringIn2DStringArray(String[][] mat, String e) {
        if (mat == null || e == null || mat.length <= 0) {
            return new Point(-1, -1);
        }

        for (int row = 0; row < mat.length; row++) {
            for (int col = 0; col < mat[0].length; col++) {
                if (mat[row][col].equals(e)) {
                    return new Point(row, col);
                }
            }
        }

        return new Point(-1, -1);
    }
}
