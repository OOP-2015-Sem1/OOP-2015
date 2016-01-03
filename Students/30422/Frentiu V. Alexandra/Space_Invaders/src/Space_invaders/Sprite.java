package Space_invaders;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
private Image image;
public Sprite(Image image)
{
	this.image = image;
	
}
public int getWidth() {
	return image.getWidth(null);
}


  //Get the height of the drawn sprite in pixels
 
public int getHeight() {
	return image.getHeight(null);
}
public void draw(Graphics g, int x, int y)
{
	g.drawImage(image,x,y,null);
	
	//line for more appealing
	g.setColor(Color.GREEN);
	g.drawLine(0,544, 800,544);
}
}
