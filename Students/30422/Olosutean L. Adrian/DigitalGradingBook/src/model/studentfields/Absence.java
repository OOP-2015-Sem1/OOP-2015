package model.studentfields;

import java.util.Date;

import model.users.Student;
import model.utils.Subject;

public class Absence extends StudentField{
	private boolean isMotivated;
	private Student student;
	public Absence(String subject, String date) {
		super(subject, date);
		
	}
	public Absence(String subject, String date, Student student) {
		this(subject,date);
		this.student = student;
	}
	public void setMotivated(boolean motivated){
		this.isMotivated = motivated;
	}
	
	public boolean getIfMotivated(){
		return isMotivated;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public Student getStudent() {
		return student;
	}
	
	
}
