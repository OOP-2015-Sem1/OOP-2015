package com.hospital;

import com.hospital.entities.Hospital;

/**
 ******************************************************************************************************
 * EVERYTHING FROM THIS CLASS WOULD NOT HAVE BEEN TAKEN INTO CONSIDERATION IN THE THE GRADING PROCESS.*
 ******************************************************************************************************
 */
public class Main {

	private static final int NUMBER_OF_DOCTORS = 7;
	private static final int NUMBER_OF_PATIENTS = 50;

	public static void main(String[] args) throws InvalidDataException {
		Hospital hospital = new Hospital();
		HospitalFactory hospitalFactory = new HospitalFactory();

		for (int i = 0; i < NUMBER_OF_DOCTORS; i++) {
			hospital.addDoctor(hospitalFactory.createDoctor());
		}

		for (int i = 0; i < NUMBER_OF_PATIENTS; i++) {
			hospital.addPatient(hospitalFactory.createPatient());
		}

		hospital.showStatistics();

		hospital.startHealing();

		hospital.showDoctorsSortedByNumberofDiseasesCured();
		hospital.showPatientsSortedByNumberOfDiseases();

		hospital.showStatistics();

	}
}
