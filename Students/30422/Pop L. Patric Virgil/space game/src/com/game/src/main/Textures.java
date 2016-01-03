package com.game.src.main;

import java.awt.image.BufferedImage;

public class Textures {
	
	public BufferedImage player,bullet,enemy;
	
	private SpriteSheet ss;
	
	public Textures(Game game){
		 ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures(){
		player = ss.grabImage(1, 1, 32, 32);
		bullet = ss.grabImage(2, 1, 32, 32);
		enemy = ss.grabImage(3, 1, 32, 32);
		
	}

}
