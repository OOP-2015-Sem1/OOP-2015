package fluffy.the.cat.io;

import java.awt.Point;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FluffySavegameManager {
	private final String FLUFFY_SAVEGAME_FILE;

	public FluffySavegameManager(String fluffyHatDat) {
		this.FLUFFY_SAVEGAME_FILE = fluffyHatDat;
	}

	public void saveGame(Point fluffyPosition) throws IOException {
		FileOutputStream fout = new FileOutputStream(FLUFFY_SAVEGAME_FILE);
		ObjectOutputStream oos = new ObjectOutputStream(fout);
		oos.writeObject(fluffyPosition);
		oos.close();
	}

	public Point restoreGame() throws IOException, ClassNotFoundException {
		Point fluffyPosition;
		FileInputStream fin = new FileInputStream(FLUFFY_SAVEGAME_FILE);
		ObjectInputStream ois = new ObjectInputStream(fin);
		fluffyPosition = (Point) ois.readObject();
		ois.close();
		return fluffyPosition;
	}

	public boolean canRestoreGame() {
		File f = new File(FLUFFY_SAVEGAME_FILE);
		return f.exists() && !f.isDirectory();
	}
}
