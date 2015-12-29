package Calculation;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;

import java.io.PrintWriter;

import Main.Constants;

public class FileIO {

	// String filePath = "E:\\ALEX\\FACULTA\\OOP\\LABS\\1010\\src\\Players.txt";
	// File statText = new File(filePath);

	public static void writeIO(File statText, String[] date) {

		try {
			// Whatever the file path is.

			PrintWriter writer = new PrintWriter(statText, "UTF-8");
			int i = 0;
			for (i = 0; i < date.length; i++) {
				writer.println(date[i]);
			}
			writer.close();
		} catch (IOException e) {
			System.err.println("Problem writing to the file statsTest.txt");
		}
	}

	public static void readIO(String filePath, String[] date) {

		try {

			// Create object of FileReader
			FileReader inputFile = new FileReader(filePath);

			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);
			int i = -1;
			String line;
			// Read file line by line and print on the console

			while ((line = bufferReader.readLine()) != null) {

				date[++i] = line;

			}
			// Close the buffer reader
			bufferReader.close();

		} catch (Exception e) {
			System.out.println("Error while reading file line by line:" + e.getMessage());
		}

	}

	public static void readPlayers(String[] name, String[] score) {
		readIO(Constants.filePathPlayer, name);
		readIO(Constants.filePathScore, score);

	}

	public static int convertDate(String[] date, int index) {

		readIO(Constants.filePathScore, date);
		return Integer.valueOf(date[index]);

	}
}
