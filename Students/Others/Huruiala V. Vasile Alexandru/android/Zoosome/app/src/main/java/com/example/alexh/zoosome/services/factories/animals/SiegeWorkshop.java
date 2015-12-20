package com.example.alexh.zoosome.services.factories.animals;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.models.animals.Scorpion;
import com.example.alexh.zoosome.models.animals.Mangonel;
import com.example.alexh.zoosome.models.animals.Ram;
import com.example.alexh.zoosome.models.animals.Trebuchet;
import com.example.alexh.zoosome.services.factories.Constants;
import com.example.alexh.zoosome.services.factories.NameGenerator;

public class SiegeWorkshop extends SpeciesFactory {
    private static final String[] NAME_ENDING = {"ram", "mangonel", "scorpion", "trebuchet"};
    private static final int[] NO_LEGS_BASE = {4, 4, 2, 0};
    private static final int[] NO_LEGS_VAR = {2, 1, 1, 0};
    private static final double[] MAINTENANCE_COST_BASE = {1.5, 2.5, 1.25, 4.5};
    private static final double[] MAINTENANCE_COST_VAR = {1.5, 1.5, 1.25, 3.0};
    private static final double[] DANGER_CHANCE_BASE = {0.05, 0.075, 0.1, 0.05};
    private static final double[] DANGER_CHANCE_VAR = {0.1, 0.225, 0.15, 0.35};
    private static final int[] RANGE_BASE = {0, 100, 200, 250};
    private static final int[] RANGE_VAR = {0, 50, 100, 200};
    private static final double[] MOBLIE_CHACE = {0.9, 0.6, 0.55, 0.001};

    @Override
    public Animal getAnimal(String type) throws Exception {
        if (Constants.Animals.Sieges.RAM.equals(type)) {
            return new Ram();
        } else if (Constants.Animals.Sieges.MANGONEL.equals(type)) {
            return new Mangonel();
        } else if (Constants.Animals.Sieges.SCORPION.equals(type)) {
            return new Scorpion();
        } else if (Constants.Animals.Sieges.TREBUCHET.equals(type)) {
            return new Trebuchet();
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }

    @Override
    public Animal getRandomAnimalOfType(String type) throws Exception {
        String name = NameGenerator.getRandomName();

        if (Constants.Animals.Sieges.RAM.equals(type)) {
            final int index = 0;

            name += NAME_ENDING[index];
            final int noLegs = NO_LEGS_BASE[index] + 2 * (int) (NO_LEGS_VAR[index] * Math.random());
            final double maintenanceCost = MAINTENANCE_COST_BASE[index] + MAINTENANCE_COST_VAR[index] * Math.random();
            final double dangerPerc = DANGER_CHANCE_BASE[index] + DANGER_CHANCE_VAR[index] * Math.random();
            final int range = RANGE_BASE[index] + (int) (RANGE_VAR[index] * Math.random());
            final boolean mobile = (Math.random() <= MOBLIE_CHACE[index]);

            return new Ram(name, noLegs, maintenanceCost, dangerPerc, range, mobile);
        } else if (Constants.Animals.Sieges.MANGONEL.equals(type)) {
            final int index = 1;

            name += NAME_ENDING[index];
            final int noLegs = NO_LEGS_BASE[index] + 2 * (int) (NO_LEGS_VAR[index] * Math.random());
            final double maintenanceCost = MAINTENANCE_COST_BASE[index] + MAINTENANCE_COST_VAR[index] * Math.random();
            final double dangerPerc = DANGER_CHANCE_BASE[index] + DANGER_CHANCE_VAR[index] * Math.random();
            final int range = RANGE_BASE[index] + (int) (RANGE_VAR[index] * Math.random());
            final boolean mobile = (Math.random() <= MOBLIE_CHACE[index]);

            return new Mangonel(name, noLegs, maintenanceCost, dangerPerc, range, mobile);
        } else if (Constants.Animals.Sieges.SCORPION.equals(type)) {
            final int index = 2;

            name += NAME_ENDING[index];
            final int noLegs = NO_LEGS_BASE[index] + 2 * (int) (NO_LEGS_VAR[index] * Math.random());
            final double maintenanceCost = MAINTENANCE_COST_BASE[index] + MAINTENANCE_COST_VAR[index] * Math.random();
            final double dangerPerc = DANGER_CHANCE_BASE[index] + DANGER_CHANCE_VAR[index] * Math.random();
            final int range = RANGE_BASE[index] + (int) (RANGE_VAR[index] * Math.random());
            final boolean mobile = (Math.random() <= MOBLIE_CHACE[index]);

            return new Scorpion(name, noLegs, maintenanceCost, dangerPerc, range, mobile);
        } else if (Constants.Animals.Sieges.TREBUCHET.equals(type)) {
            final int index = 3;

            name += NAME_ENDING[index];
            final int noLegs = NO_LEGS_BASE[index] + 2 * (int) (NO_LEGS_VAR[index] * Math.random());
            final double maintenanceCost = MAINTENANCE_COST_BASE[index] + MAINTENANCE_COST_VAR[index] * Math.random();
            final double dangerPerc = DANGER_CHANCE_BASE[index] + DANGER_CHANCE_VAR[index] * Math.random();
            final int range = RANGE_BASE[index] + (int) (RANGE_VAR[index] * Math.random());
            final boolean mobile = (Math.random() <= MOBLIE_CHACE[index]);

            return new Trebuchet(name, noLegs, maintenanceCost, dangerPerc, range, mobile);
        } else {
            throw new Exception("Invalid animal exception!");
        }
    }
}
