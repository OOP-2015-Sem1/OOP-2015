package com.game.src.main.controls;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import com.game.src.main.Bullet;
import com.game.src.main.Enemy;
import com.game.src.main.Game;
import com.game.src.main.classes.Model;
import com.game.src.main.classes.Model;
import com.game.src.main.sprite.Textures;

public class Controller {

	private LinkedList<Model> ea = new LinkedList<Model>();
	private LinkedList<Model> eb = new LinkedList<Model>();

	Model enta;
	Model entb;

	Random r = new Random();
	private Game game;

	Bullet TempBullet;
	Enemy TempEnemy;

	// Game game;
	Textures tex;

	public Controller(Game game, Textures tex) {
		this.game = game;
		this.tex = tex;

		// addEntity(new
		// Enemy(r.nextInt(Game.WIDTH*Game.SCALE),0,tex,this,game));

	}

	public void createEnemy(int enemy_count) {
		for (int i = 0; i < enemy_count; i++) {
			addEntity1(new Enemy(r.nextInt(640), -10, tex, this, game));
		}
	}

	public void tick() {
		for (int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);

			enta.tick();
		}
		for (int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);

			entb.tick();
		}

	}

	public void render(Graphics g) {
		for (int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);

			enta.render(g);
		}
		for (int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);

			entb.render(g);
		}

	}

	public void addEntity(Model block) {
		ea.add(block);

	}

	public void removeEntity(Model block) {
		ea.remove(block);
	}

	public void addEntity1(Model block) {
		eb.add(block);

	}

	public void removeEntity1(Model block) {
		eb.remove(block);
	}

	public LinkedList<Model> getEntityA() {
		return ea;
	}

	public LinkedList<Model> getEntityB() {
		return eb;
	}

}
