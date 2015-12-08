package com.solid.l.bad;

public class BadSquare extends BadRectangle {

	public BadSquare(int height, int width) {
		super(height, width);
	}

	public void setHeight(int height) {
		super.setHeight(height);
		super.setWidth(height);
	}

	public void setWidth(int width) {
		super.setWidth(width);
		super.setHeight(width);
	}

	@Override
	public String toString() {
		return "I am a square";
	}
}
