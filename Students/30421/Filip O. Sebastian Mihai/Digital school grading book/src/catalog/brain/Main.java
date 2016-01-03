package catalog.brain;

import java.io.BufferedReader;
import java.io.FileReader;
import catalog.ui.CatalogFrame;

public class Main {
	@SuppressWarnings({ })
	public static int whichStudentButtonIsPressed;
	public static int nrOfStudents = 0;
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		new Classroom();
		new Students();
		for (int i = 0; i < 13; i++) {
			int j = 0;
			String fileName = "NULL";
			if (i == 0)
				fileName = "Classrooms.txt";
			if (i >= 1) {
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
						Classroom.Classrooms.add(j, line);
					}
					if (i >= 1) {

						Students.studentsFromGrade[i].add(j, line);
					}
					j++;
				}
				// Close the buffer reader
				bufferReader.close();
			} catch (Exception e) {
				System.out.println("Error while reading file line by line: " + fileName + " " + e.getMessage());
			}
		}
		for (int i = 1; i < 13; i++)
			//System.out.println(Students.studentsFromGrade[i].get(0));
			//System.out.println(Integer.valueOf((String) Students.studentsFromGrade[i].get(0)));
			nrOfStudents = nrOfStudents + Integer.valueOf((String) Students.studentsFromGrade[i].get(0));
		new CatalogFrame();
	}
}
