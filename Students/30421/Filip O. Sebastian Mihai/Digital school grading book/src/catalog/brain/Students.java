package catalog.brain;

import java.util.ArrayList;
public class Students {
	@SuppressWarnings("rawtypes")
	public static ArrayList[] studentsFromGrade = new ArrayList[13];

	Students() {
		for (int i = 0; i < 13; i++) {
			studentsFromGrade[i] = new ArrayList<String>();
		}
	}
}
