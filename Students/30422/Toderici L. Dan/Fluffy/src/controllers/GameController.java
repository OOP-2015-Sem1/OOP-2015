package controllers;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import models.Fluffy;
import models.Game;
import views.GameView;

public class GameController implements KeyEventDispatcher{
	
	private Game gameBackEnd;
	private GameView gameFrontEnd;
	private Fluffy fluffy;
	
	public GameController()
	{
		gameBackEnd = new Game();
		this.fluffy = gameBackEnd.getFluffy();
		gameFrontEnd = new GameView(gameBackEnd.getGameMatrix(), gameBackEnd.getCluesMatrix(),gameBackEnd.getFluffy());
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {

		if (e.getID() == KeyEvent.KEY_PRESSED) {
			int key = e.getKeyCode();
			switch (key) {
			case KeyEvent.VK_UP:
				gameFrontEnd.move(fluffy, -1, 0);
				break;
			case KeyEvent.VK_DOWN:
				gameFrontEnd.move(fluffy, 1, 0);
				break;
			case KeyEvent.VK_LEFT:
				gameFrontEnd.move(fluffy, 0, -1);
				break;
			case KeyEvent.VK_RIGHT:
				gameFrontEnd.move(fluffy, 0, 1);
				break;
			}
		}
		this.gameFrontEnd.keepYellowClues();

		gameFrontEnd.repaint();
		gameFrontEnd.revalidate();
		return false;
	}
	
	

}
