package controller;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Controller extends JComponent implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<ControlPlayer> playerList;

	public Controller() {
		playerList = new ArrayList<ControlPlayer>();
		addKeyListener(this);
	}

	/*
	 * adds a control over a player
	 **/
	public void addControlPlayer(ControlPlayer c) {
		playerList.add(c);
	}

	/*
	 * removes a control over a player
	 **/
	public void removeControlPlayer(ControlPlayer c) {
		playerList.remove(c);
	}

	/*
	 * transmits the keyevents when a key is pressed
	 **/
	public void keyPressed(KeyEvent e) {
		System.out.println(e.getKeyCode());
		for (ControlPlayer c : playerList)
			c.keyPressed(e);
	}

	/*
	 * transmits the keyevents when a key is released
	 **/
	public void keyReleased(KeyEvent e) {
		for (ControlPlayer c : playerList)
			c.keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
	}
}
