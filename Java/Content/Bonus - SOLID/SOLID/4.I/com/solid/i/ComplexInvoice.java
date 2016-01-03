package com.solid.i;

import java.util.Date;

public class ComplexInvoice {
	private final int number;
	private final Date dateCreated;

	public ComplexInvoice(int number, Date dateCreated) {
		this.number = number;
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return String.valueOf(this.number) + " " + dateCreated;
	}
}
