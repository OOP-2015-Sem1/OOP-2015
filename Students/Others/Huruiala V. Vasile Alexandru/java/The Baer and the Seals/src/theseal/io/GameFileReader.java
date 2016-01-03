package theseal.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import theseal.model.Bear;
import theseal.model.Entity;
import theseal.model.Hole;
import theseal.model.Seal;

public class GameFileReader {
	private final String path;

	private ArrayList<Entity> entities;
	private int rows;
	private int cols;
	private int ticksToSurvive;

	public GameFileReader(String path) {
		this.path = path;
	}

	public void readData() {
		this.entities = new ArrayList<>();

		String line;
		try {
			FileReader fileReader = new FileReader(this.path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int lineCounter = 1;
			while ((line = bufferedReader.readLine()) != null) {
				if (lineCounter == 1) {
					String[] lineElements = line.split(" ");
					this.rows = Integer.parseInt(lineElements[0]);
					this.cols = Integer.parseInt(lineElements[1]);
					this.ticksToSurvive = Integer.parseInt(lineElements[2]);
				} else if (lineCounter <= 2) {
					String[] lineElements = line.split(" ");
					this.entities.addAll(parseEntities(lineElements));
				} else if (lineCounter <= 4) {
					String[] lineElements = line.split(" ");
					String[] usefulLineElements = Arrays.copyOfRange(lineElements, 1, lineElements.length);
					this.entities.addAll(parseEntities(usefulLineElements));
				}

				lineCounter++;
			}
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + this.path + "'");
		} catch (IOException e) {
			System.out.println("Error reading file '" + this.path + "'");
		}
	}

	private List<Entity> parseEntities(String[] lineElements) {
		List<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < lineElements.length; i += 3) {
			String typeOfEntity = lineElements[i];
			Integer x = Integer.valueOf(lineElements[i + 1]);
			Integer y = Integer.valueOf(lineElements[i + 2]);

			switch (typeOfEntity) {
			case "B":
				entities.add(new Bear(new Point(x, y)));
				break;
			case "S":
				entities.add(new Seal(new Point(x, y)));
				break;
			case "H":
				entities.add(new Hole(new Point(x, y)));
				break;
			default:
				break;
			}
		}

		return entities;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getTicksToSurvive() {
		return ticksToSurvive;
	}
}
