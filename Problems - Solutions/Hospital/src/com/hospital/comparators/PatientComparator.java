package com.hospital.comparators;

import java.util.Comparator;

import com.hospital.entities.Patient;

public class PatientComparator implements Comparator<Patient> {

	@Override
	public int compare(Patient p1, Patient p2) {
		return Integer
				.compare(p1.getDiseases().size(), p2.getDiseases().size());
	}

}
