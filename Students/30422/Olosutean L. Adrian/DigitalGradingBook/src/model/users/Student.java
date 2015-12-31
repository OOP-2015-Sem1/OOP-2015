package model.users;

import java.util.ArrayList;

import model.studentfields.Absence;
import model.studentfields.Mark;
import model.utils.StudentClass;

public class Student{
	
	private Credential credential;
	public ArrayList<Mark> marks = new ArrayList<>();
	public ArrayList<Absence> absences = new ArrayList<>();
	
	public Student(Credential credential) {
		this.credential = credential;
		
	}
	public Credential getCredential() {
		return credential;
	}

}
