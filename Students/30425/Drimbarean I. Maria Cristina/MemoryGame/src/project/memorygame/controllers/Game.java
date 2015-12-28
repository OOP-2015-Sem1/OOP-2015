package project.memorygame.controllers;

import project.memorygame.views.*;

import java.awt.Canvas;

@SuppressWarnings("serial")
public class Game extends Canvas {

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

	private Board b;
	private MainMenu m;
	private DifficultyMenu d;
	public WinMenu w;
	public int nrOftries;

	// CONSTRUCTOR
	public Game() {

		m = new MainMenu(WIDTH, HEIGHT, this);
	}

	// difficuly menu
	public void enterDifficultyMenu() {
		d = new DifficultyMenu(WIDTH, HEIGHT, this);
		m.setVisible(false);
	}

	// starts the game with different board
	public void enterGame(int pairs) {
		b = new Board(WIDTH, HEIGHT, this, pairs);
		d.setVisible(false);
	}

	// starts the win menu
	public void enterWinMenu() {
		w = new WinMenu(WIDTH, HEIGHT, this);
		b.setVisible(false);
	}

}
