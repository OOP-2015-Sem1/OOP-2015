package catalog.brain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import catalog.ui.CatalogFrame;

public class Main {
	public static Classroom clasa = new Classroom();
	public static Students stud = new Students();

	@SuppressWarnings({ "unchecked", "static-access", "static-access", "static-access" })
	public static void main(String[] args) {

		int z = 1,y=1;
		for (int i = 0; i < 13; i++) {
			int j = 0;
			String fileName = "NULL";
			if (i == 0)
				fileName = "Classrooms.txt";
			if (i >=1) {
				fileName = i + "GradeStudents.txt";
			}
			try {

				// Create object of FileReader
				FileReader inputFile = new FileReader(fileName);
				// Instantiate the BufferedReader Class
				BufferedReader bufferReader = new BufferedReader(inputFile);
				// Variable to hold the one line data
				String line;
				// Read file line by line and print on the console
				while ((line = bufferReader.readLine()) != null) {

					if (i == 0) {
						Students.Classrooms.add(j, line);
					}
					if(i>=1){
						
						Students.studentsFromGrade[i].add(j, line);
					}
					j++;
				}
				z++;
				// Close the buffer reader
				bufferReader.close();
			} catch (Exception e) {
				System.out.println("Error while reading file line by line: " + fileName + e.getMessage());
			}
		}
		new CatalogFrame();
	}
}
