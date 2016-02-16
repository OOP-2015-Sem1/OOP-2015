package com.hospital;

import java.util.Random;
import java.util.UUID;

import com.hospital.disease.Disease;
import com.hospital.disease.Severity;
import com.hospital.entities.Doctor;
import com.hospital.entities.Patient;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class HospitalFactory {

	private static final int MAX_NR_DISEASES_PER_PATIENT = 10;

	public Patient createPatient() throws InvalidDataException {
		Patient patient = new Patient(createValidID(), createValidName());
		int randomNumberOfDiseases = new Random()
				.nextInt(MAX_NR_DISEASES_PER_PATIENT) + 1;
		for (int i = 0; i < randomNumberOfDiseases; i++) {
			patient.addDisease(createDisease());
		}

		return patient;
	}

	public Doctor createDoctor() throws InvalidDataException {
		return new Doctor(createValidID(), createValidName());
	}

	private Disease createDisease() {
		return new Disease(createValidName(), createValidSeverity());
	}

	private String createValidName() {
		return UUID.randomUUID().toString().substring(0, 10);
	}

	private int createValidID() {
		return 1000000 + new Random().nextInt(999999);
	}

	private Severity createValidSeverity() {
		int r = new Random().nextInt(10);
		if (r < 5) {
			return Severity.LOW;
		} else if (r < 9) {
			return Severity.MEDIUM;
		} else {
			return Severity.HIGH;
		}
	}

}
