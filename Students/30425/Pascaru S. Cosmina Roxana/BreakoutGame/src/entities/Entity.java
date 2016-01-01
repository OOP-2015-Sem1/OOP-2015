package entities;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Entity {

	public Rectangle surface;

	public abstract void paintComponent(Graphics g);

	public abstract void update();
}
