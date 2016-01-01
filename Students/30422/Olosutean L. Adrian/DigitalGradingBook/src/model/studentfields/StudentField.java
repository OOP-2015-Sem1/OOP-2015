package model.studentfields;

import java.util.Date;

import model.utils.Subject;

public abstract class StudentField {
	protected String date;
	protected Subject subject;
	
	public StudentField(String subject, String date) {
		setSubject(subject);
		setDate(date);
		
	}
	
	private void setDate(String date) {
		this.date = date;
	}
	
	private void setSubject(String name) {
		Subject newsubject = null;
		try {
			newsubject = new Subject(name);
			
		} catch (Exception e) {
			
		}
		finally{
			this.subject = newsubject;
		}
	}
	
	public String getDate() {
		return date;
	}
	
	public String getSubject() {
		return subject.getName();
	}
	
	public boolean equals(StudentField that) {
		if(this.date.equals(that.date)  && this.subject.equals(that.subject))
			return true;
		else
			return false;
	}
	
}
