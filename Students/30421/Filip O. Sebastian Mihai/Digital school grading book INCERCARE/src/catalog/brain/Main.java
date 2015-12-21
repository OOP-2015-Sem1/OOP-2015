package catalog.brain;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import catalog.ui.CatalogFrame;

public class Main {
	public static Classroom clasa = new Classroom();
	public static Students stud = new Students();
	public static ArrayList<String> fau = new ArrayList<String>();
	public static void main(String[] args) {
		
		int z=1;
		for (int i = 0; i < 13; i++) {
			int j = 0;
			String fileName = "NULL";
			if(i==0)
				fileName = "Classrooms.txt";
			if(i==z){
				fileName = i+"GradeStudents.txt";
				z++;
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
					fau.add(j,line);
					
					if(i==0)
						clasa.Classrooms.add(j,line);
					if(i==z)
						stud.studentsFromGrade[i].add(j,line);
						System.out.println(stud.studentsFromGrade[i].get(j));
						
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
