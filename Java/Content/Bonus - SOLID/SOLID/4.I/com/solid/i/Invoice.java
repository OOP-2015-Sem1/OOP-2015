package com.solid.i;

public class Invoice {
	private final int number;

	public Invoice(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return String.valueOf(this.number);
	}
}
