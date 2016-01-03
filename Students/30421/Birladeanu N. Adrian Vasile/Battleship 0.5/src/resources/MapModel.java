package resources;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import resources.Resources;

public class MapModel {
	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	protected int orientation = HORIZONTAL;

	public static int shipType;
	public static final int CARRIER = 1;
	public static final int BATTLESHIP = 2;
	public static final int CRUISER = 3;
	public static final int DESTROYER = 4;

	public boolean correctPosition = false;
	protected Point point;
	protected ArrayList<Point> highlightedRegions = new ArrayList<Point>();

	protected int numberOfCarriers;
	protected int numberOfBattleships;
	protected int numberOfCruisers;
	protected int numberOfDestroyers;
	public int shipPoints=0;

	protected int[][] region = new 
			int[Resources.MAP_DIMENSION][Resources.MAP_DIMENSION];
	protected int[][]visitedRegion=new 
			int[Resources.MAP_DIMENSION][Resources.MAP_DIMENSION];
	public MapInterface interactiveMap;

	public MapModel() {
		interactiveMap = new MapInterface(this);
		for (int i = 0; i < Resources.MAP_DIMENSION; i++)
			for (int j = 0; j < Resources.MAP_DIMENSION; j++) {
				visitedRegion[i][j] = 0;
				region[i][j]=0;
			}
		numberOfCarriers = 0;
		numberOfBattleships = 0;
		numberOfCruisers = 0;
		numberOfDestroyers = 0;
	}
	
	public int getRegion(int i, int j){
		return region[i][j];
	}

	/**
	 * Functions that select the ship to be placed
	 */

	public void selectCarrier() {
		shipType = CARRIER;
	}

	public void selectBattleship() {
		shipType = BATTLESHIP;
	}

	public void selectCruiser() {
		shipType = CRUISER;
	}

	public void selectDestroyer() {
		shipType = DESTROYER;
	}

	/**
	 * Select Orientation
	 */

	public void setVerticalOrientation() {
		orientation = VERTICAL;
	}

	public void setHorizontalOrientation() {
		orientation = HORIZONTAL;
	}

	/**
	 * Check if the ship can be safely placed
	 */

	public void checkShip(int i, int j) {
		if (shipType == DESTROYER) {
			checkDestroyer(i, j);
		} else if (shipType == CRUISER) {
			checkCruiser(i, j);
		} else if (shipType == BATTLESHIP) {
			checkBattleship(i, j);
		} else if (shipType == CARRIER) {
			checkCarrier(i, j);
		}
	}

