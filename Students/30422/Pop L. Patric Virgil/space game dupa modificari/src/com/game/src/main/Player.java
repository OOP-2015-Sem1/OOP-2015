package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.ref.PhantomReference;

import com.game.src.main.classes.Model;
import com.game.src.main.classes.Model;
import com.game.src.main.controls.Controller;
import com.game.src.main.sprite.GameObject;
import com.game.src.main.sprite.Textures;

public class Player extends GameObject implements Model {

	private double velX;
	private double velY;

	private BufferedImage player;

	private Textures tex;
	Game game;
	Controller c;

	public Player(double x, double y, Textures tex, Game game, Controller c) {

		super(x, y);
		this.tex = tex;
		this.game = game;
		this.c = c;

	}

	public void tick() {
		x += velX;
		y += velY;

		if (x <= 0)
			x = 0;
		if (x >= 640 - 22)
			x = 640 - 22;
		if (y <= 0)
			y = 0;
		if (y >= 480 - 32)
			y = 480 - 32;
		
		
		
//		for(int i=0;i< game.eb.size();i++){
//			
//			
//
//			Model tempEnt = game.eb.get(i);
//			
//			System.out.println(tempEnt.getX()+" "+tempEnt.getY());
//		
//			if(Physics.Collision(this, tempEnt))
//			{
//				
//				c.removeEntity(tempEnt);
//				Game.HEALTH-=10;
//			}
//		}
//		 for(int i=0;i<game.eb.size();i++){
//		 Model tempEnt=game.eb.get(i);
//		 if(Physics.Collision(this, tempEnt))
//		 {
//		 c.removeEntity(tempEnt);
//		 c.removeEntity(this);
//		 game.setEnemy_killed(game.getEnemy_killed()+1);
//		 }
//		 }
//
	}

	public void render(Graphics g) {
		g.drawImage(tex.player, (int) x, (int) y, null);

	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setVelX(double velX) {
		this.velX = velX;
	}

	public void setVelY(double velY) {
		this.velY = velY;
	}

	@Override
	public Rectangle getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
}
