package theseal.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import theseal.io.GameFileReader;
import theseal.model.Bear;
import theseal.model.Drawable;
import theseal.model.Entity;
import theseal.model.Hole;
import theseal.model.Mortal;
import theseal.model.Seal;

public class Game {
	public static final String SEALS_DIED = "All of the seals are dead. Press ESC to exit.";
	public static final String SEALS_LIVED = "The survivours lived to tell the tale. Press ESC to exit.";
	private static final String SEAL_DROWNED = "DROWNED";
	private static final String SEAL_EATEN = "EATEN";

	private int rows;
	private int cols;

	private ArrayList<Entity> entities;
	private ArrayList<Hole> holes;
	private Bear bear;
	private ArrayList<Seal> seals;
	private int ticksToSurvive;
	private int ticksRan;

	private ArrayList<Entity> toBeDeleted;
	private ArrayList<GameEvent> gameEvents;

	public Game() {
		this.ticksRan = 0;
		GameFileReader gfr = new GameFileReader("Bear_Seals.txt");
		gfr.readData();
		this.entities = gfr.getEntities();
		this.rows = gfr.getRows();
		this.cols = gfr.getCols();
		this.ticksToSurvive = gfr.getTicksToSurvive();
		this.holes = new ArrayList<>();
		this.bear = null;
		this.seals = new ArrayList<>();

		// Load the other lists
		for (Entity e : this.entities) {
			if (e instanceof Seal) {
				this.seals.add((Seal) e);
			} else if (e instanceof Hole) {
				this.holes.add((Hole) e);
			} else if (e instanceof Bear) {
				this.bear = (Bear) e;
			}
		}

		// Surface the seals that are on holes
		for (Seal s : this.seals) {
			Point p = s.getPosition();
			for (Hole h : this.holes) {
				if (p.equals(h.getPosition())) {
					s.surface();
				}
			}
		}

		this.toBeDeleted = new ArrayList<>();
		this.gameEvents = new ArrayList<>();
	}

	public GameState run() {
		if (this.seals.size() == 0) {
			return GameState.SealsDead;
		}
		if (this.ticksRan >= this.ticksToSurvive) {
			return GameState.SealsLive;
		}
		this.ticksRan++;

		this.toBeDeleted.clear();
		this.gameEvents.clear();

		// Seal logic
		for (Seal s : this.seals) {
			// Submerges when it moves
			s.submerge();

			// Swim to new location or a hole if the seal is low on air
			s.move(this.getRandomHolePositionForSeal(s), this.getRandomAvailableCellForSeal(s));

			// Check if the seal is at a hole
			for (Hole h : this.holes) {
				if (s.getPosition().equals(h.getPosition())) {
					s.surface();
				}
			}

			if (s.outOfAir()) {
				s.die();
				this.gameEvents.add(new GameEvent(s.getPosition(), SEAL_DROWNED));
			}
		}

		// Bear logic
		this.bear.moveIfWilling(this.getRandomHolePositionForBear());
		for (Seal s : this.seals) {
			if ((!s.isSubmerged) && this.bear.getPosition().equals(s.getPosition())) {
				if (this.bear.kill(s)) {
					this.gameEvents.add(new GameEvent(s.getPosition(), SEAL_EATEN));
				}
			}
		}

		for (Entity e : this.entities) {
			if (e instanceof Mortal) {
				if (((Mortal) e).isDead()) {
					this.toBeDeleted.add(e);
				}
			}
		}

		for (Entity e : this.toBeDeleted) {
			this.entities.remove(e);
			if (e instanceof Seal) {
				this.seals.remove((Seal) e);
			}
		}

		return GameState.StillRunning;
	}

	private boolean isSpaceAvailableForSeal() {
		return (this.rows * this.cols) > this.seals.size();
	}

	private boolean isFreeCellForSeal(Seal sealToMove, Point testLocation) {
		for (Seal s : this.seals) {
			if (sealToMove != s) {
				if (s.getPosition().equals(testLocation)) {
					return false;
				}
			}
		}
		return true;
	}

	private Point getRandomAvailableCellForSeal(Seal seal) {
		Random r = new Random();

		if (this.isSpaceAvailableForSeal()) {
			Point newPoint;
			do {
				newPoint = new Point(r.nextInt(this.rows), r.nextInt(this.cols));
			} while (!this.isFreeCellForSeal(seal, newPoint));
			return newPoint;
		} else {
			return seal.getPosition();
		}
	}

	private ArrayList<Hole> getFreeHolesForSeal() {
		ArrayList<Hole> freeHoles = new ArrayList<>();

		for (Hole h : this.holes) {
			boolean isFreeHole = true;
			Point holePosition = h.getPosition();
			for (Seal s : this.seals) {
				if (s.getPosition().equals(holePosition)) {
					isFreeHole = false;
				}
			}
			if (isFreeHole) {
				freeHoles.add(h);
			}
		}

		return freeHoles;
	}

	private Point getRandomHolePositionForSeal(Seal seal) {
		Random r = new Random();

		ArrayList<Hole> freeHoles = this.getFreeHolesForSeal();
		int freeHolesSize = freeHoles.size();
		if (freeHolesSize >= 1) {
			return freeHoles.get(r.nextInt(freeHolesSize)).getPosition();
		} else {
			return seal.getPosition();
		}
	}

	private Point getRandomHolePositionForBear() {
		Random r = new Random();

		Point currentLocation = this.bear.getPosition();
		int noOfHoles = this.holes.size();
		if (noOfHoles == 1) {
			return currentLocation;
		}

		Point newPosition;
		do {
			newPosition = this.holes.get(r.nextInt(noOfHoles)).getPosition();
		} while (newPosition.equals(currentLocation));
		return newPosition;
	}

	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

	public ArrayList<Hole> getHoles() {
		return this.holes;
	}

	public ArrayList<Seal> getSeals() {
		return this.seals;
	}

	public Bear getBear() {
		return this.bear;
	}

	public ArrayList<GameEvent> getGameEvents() {
		return this.gameEvents;
	}

	public ArrayList<Drawable> getDrawables() {
		ArrayList<Drawable> drawables = new ArrayList<>();

		for (Entity e : this.entities) {
			if (e instanceof Drawable) {
				drawables.add(e);
			}
		}

		return drawables;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}
}
