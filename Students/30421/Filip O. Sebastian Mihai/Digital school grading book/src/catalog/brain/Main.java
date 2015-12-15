package catalog.brain;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import catalog.ui.CatalogFrame;

public class Main {
	static public ArrayList<String> Classrooms = new ArrayList<String>();
	public static ArrayList<String> Grade1 = new ArrayList<String>();
	public static ArrayList<String> Grade2 = new ArrayList<String>();
	static public ArrayList<String> Grade3 = new ArrayList<String>();
	static public ArrayList<String> Grade4 = new ArrayList<String>();
	static public ArrayList<String> Grade5 = new ArrayList<String>();
	static public ArrayList<String> Grade6 = new ArrayList<String>();
	static public ArrayList<String> Grade7 = new ArrayList<String>();
	static public ArrayList<String> Grade8 = new ArrayList<String>();
	static public ArrayList<String> Grade9 = new ArrayList<String>();
	static public ArrayList<String> Grade10 = new ArrayList<String>();
	static public ArrayList<String> Grade11 = new ArrayList<String>();
	static public ArrayList<String> Grade12 = new ArrayList<String>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for (int i = 0; i < 13; i++) {
			int j = 0;
			String fileName = "NULL";
			if (i == 0)
				fileName = "Classrooms.txt";
			if (i == 1)
				fileName = "1GradeStudents.txt";
			if (i == 2)
				fileName = "2GradeStudents.txt";
			if (i == 3)
				fileName = "3GradeStudents.txt";
			if (i == 4)
				fileName = "4GradeStudents.txt";
			if (i == 5)
				fileName = "5GradeStudents.txt";
			if (i == 6)
				fileName = "6GradeStudents.txt";
			if (i == 7)
				fileName = "7GradeStudents.txt";
			if (i == 8)
				fileName = "8GradeStudents.txt";
			if (i == 9)
				fileName = "9GradeStudents.txt";
			if (i == 10)
				fileName = "10GradeStudents.txt";
			if (i == 11)
				fileName = "11GradeStudents.txt";
			if (i == 12)
				fileName = "12GradeStudents.txt";
			try {

				// Create object of FileReader
				FileReader inputFile = new FileReader(fileName);

				// Instantiate the BufferedReader Class
				BufferedReader bufferReader = new BufferedReader(inputFile);
				// Variable to hold the one line data
				String line;

				// Read file line by line and print on the console

				while ((line = bufferReader.readLine()) != null) {
					if (i == 0)
						Classrooms.add(j, line);
					if (i == 1)
						Grade1.add(j, line);
					if (i == 2)
						Grade2.add(j, line);
					if (i == 3)
						Grade3.add(j, line);
					if (i == 4)
						Grade4.add(j, line);
					if (i == 5)
						Grade5.add(j, line);
					if (i == 6)
						Grade6.add(j, line);
					if (i == 7)
						Grade7.add(j, line);
					if (i == 8)
						Grade8.add(j, line);
					if (i == 9)
						Grade9.add(j, line);
					if (i == 10)
						Grade10.add(j, line);
					if (i == 11)
						Grade11.add(j, line);
					if (i == 12)
						Grade12.add(j, line);
					j++;
				}
				// Close the buffer reader
				bufferReader.close();
			} catch (Exception e) {
				System.out.println("Error while reading file line by line: " + e.getMessage());
			}
		}
		new CatalogFrame();
	}

}
