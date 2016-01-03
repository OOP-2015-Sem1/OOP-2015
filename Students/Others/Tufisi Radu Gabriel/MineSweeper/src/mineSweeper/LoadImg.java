package mineSweeper;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadImg {

	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(LoadImg.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedImage scale(BufferedImage source, int width, int height) {
       BufferedImage scaled= new BufferedImage(width, height,source.getType());
       Graphics	g = scaled.getGraphics();
       g.drawImage(source,0 ,0,width,height,null);
       g.dispose();
       return scaled;
	}
}
