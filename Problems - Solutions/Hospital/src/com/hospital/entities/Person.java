package com.hospital.entities;

import com.hospital.InvalidDataException;

public class Person {

	private static final int ID_LENGTH = 7;
	private static final int MIN_NAME_LEGNTH = 6;

	private int ID;
	private String name;

	public Person(int ID, String name) throws InvalidDataException {
		setID(ID);
		setName(name);
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) throws InvalidDataException {
		int idLength = String.valueOf(ID).length();
		if (idLength != ID_LENGTH) {
			throw new InvalidDataException(
					String.format(
							"The id was not %d characters long, but in fact it was %d!\n",
							ID_LENGTH, idLength));
		}
		this.ID = ID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws InvalidDataException {
		if (name == null || (name.length() < MIN_NAME_LEGNTH))
			throw new InvalidDataException(String.format(
					"The name %s was invalid!", name));
		this.name = name;
	}

}
