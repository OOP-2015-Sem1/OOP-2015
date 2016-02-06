package river.io;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import river.models.Boat;
import river.models.Curl;
import river.models.Entity;
import river.models.Pole;

public class ReadFromFile {

	private String FILE_NAME;

	public ReadFromFile(String filename) {
		this.FILE_NAME = filename;
	}

	public List<Entity> getEntities() {

		List<Entity> entities = new ArrayList<Entity>();

		try {
			FileReader readFile = new FileReader(FILE_NAME);
			BufferedReader read = new BufferedReader(readFile);

			String line;
			int lineNumber = 0;

			while ((line = read.readLine()) != null) {
				entities.addAll(parseEntities(line, lineNumber));
				lineNumber += 1;
			}

		}

		catch (FileNotFoundException e) {
			System.out.println("The file could not be found");
		} catch (IOException e) {
			System.out.println("Some error occured");
		}

		return entities;

	}

	public List<Entity> parseEntities(String line, int lineNr) {
		List<Entity> entities = new ArrayList<Entity>();

		String[] elements = null;

		if (lineNr == 0) {
			elements = line.split(" ");
			for (int i = 0; i < elements.length; i += 2) {
				Integer nr = Integer.valueOf(elements[i]);
				Integer column = Integer.valueOf(elements[i + 1]);

				for (int j = 0; j < nr; j++) {
					Curl c = new Curl(new Point(0, column));
					c.setOnRiver(false);
					entities.add(c);
				}
			}

		} else {

			for (int i = 0; i < line.length(); i++) {
					switch (line.charAt(i)) {
					case 'B': {
						entities.add(new Boat(new Point(lineNr-1, i)));
						break;
					}
					case 'P': {
						entities.add(new Pole(new Point(lineNr-1, i)));
					}
					}
				}
			}

		return entities;

	}
}
