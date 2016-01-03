package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gui.Game;
import gui.Login;

public class TetrisController implements KeyListener {

	public Game game;

	public TetrisController() {
		game = new Game();
		game.getGame().addKeyListener(this);
	}
	public Game getGame(){
		return game;
	}
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		switch (keyCode) {
		case KeyEvent.VK_UP:
			if (Main.gameOver == false)
				if (Login.start == true)
					Move.shift();
			break;
		case KeyEvent.VK_DOWN:
			if (Main.gameOver == false)
				if (Login.start == true)
					Move.moveDown();
			break;
		case KeyEvent.VK_LEFT:
			if (Main.gameOver == false)
				if (Login.start == true)
					Move.moveLeft();
			break;
		case KeyEvent.VK_RIGHT:
			if (Main.gameOver == false)
				if (Login.start == true)
					Move.moveRight();
			break;
		}
	}

	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
