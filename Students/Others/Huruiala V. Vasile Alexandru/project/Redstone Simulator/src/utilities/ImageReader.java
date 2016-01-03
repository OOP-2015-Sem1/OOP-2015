package utilities;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {
	private final static String MISSING_IMAGE_PATH = "MissingImage.bmp";

	public static final Image readImage(String imagePath) {
		Image image;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (Exception e) {
			try {
				image = ImageIO.read(new File(MISSING_IMAGE_PATH));
			} catch (IOException e1) {
				image = null;
			}
		}

		return image;
	}
}
