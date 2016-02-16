package com.hospital.disease;

import java.util.Random;

public class Disease {

	private String name;
	private Severity severity;

	public Disease(String name, Severity severity) {
		this.name = name;
		this.severity = severity;
	}

	public boolean cure() {
		int chances = new Random().nextInt(100);
		if (severity.equals(Severity.LOW)) {
			return chances < 75;
		} else if (severity.equals(Severity.MEDIUM)) {
			return chances < 50;
		} else if (severity.equals(Severity.HIGH)) {
			return chances < 25;
		} else {
			return false;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

}
