package breakoutGame;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener{

	
	public boolean left, right;
	
	public KeyboardHandler(Canvas canvas){
		canvas.addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			right = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
