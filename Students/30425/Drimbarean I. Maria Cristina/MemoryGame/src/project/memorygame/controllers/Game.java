package project.memorygame.controllers;

import project.memorygame.views.*;

import java.awt.Canvas;

@SuppressWarnings("serial")
public class Game extends Canvas {

	private Board b;
	private MainMenu m;
	private DifficultyMenu d;
	public WinMenu w;
	public int nrOftries;

	// CONSTRUCTOR
	public Game() {

		m = new MainMenu(Constants.WIDTH, Constants.HEIGHT, this);
	}

	// difficuly menu
	public void enterDifficultyMenu() {
		d = new DifficultyMenu(Constants.WIDTH, Constants.HEIGHT, this);
		m.setVisible(false);
	}

	// starts the game with different board
	public void enterGame(int pairs) {
		b = new Board(Constants.WIDTH,Constants.HEIGHT, this, pairs);
		d.setVisible(false);
	}

	// starts the win menu
	public void enterWinMenu() {
		w = new WinMenu(Constants.WIDTH, Constants.HEIGHT, this);
		b.setVisible(false);
	}

}
