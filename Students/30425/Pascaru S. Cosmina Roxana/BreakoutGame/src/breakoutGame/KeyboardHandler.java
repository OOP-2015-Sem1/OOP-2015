package breakoutGame;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {

	public String direction;

	public KeyboardHandler(Canvas canvas) {
		canvas.addKeyListener(this);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			direction = "left";
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			direction = "right";
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			direction = null;
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			direction = null;
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
