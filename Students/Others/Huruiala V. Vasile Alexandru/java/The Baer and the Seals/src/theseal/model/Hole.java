package theseal.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Hole extends Entity {
	private static final String IMAGE_PATH = "hole.png";

	public Hole(Point position) {
		super(position);
		try {
			super.setImage(ImageIO.read(new File(IMAGE_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
