package com.solid.l.bad;

public class BadGraphicsService {

	public void checkForArea(BadRectangle rectangle) {
		int height = 20;
		int width = 5;
		rectangle.setHeight(height);
		rectangle.setWidth(width);

		if (rectangle.getArea() == (width * height)) {
			System.out.println("Great rectangle you got there!");
		} else {
			System.out.println("Well, not a rectangle.");
		}
	}
}
