package theseal.model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Ice implements Drawable {
	private static final String IMAGE_PATH = "ice.png";
	private Image image;

	private static Ice iceInstance = new Ice();

	private Ice() {
		try {
			image = ImageIO.read(new File(IMAGE_PATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getImage() {
		return this.image;
	}

	public static Ice getInstance() {
		return iceInstance;
	}
}
