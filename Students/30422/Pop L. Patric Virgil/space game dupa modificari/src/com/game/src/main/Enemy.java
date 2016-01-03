package com.game.src.main;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.game.src.main.classes.Model;

import com.game.src.main.controls.Controller;
import com.game.src.main.sprite.GameObject;
import com.game.src.main.sprite.Textures;

public class Enemy extends GameObject implements Model {

	private Textures tex;
	Random r = new Random();
	private Game game;
	private Controller c;
	private int speed = r.nextInt(3) + 1;

	public Enemy(double x, double y, Textures tex, Controller c, Game game) {
		super(x, y);
		this.tex = tex;
		this.c = c;
		this.game = game;
	}

	public void tick() {
		y += speed;
		if (y > Game.HEIGHT * Game.SCALE) {
			y = -10;
			x = r.nextInt(640);
		}

for (int i = 0; i < game.ea.size(); i++) {
			Model tempEnt = game.ea.get(i);
			if (Physics.Collision(this, tempEnt)) {
				c.removeEntity(tempEnt);
				c.removeEntity1(this);
				Game.HEALTH -= 50;
			
			//	if (Game.HEALTH <= 0) {
				//	System.out.println(game.getEnemy_killed());
				//	game.State=game.State.END;
				//	game.END.render(Graphics g);
			//	}
				
				game.setEnemy_killed(game.getEnemy_killed() + 1);
			}
		}

	}

	public void render(Graphics g) {
		g.drawImage(tex.enemy, (int) x, (int) y, null);

	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	@Override
	public double getX() {

		return x;
	}

	public double getY() {

		return y;
	}

}
