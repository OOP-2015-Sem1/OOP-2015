package bear.and.seals.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import bear.and.seals.io.BearSealsFileReader;
import bear.and.seals.io.BoardConsolePrinter;
import bear.and.seals.models.Bear;
import bear.and.seals.models.Entity;
import bear.and.seals.models.Hole;
import bear.and.seals.models.Seal;

public class Game {

	private static final int MAX_STEPS = 300;
	private static final String CONFIG_FILE_NAME = "Bear_Seals.txt";

	private final BoardConfiguration boardConfiguration;

	private final List<Entity> entities;
	private final Bear bear;
	private final List<Hole> holes;
	private final List<Seal> seals;

	private int numberOfSteps = 0;

	public Game() {
		BearSealsFileReader fileReader = new BearSealsFileReader(
				CONFIG_FILE_NAME);
		boardConfiguration = new BoardConfiguration();
		entities = fileReader.getBoardEntities();
		bear = (Bear) entities.get(0);
		holes = new ArrayList<Hole>();
		seals = new ArrayList<Seal>();
		fillArrays();
		normalizeSealUnderwaterStatus();
	}

	public void run() {
		Scanner in = new Scanner(System.in);

		while (numberOfSteps < MAX_STEPS && seals.size() > 0) {
			System.out.printf("%d seals left\n", seals.size());

			boardConfiguration.updateBoard(entities);
			boardConfiguration.printBoard(new BoardConsolePrinter());

			manageSeals();
			manageBear();
			String r = in.nextLine();
			if (r.equals("q")) {
				break;
			}

		}

		if (seals.size() == 0) {
			System.out.println("All the seals died");
		} else {
			System.out.println("300 steps have passed!");
		}

		in.close();
	}

	private void manageBear() {
		bear.nextStep();
		if (bear.shouldMove()) {
			bear.move(boardConfiguration.getRandomHoleLocation(holes));
		}
	}

	private void manageSeals() {
		Iterator<Seal> i = seals.iterator();
		while (i.hasNext()) {
			Seal s = i.next();
			s.nextStep();
			if (s.isUnderwater()) {
				boolean canStillSwim = s.swim();
				if (canStillSwim) {
					s.move(boardConfiguration.getRandomlyUnnoccupiedPosition());
				} else {
					Point randomHoleLocation = boardConfiguration
							.getRandomHoleLocation(holes);
					if (bear.canEat()
							&& bear.getPosition().equals(randomHoleLocation)) {
						// "killing" the seal
						i.remove();
						bear.eat();
						System.out.println("The seal got eaten by the bear.");
						entities.remove(s);
					} else if (holeOccupied(randomHoleLocation) && s.isDying()) {
						i.remove();
						System.out.println("Seal drowned.");
						entities.remove(s);
					} else {
						s.move(randomHoleLocation);
						s.setUnderwater(false);
					}
				}
			} else {
				s.setUnderwater(true);
				s.move(boardConfiguration.getRandomlyUnnoccupiedPosition());
			}
		}
	}

	private boolean holeOccupied(Point randomHoleLocation) {
		boolean holeOccupied = false;
		for (Seal s : seals) {
			if (s.getPosition().equals(randomHoleLocation)) {
				holeOccupied = true;
			}
		}
		return holeOccupied;
	}

	private void normalizeSealUnderwaterStatus() {
		for (Seal s : seals) {
			s.setUnderwater(true);
			for (Hole h : holes) {
				if (s.getPosition().equals(h.getPosition())) {
					s.setUnderwater(false);
				}
			}
		}
	}

	private void fillArrays() {
		// initial bear position also marks a hole!!!
		// create hole from initial bear position
		Hole initialBearHole = new Hole(new Point(bear.getPosition()));
		holes.add(initialBearHole);
		entities.add(initialBearHole);

		int i = 1;
		while (i < entities.size() && entities.get(i) instanceof Hole) {
			holes.add((Hole) entities.get(i));
			i++;
		}
		while (i < entities.size() && entities.get(i) instanceof Seal) {
			seals.add((Seal) entities.get(i));
			i++;
		}
	}
}
