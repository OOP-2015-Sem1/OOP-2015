package catalog.brain;

import java.util.ArrayList;

public class Students {
	static public ArrayList[] studentsFromGrade = new ArrayList[13];
	static public ArrayList<String> Classrooms = new ArrayList<String>();
	static public int[] NrOfStudents = new int[14];
	Students(){
		for(int i=1;i<13;i++)
			NrOfStudents[i]=20;
		for(int i=0; i<13; i++)
		{
			studentsFromGrade[i] = new ArrayList<String>();
			for(int j=0;j<NrOfStudents[i];j++){
				studentsFromGrade[i].add(j,"EU");
			}
			
		}
	}
}
