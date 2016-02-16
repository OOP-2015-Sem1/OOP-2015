package com.hospital.entities;

import java.util.HashSet;
import java.util.Set;

import com.hospital.InvalidDataException;
import com.hospital.disease.Disease;

public class Patient extends Person {

	private Set<Disease> diseases;

	public Patient(int ID, String name) throws InvalidDataException {
		super(ID, name);
		this.diseases = new HashSet<Disease>();
	}

	public void addDisease(Disease disease) {
		diseases.add(disease);
	}

	public boolean cureDisease(Disease disease) {
		if (diseases.contains(disease)) {
			if (disease.cure()) {
				diseases.remove(disease);
				return true;
			}
		}
		return false;
	}

	public Set<Disease> getDiseases() {
		return this.diseases;
	}

	public String toString() {
		return String
				.format("I am a patient with the ID %d and my name is %s and I have %d diseases",
						getID(), getName(), diseases.size());
	}

}
