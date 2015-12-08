package com.solid.l.good;

public class GoodRectangle extends Shape {
	private int height;
	private int width;

	public GoodRectangle(int height, int width) {
		this.height = height;
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getArea() {
		return this.height * this.width;
	}

}
