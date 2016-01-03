package model.users;

import java.util.ArrayList;

import model.studentfields.Absence;
import model.studentfields.Mark;
import model.utils.StudentClass;
import model.utils.Subject;

public class Teacher {
	private Subject[] subjects;
	private StudentClass[] classes;
	private Credential credential;
	
	public Teacher(Credential credential) {
		this.credential = credential;
	}

	
	public void setSubjects(String[] strings) {
		Subject[] subjects = new Subject[strings.length];
		for (int i = 0; i < subjects.length; i++) {
			subjects[i] = new Subject(strings[i]);
		}
		this.subjects = subjects;
	}
	
	public void setClasses(String[] strings) {
		StudentClass[] classes = new StudentClass[strings.length];
		for (int i = 0; i < classes.length; i++) {
			classes[i] = new StudentClass(strings[i]);
		}
		this.classes = classes;
	}
	
	public String[] getClasses() {
		String[] classesS = new String[classes.length];
		for (int i = 0; i < classesS.length; i++) {
			classesS[i] = classes[i].getName();
		}
		return classesS;
	}
	
	public String[] getSubjects() {
		String[] classesS = new String[subjects.length];
		for (int i = 0; i < classesS.length; i++) {
			classesS[i] = subjects[i].getName();
		}
		return classesS;
	}
	public Credential getCredential() {
		return this.credential;
	}
	
	
}
