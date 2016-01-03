package catalog.brain;

import java.util.ArrayList;

public class Classroom {
	@SuppressWarnings("rawtypes")
	public ArrayList[] GradeWithStudents = new ArrayList[20];
	public static ArrayList<String> Classrooms = new ArrayList<String>();

	Classroom() {
		for (int i = 0; i < 20; i++) {
			GradeWithStudents[i] = new ArrayList<Students>();
		}
	}
}
