package catalog.brain;

import java.util.ArrayList;

public class Classroom{
	static public ArrayList[] GradeWithStudents = new ArrayList[20];
	Classroom(){
		
		for(int i=0; i<20; i++)
		{
			GradeWithStudents[i] = new ArrayList<Students>();
		}
	}
}
