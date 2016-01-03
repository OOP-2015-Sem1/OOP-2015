package com.game.src.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyImput extends KeyAdapter{
	
	Game game;
	
	
	public KeyImput(Game game){
		this.game=game;
	}
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	}
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}
	

}
