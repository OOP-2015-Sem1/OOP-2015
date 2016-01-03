package controller;

import java.awt.event.KeyEvent;

import core.GameManagerImpl;
import graphics.GameDrawer;
import model.Bomberman;
import model.Constants;

public class ControlPlayer extends Thread {
	private Bomberman bomberman;
	private GameManagerImpl gmi;
	private GameDrawer gd;
	private boolean upKeyPressed = false;
	private boolean downKeyPressed = false;
	private boolean leftKeyPressed = false;
	private boolean rightKeyPressed = false;
	private boolean dropBombKeyPressed = false;
	private boolean enable = false;

	private int fire;
	private int left;
	private int up;
	private int right;
	private int down;

	public ControlPlayer(GameDrawer gd, GameManagerImpl gmi, int[] key) {
		this.gmi = gmi;
		bomberman = gmi.getBomberman();
		this.gd = gd;
		fire = key[0];
		left = key[1];
		up = key[2];
		right = key[3];
		down = key[4];
	}

	/*
	 * if an action key is pressed, a boolean is initialised in such a way that
	 * while it is true it's doing the action that the pressed button is set to
	 * execute
	 **/
	public void keyPressed(KeyEvent e) {

		System.out.println(e.getKeyCode());
		if (e.getKeyCode() == fire) {
			dropBombKeyPressed = true;
			interrupt();
		}

		if (e.getKeyCode() == left) {

			leftKeyPressed = true;
			enable = true;
			interrupt();
		}

		if (e.getKeyCode() == up) {
			upKeyPressed = true;
			enable = true;
			interrupt();
		}

		if (e.getKeyCode() == right) {
			rightKeyPressed = true;
			enable = true;
			interrupt();
		}

		if (e.getKeyCode() == down) {
			downKeyPressed = true;
			enable = true;
			interrupt();
		}

	}

	/*
	 * if an action key is released the coressponding boolean becomes false end
	 * the action is not executed any more
	 **/
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == fire) {
			dropBombKeyPressed = false;
			interrupt();

		}

		if (e.getKeyCode() == left) {
			leftKeyPressed = false;
			enable = true;
			interrupt();
		}
		if (e.getKeyCode() == up) {
			upKeyPressed = false;
			enable = true;
			interrupt();
		}

		if (e.getKeyCode() == right) {
			rightKeyPressed = false;
			enable = true;
			interrupt();
		}
		if (e.getKeyCode() == down) {
			downKeyPressed = false;
			enable = true;
			interrupt();
		}

	}

	/*
	 * here we query the value of the booleans and according to theese we order
	 * the bombermen to perform various actions
	 **/
	public void run() {
		while (bomberman.isAlive() == true) {
			if (dropBombKeyPressed)
				bomberman.dropBomb();
			if (enable == true) {

				if (leftKeyPressed) {
					if (bomberman.getLastStatus() != Constants.STATUS_MOVING_LEFT)
						bomberman.startMovingLeft();
					else
						bomberman.updateStatus(Constants.STATUS_MOVING_LEFT);
				}

				if (downKeyPressed) {
					if (bomberman.getLastStatus() != Constants.STATUS_MOVING_DOWN)
						bomberman.startMovingDown();
					else
						bomberman.updateStatus(Constants.STATUS_MOVING_DOWN);
				}

				if (upKeyPressed) {
					if (bomberman.getLastStatus() != Constants.STATUS_MOVING_UP)
						bomberman.startMovingUp();
					else
						bomberman.updateStatus(Constants.STATUS_MOVING_UP);
				}

				if (rightKeyPressed) {
					if (bomberman.getLastStatus() != Constants.STATUS_MOVING_RIGHT)
						bomberman.startMovingRight();
					else
						bomberman.updateStatus(Constants.STATUS_MOVING_RIGHT);
				}
				if (!(leftKeyPressed || downKeyPressed || upKeyPressed || rightKeyPressed)) {
					bomberman.updateStatus(Constants.STATUS_STANDING);
				}
				enable = false;
			}
			gmi.refreshBomberman();
			gd.repaint();

			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
		}
	}

}
