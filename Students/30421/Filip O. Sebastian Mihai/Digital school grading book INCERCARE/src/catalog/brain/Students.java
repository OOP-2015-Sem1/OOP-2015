package catalog.brain;

import java.util.ArrayList;

public class Students {
	static public ArrayList[] studentsFromGrade = new ArrayList[13];
	static public int[] NrOfStudents = new int[14];
	Students(){
		for(int i=1;i<14;i++)
			NrOfStudents[i]=35;
		for(int i=0; i<NrOfStudents[i]; i++)
		{
			studentsFromGrade[i] = new ArrayList<String>();
			
		}
	}
}
