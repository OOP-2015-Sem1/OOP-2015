package UI;
import java.awt.image.BufferedImage;

import model.Game;

public class Texture {

	public static BufferedImage player;
	public static BufferedImage ghost;
	
	public Texture(Game game){	
		
		player = game.spritesheet.getSprite(0, 0);
		ghost = game.spritesheet.getSprite(0, 16);
	}
	
}
