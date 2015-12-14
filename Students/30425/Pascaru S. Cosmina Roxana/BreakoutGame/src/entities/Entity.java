package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import breakoutGame.Game;

public abstract class Entity {
	
	Rectangle entityCollider;
	Game game;

	public abstract void paintComponent(Graphics g);
	
	public abstract void update();
}
