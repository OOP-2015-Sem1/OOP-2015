package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public abstract class Entity {
	
	public Rectangle entityCollider;
	
	public abstract void paintComponent(Graphics g);
	
	public abstract void update();
}
