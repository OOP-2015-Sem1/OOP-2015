package com.hospital.comparators;

import java.util.Comparator;

import com.hospital.entities.Doctor;

public class DoctorComparator implements Comparator<Doctor> {

	@Override
	public int compare(Doctor d1, Doctor d2) {
		return Integer.compare(d1.getNumberOfDiseasesCured(),
				d2.getNumberOfDiseasesCured());
	}

}
