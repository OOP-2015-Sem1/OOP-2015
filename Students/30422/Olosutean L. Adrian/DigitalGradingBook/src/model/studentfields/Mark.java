package model.studentfields;

import java.util.Date;

import model.utils.Subject;

public class Mark extends StudentField{
	short grading;
	
	public Mark(short grading, String date, String subject) {
		super(subject, date);
		
		setGrading(grading);
		
	}
	
	public void setGrading(short grading) {
		this.grading = grading;
		
	}
	
}
