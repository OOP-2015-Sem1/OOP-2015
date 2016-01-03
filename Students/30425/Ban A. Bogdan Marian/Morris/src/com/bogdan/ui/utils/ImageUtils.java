package com.bogdan.ui.utils;

import javax.swing.ImageIcon;

public class ImageUtils {
	public static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = ImageUtils.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find image: " + path);
			return null;
		}
	}
}
