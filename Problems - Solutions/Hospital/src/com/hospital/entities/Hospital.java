package com.hospital.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hospital.comparators.DoctorComparator;
import com.hospital.comparators.PatientComparator;

public class Hospital {

	private Set<Doctor> doctors;
	private Set<Patient> patients;

	public Hospital() {
		this.doctors = new HashSet<Doctor>();
		this.patients = new HashSet<Patient>();
	}

	public void addDoctor(Doctor doctor) {
		doctors.add(doctor);
	}

	public void addPatient(Patient patient) {
		patients.add(patient);
	}

	public void startHealing() {
		for (Doctor doctor : doctors) {
			for (Patient patient : patients) {
				doctor.curePatient(patient);
			}
		}
	}

	public void showPatientsSortedByNumberOfDiseases() {
		List<Patient> sortedPatients = new ArrayList<Patient>();
		sortedPatients.addAll(patients);

		Collections.sort(sortedPatients, new PatientComparator());
		printEntities(sortedPatients);
	}

	public void showDoctorsSortedByNumberofDiseasesCured() {
		List<Doctor> sortedDoctors = new ArrayList<Doctor>();
		sortedDoctors.addAll(doctors);

		Collections.sort(sortedDoctors, new DoctorComparator());
		printEntities(sortedDoctors);
	}

	public void showStatistics() {
		int numberOfPatientsWhichAreStillSick = computeNumberOfSickPatients();
		int numberOfDiseasesStillAffectingPatients = computerNumberOfDiseasesUncured();
		System.out
				.println(String
						.format("There are %d patients which still require medical attention and %d diseases not yet cured!\n",
								numberOfPatientsWhichAreStillSick,
								numberOfDiseasesStillAffectingPatients));
	}

	private int computeNumberOfSickPatients() {
		int numberOfSickPatients = 0;
		for (Patient p : patients) {
			if (p.getDiseases().size() > 0)
				numberOfSickPatients += 1;
		}
		return numberOfSickPatients;
	}

	private int computerNumberOfDiseasesUncured() {
		int numberOfDiseasesUncured = 0;
		for (Patient p : patients) {
			numberOfDiseasesUncured += p.getDiseases().size();
		}
		return numberOfDiseasesUncured;
	}

	private void printEntities(List<? extends Person> entities) {
		for (Person p : entities) {
			System.out.println(p);
		}
		System.out.println();
	}
}
