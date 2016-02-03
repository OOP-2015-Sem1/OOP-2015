package bear.and.seals.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bear.and.seals.models.Bear;
import bear.and.seals.models.Entity;
import bear.and.seals.models.Hole;
import bear.and.seals.models.Seal;

public class BearSealsFileReader {
	private final String fileName;

	public BearSealsFileReader(String fileName) {
		this.fileName = fileName;
	}

	public List<Entity> getBoardEntities() {
		List<Entity> entities = new ArrayList<Entity>();

		String line = null;

		try {
			FileReader fileReader = new FileReader(fileName);

			BufferedReader bufferedReader = new BufferedReader(fileReader);

			int lineCounter = 1;
			while ((line = bufferedReader.readLine()) != null) {
				if (lineCounter == 1) {
					String[] lineElements = line.split(" ");
					entities.addAll(parseEntities(lineElements));
				} else if (lineCounter <= 3) {
					String[] lineElements = line.split(" ");
					String[] usefulLineElements = Arrays.copyOfRange(
							lineElements, 1, lineElements.length);
					entities.addAll(parseEntities(usefulLineElements));
				}

				lineCounter++;
			}

			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		return entities;
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

}
