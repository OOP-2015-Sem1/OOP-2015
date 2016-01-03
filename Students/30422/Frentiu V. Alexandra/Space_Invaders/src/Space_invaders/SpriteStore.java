package Space_invaders;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class SpriteStore {
	private static SpriteStore ss = new SpriteStore();
	public static SpriteStore get()
	{ return ss;
	}
	private HashMap sprites  = new HashMap();
	 //param ref- The reference to the image to use for the sprite
	 //return A- sprite instance containing an accelerate image of the request reference
	public Sprite getSprite(String ref)
	{
		if(sprites.get(ref)!= null)
			return (Sprite) sprites.get(ref);
	
	BufferedImage sourceImage = null;
	try
	{
		URL url = this.getClass().getClassLoader().getResource(ref);
		if(url ==null)
			fail("Can't find ref:+ ref");
		// use ImageIO to read the image in
	sourceImage = ImageIO.read(url);
	}
	catch (IOException e)
	{	fail("Failed to load: "+ref);
	}
	// create an accelerated image of the right size to store our sprite in
			GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
			Image image = gc.createCompatibleImage(sourceImage.getWidth(),sourceImage.getHeight(),Transparency.BITMASK);
			
			// draw our source image into the accelerated image
			image.getGraphics().drawImage(sourceImage,0,0,null);
			Sprite sprite = new Sprite(image);
			 sprites.put(ref,sprite);
			 return sprite;
	
}//if unavailable resource dump message and abort game
	//failure measures
	private void fail(String message)
	{System.err.println(message);
	System.exit(0);
		
	}
}
