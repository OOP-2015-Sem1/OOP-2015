package com.hospital.entities;

import java.util.Random;
import java.util.Set;

import com.hospital.InvalidDataException;
import com.hospital.disease.Disease;

public class Doctor extends Person {

	private int numberOfDiseasesCured;

	public Doctor(int ID, String name) throws InvalidDataException {
		super(ID, name);
		this.numberOfDiseasesCured = 0;
	}

	public void curePatient(Patient patient) {
		Set<Disease> diseases = patient.getDiseases();
		int numberOfDiseases = diseases.size();
		int numberOfDiseasesToAttemptToCure = new Random()
				.nextInt(numberOfDiseases + 1);
		for (int i = 0; i < numberOfDiseasesToAttemptToCure; i++) {
			if (patient.cureDisease(diseases.iterator().next())) {
				numberOfDiseasesCured += 1;
			}
		}
	}

	public int getNumberOfDiseasesCured() {
		return numberOfDiseasesCured;
	}

	public String toString() {
		return String
				.format("I am a doctor with the ID %d and my name is %s and I have cured %d diseases!",
						getID(), getName(), numberOfDiseasesCured);
	}

}
