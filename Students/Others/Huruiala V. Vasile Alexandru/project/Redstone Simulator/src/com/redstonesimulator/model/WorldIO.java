package com.redstonesimulator.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import com.redstonesimulator.model.block.Block;
import com.redstonesimulator.model.block.BlockID;

import utilities.Direction;
import utilities.Int3DPoint;

public final class WorldIO {
	private static final String SAVES_FOLDER_PATH = "Saves";
	private static final File SAVES_FOLDER_FILE = new File(SAVES_FOLDER_PATH);
	private static final String SAVE_FILE_EXTENSION = ".dat";

	public static ArrayList<String> getAllSaveFilesArrayList() {
		ArrayList<String> saveFileNames = new ArrayList<>();

		if (!SAVES_FOLDER_FILE.exists()) {
			SAVES_FOLDER_FILE.mkdirs();
		} else {
			for (File file : SAVES_FOLDER_FILE.listFiles()) {
				if (file.isFile()) {
					String fileName = file.getName();
					if (fileName.length() > SAVE_FILE_EXTENSION.length() && fileName.endsWith(SAVE_FILE_EXTENSION)) {
						saveFileNames.add(fileName.substring(0, fileName.length() - SAVE_FILE_EXTENSION.length()));
					}
				}
			}
		}

		return saveFileNames;
	}

	public static String[] getAllSaveFilesArray() {
		ArrayList<String> saveFileNames = getAllSaveFilesArrayList();
		return saveFileNames.toArray(new String[saveFileNames.size()]);
	}

	private static String getSavePath(String worldName) {
		return SAVES_FOLDER_PATH + "/" + worldName + SAVE_FILE_EXTENSION;
	}

	public static void writeWorld(String worldName, World world) {
		try {
			File saveFile = new File(getSavePath(worldName));
			if (!saveFile.exists()) {
				SAVES_FOLDER_FILE.mkdirs();
				saveFile.createNewFile();
			}

			FileWriter saveFileWriter = new FileWriter(saveFile);
			PrintWriter savePrintWriter = new PrintWriter(saveFileWriter);
			
			savePrintWriter.println(worldName);

			int xs = world.getXSize();
			savePrintWriter.println(xs);
			int ys = world.getYSize();
			savePrintWriter.println(ys);
			int zs = world.getZSize();
			savePrintWriter.println(zs);

			for (int z = 0; z < zs; z++) {
				for (int x = 0; x < xs; x++) {
					for (int y = 0; y < ys; y++) {
						Block block = world.getBlock(new Int3DPoint(x, y, z));
						BlockID blockID = BlockID.valueOfBlock(block);
						StringBuilder sb = new StringBuilder();
						sb.append(blockID.name());
						LinkedList<String> list = block.encode();
						for (String s : list) {
							sb.append(";");
							sb.append(s);
						}
						
						savePrintWriter.println(sb.toString());
					}
				}
			}

			world.setModified(false);
			
			savePrintWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static World readWorld(String worldName) {
		World world = null;

		try {
			File saveFile = new File(getSavePath(worldName));
			FileReader saveFileReader = new FileReader(saveFile);
			BufferedReader saveFileBufferedReader = new BufferedReader(saveFileReader);

			String readWorldName = saveFileBufferedReader.readLine();
			int xs = Integer.parseInt(saveFileBufferedReader.readLine());
			int ys = Integer.parseInt(saveFileBufferedReader.readLine());
			int zs = Integer.parseInt(saveFileBufferedReader.readLine());
			world = new World(readWorldName, xs, ys, zs);

			for (int z = 0; z < zs; z++) {
				for (int x = 0; x < xs; x++) {
					for (int y = 0; y < ys; y++) {
						String line = saveFileBufferedReader.readLine();
						String[] elements = line.split(";");
						
						BlockID blockID = BlockID.valueOf(elements[0]);
						Block block = blockID.makeBlock(Direction.North);
						world.setBlock(new Int3DPoint(x, y, z), block);
						
						LinkedList<String> list = new LinkedList<>();
						for (int i = 1; i < elements.length; i++) {
							list.addLast(elements[i]);
						}
						
						block.decode(list);
					}
				}
			}
			
			world.setModified(false);
			
			saveFileBufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			world = null;
		}

		return world;
	}
}