	public void checkDestroyer(int i, int j) {
		if (orientation == VERTICAL) {
			if (i < 9) {
				if (region[i][j] == 0 && region[i + 1][j] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i + 1, j, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i + 1, j, Color.RED);
					correctPosition = false;
				}
			} else {
				highlightRegion(i, j, Color.RED);
				correctPosition = false;
			}
		} else if (orientation == HORIZONTAL) {
			if (j < 9) {
				if (region[i][j] == 0 && region[i][j + 1] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i, j + 1, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i, j + 1, Color.RED);
					correctPosition = false;
				}
			} else {
				highlightRegion(i, j, Color.RED);
				correctPosition = false;
			}
		}
	}

	public void checkCruiser(int i, int j) {
		if (orientation == VERTICAL) {
			if (i < 8) {
				if (region[i][j] == 0 && region[i + 1][j] == 0 && 
						region[i + 2][j] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i + 1, j, Color.GREEN);
					highlightRegion(i + 2, j, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i + 1, j, Color.RED);
					highlightRegion(i + 2, j, Color.RED);
					correctPosition = false;
				}
			} else {
				for (int k = i; k < Resources.MAP_DIMENSION; k++) {
					highlightRegion(k, j, Color.RED);
					correctPosition = false;
				}
			}
		} else if (orientation == HORIZONTAL) {
			if (j < 8) {
				if (region[i][j] == 0 && region[i][j + 1] == 0 && 
						region[i][j + 2] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i, j + 1, Color.GREEN);
					highlightRegion(i, j + 2, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i, j + 1, Color.RED);
					highlightRegion(i, j + 2, Color.RED);
					correctPosition = false;
				}
			} else {
				for (int k = j; k < Resources.MAP_DIMENSION; k++) {
					highlightRegion(i, k, Color.RED);
					correctPosition = false;
				}
			}
		}
	}

	public void checkBattleship(int i, int j) {
		if (orientation == VERTICAL) {
			if (i < 7) {
				if (region[i][j] == 0 && region[i + 1][j] == 0 
						&& region[i + 2][j] == 0 && region[i + 3][j] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i + 1, j, Color.GREEN);
					highlightRegion(i + 2, j, Color.GREEN);
					highlightRegion(i + 3, j, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i + 1, j, Color.RED);
					highlightRegion(i + 2, j, Color.RED);
					highlightRegion(i + 3, j, Color.RED);
					correctPosition = false;
				}
			} else {
				for (int k = i; k < Resources.MAP_DIMENSION; k++) {
					highlightRegion(k, j, Color.RED);
					correctPosition = false;
				}
			}
		} else if (orientation == HORIZONTAL) {
			if (j < 7) {
				if (region[i][j] == 0 && region[i][j + 1] == 0 && 
						region[i][j + 2] == 0 && region[i][j + 3] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i, j + 1, Color.GREEN);
					highlightRegion(i, j + 2, Color.GREEN);
					highlightRegion(i, j + 3, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i, j + 1, Color.RED);
					highlightRegion(i, j + 2, Color.RED);
					highlightRegion(i, j + 3, Color.RED);
					correctPosition = false;
				}
			} else {
				for (int k = j; k < Resources.MAP_DIMENSION; k++) {
					highlightRegion(i, k, Color.RED);
					correctPosition = false;
				}
			}
		}
	}

	public void checkCarrier(int i, int j) {
		if (orientation == VERTICAL) {
			if (i < 8 && j > 0) {
				if (region[i][j] == 0 && region[i + 1][j] == 0 && 
						region[i + 2][j] == 0 && region[i + 1][j - 1] == 0
						&& region[i + 2][j - 1] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i + 1, j, Color.GREEN);
					highlightRegion(i + 2, j, Color.GREEN);
					highlightRegion(i + 1, j - 1, Color.GREEN);
					highlightRegion(i + 2, j - 1, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i + 1, j, Color.RED);
					highlightRegion(i + 2, j, Color.RED);
					highlightRegion(i + 1, j - 1, Color.RED);
					highlightRegion(i + 2, j - 1, Color.RED);
					correctPosition = false;
				}
			} else {
				correctPosition = false;
				if (i == 9) {
					highlightRegion(i, j, Color.RED);
				} else if (i == 8) {
					if (j == 0) {
						highlightRegion(i, j, Color.RED);
						highlightRegion(i + 1, j, Color.RED);
					} else {
						highlightRegion(i, j, Color.RED);
						highlightRegion(i + 1, j, Color.RED);
						highlightRegion(i + 1, j - 1, Color.RED);
					}
				} else if (j == 0) {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i + 1, j, Color.RED);
					highlightRegion(i + 2, j, Color.RED);
				}
			}
		} else if (orientation == HORIZONTAL) {
			if (j < 8 && i < 9) {
				if (region[i][j] == 0 && region[i][j + 1] == 0 && 
						region[i][j + 2] == 0 && region[i + 1][j + 1] == 0
						&& region[i + 1][j + 2] == 0) {
					highlightRegion(i, j, Color.GREEN);
					highlightRegion(i, j + 1, Color.GREEN);
					highlightRegion(i, j + 2, Color.GREEN);
					highlightRegion(i + 1, j + 1, Color.GREEN);
					highlightRegion(i + 1, j + 2, Color.GREEN);
					correctPosition = true;
				} else {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i, j + 1, Color.RED);
					highlightRegion(i, j + 2, Color.RED);
					highlightRegion(i + 1, j + 1, Color.RED);
					highlightRegion(i + 1, j + 2, Color.RED);
					correctPosition = false;
				}
			} else {
				correctPosition = false;
				if (j == 9) {
					highlightRegion(i, j, Color.RED);
				} else if (j == 8) {
					if (i < 9) {
						highlightRegion(i, j, Color.RED);
						highlightRegion(i, j + 1, Color.RED);
						highlightRegion(i + 1, j + 1, Color.RED);
					} else if (i == 9) {
						highlightRegion(i, j, Color.RED);
						highlightRegion(i, j + 1, Color.RED);
					}
				} else if (i == 9) {
					highlightRegion(i, j, Color.RED);
					highlightRegion(i, j + 1, Color.RED);
					highlightRegion(i, j + 2, Color.RED);
				}

			}
		}
	}

	public void highlightRegion(int i, int j, Color colour) {
		interactiveMap.changeButtonColor(i, j, colour);
		point = new Point(i, j);
		highlightedRegions.add(point);
		//System.out.println("Added: ("+i+", "+j+")");
	}

	public void revertTemporaryColours() {
		while (!highlightedRegions.isEmpty()) {
			point = highlightedRegions.get(0);
			int i = (int) point.getX();
			int j = (int) point.getY();
			interactiveMap.revertColour(i, j);
			highlightedRegions.remove(0);
		}
	}

	/**
	 * Place the ships on the selected Regions
	 */

	public void setShip(int i, int j) {
		if (shipType == DESTROYER) {
			if (numberOfDestroyers < 3) {
				setDestroyer(i, j);
				numberOfDestroyers++;
			}
		} else if (shipType == CRUISER) {
			if (numberOfCruisers < 2) {
				setCruiser(i, j);
				numberOfCruisers++;
			}
		} else if (shipType == BATTLESHIP) {
			if (numberOfBattleships < 1) {
				setBattleship(i, j);
				numberOfBattleships++;
			}
		} else if (shipType == CARRIER) {
			if (numberOfCarriers < 1) {
				setCarrier(i, j);
				numberOfCarriers++;
			}
		}
	}

	public void setDestroyer(int i, int j) {
		if (orientation == VERTICAL) {
			region[i][j] = 1;
			region[i + 1][j] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j, Color.GRAY);
		} else if (orientation == HORIZONTAL) {
			region[i][j] = 1;
			region[i][j + 1] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 1, Color.GRAY);
		}
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		shipPoints+=2;
	}

	public void setCruiser(int i, int j) {
		if (orientation == VERTICAL) {
			region[i][j] = 1;
			region[i + 1][j] = 1;
			region[i + 2][j] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 2, j, Color.GRAY);
		} else if (orientation == HORIZONTAL) {
			region[i][j] = 1;
			region[i][j + 1] = 1;
			region[i][j + 2] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 1, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 2, Color.GRAY);
		}
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		shipPoints+=3;
	}

	public void setBattleship(int i, int j) {
		if (orientation == VERTICAL) {
			region[i][j] = 1;
			region[i + 1][j] = 1;
			region[i + 2][j] = 1;
			region[i + 3][j] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 2, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 3, j, Color.GRAY);
		} else if (orientation == HORIZONTAL) {
			region[i][j] = 1;
			region[i][j + 1] = 1;
			region[i][j + 2] = 1;
			region[i][j + 3] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 1, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 2, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 3, Color.GRAY);
		}
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		shipPoints+=4;

	}

	public void setCarrier(int i, int j) {
		if (orientation == VERTICAL) {
			region[i][j] = 1;
			region[i + 1][j] = 1;
			region[i + 1][j - 1] = 1;
			region[i + 2][j] = 1;
			region[i + 2][j - 1] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j - 1, Color.GRAY);
			interactiveMap.changeButtonColor(i + 2, j, Color.GRAY);
			interactiveMap.changeButtonColor(i + 2, j - 1, Color.GRAY);
		} else if (orientation == HORIZONTAL) {
			region[i][j] = 1;
			region[i][j + 1] = 1;
			region[i + 1][j + 1] = 1;
			region[i][j + 2] = 1;
			region[i + 1][j + 2] = 1;
			interactiveMap.changeButtonColor(i, j, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 1, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j + 1, Color.GRAY);
			interactiveMap.changeButtonColor(i, j + 2, Color.GRAY);
			interactiveMap.changeButtonColor(i + 1, j + 2, Color.GRAY);
		}
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		highlightedRegions.remove(0);
		shipPoints+=5;
	}

	public void colourMap() {
		for (int i = 0; i < Resources.MAP_DIMENSION; i++)
			for (int j = 0; j < Resources.MAP_DIMENSION; j++) {
				if (region[i][j] == 1) {
					interactiveMap.changeButtonColor(i, j, Color.GREEN);
				} else if (region[i][j] == 0) {
					interactiveMap.changeButtonColor(i, j, Color.BLUE);
				}
			}
	}

	public void reset() {
		for (int i = 0; i < Resources.MAP_DIMENSION; i++)
			for (int j = 0; j < Resources.MAP_DIMENSION; j++) {
				if (region[i][j] == 1) {
					region[i][j] = 0;
				}
			}
		this.colourMap();
		numberOfCarriers = 0;
		numberOfDestroyers = 0;
		numberOfBattleships = 0;
		numberOfCruisers = 0;
	}

	public int checkRegion(int i, int j) {
		if (region[i][j] == 1)
			return 1;
		return 0;
	}
}
