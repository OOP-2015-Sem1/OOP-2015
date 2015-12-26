package theGame;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

	private Handler handler;
	private boolean[] keyDown = new boolean[8];

	public KeyInput(Handler handler) {
		this.handler = handler;

		keyDown[0] = false;
		keyDown[1] = false;
		keyDown[2] = false;
		keyDown[3] = false;
		keyDown[4] = false;
		keyDown[5] = false;
		keyDown[6] = false;
		keyDown[7] = false;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.allObjects.size(); i++) {
			GameObjects tempObject = handler.allObjects.get(i);

			if (tempObject.getObjectID() == ObjectID.Player) {
				// Key events for player1
				// up
				if (key == KeyEvent.VK_W) {
					tempObject.setSpeedY(-10);
					keyDown[0] = true;
				}
				// down
				if (key == KeyEvent.VK_S) {
					tempObject.setSpeedY(10);
					keyDown[1] = true;
				}
				// left
				if (key == KeyEvent.VK_A) {
					tempObject.setSpeedX(-10);
					keyDown[2] = true;
				}
				// right
				if (key == KeyEvent.VK_D) {
					tempObject.setSpeedX(10);
					keyDown[3] = true;
				}
			}

			if (tempObject.getObjectID() == ObjectID.Player2) {
				// Key events for player2
				// up
				if (key == KeyEvent.VK_UP) {
					tempObject.setSpeedY(-10);
					keyDown[4] = true;
				}
				// down
				if (key == KeyEvent.VK_DOWN) {
					tempObject.setSpeedY(10);
					keyDown[5] = true;
					;
				}
				// left
				if (key == KeyEvent.VK_LEFT) {
					tempObject.setSpeedX(-10);
					keyDown[6] = true;
				}
				// right
				if (key == KeyEvent.VK_RIGHT) {
					tempObject.setSpeedX(10);
					keyDown[7] = true;
					;
				}
			}
		}

	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();

		for (int i = 0; i < handler.allObjects.size(); i++) {
			GameObjects tempObject = handler.allObjects.get(i);

			if (tempObject.getObjectID() == ObjectID.Player) {
				// Key events for player1
				// up
				if (key == KeyEvent.VK_W)
					keyDown[0] = false;
				// down
				if (key == KeyEvent.VK_S)
					keyDown[1] = false;
				// left
				if (key == KeyEvent.VK_A)
					keyDown[2] = false;
				// right
				if (key == KeyEvent.VK_D)
					keyDown[3] = false;
				// vertical movement
				if (!keyDown[0] && !keyDown[1])
					tempObject.setSpeedY(0);
				// horizontal movement
				if (!keyDown[2] && !keyDown[3])
					tempObject.setSpeedX(0);
			}

			if (tempObject.getObjectID() == ObjectID.Player2) {
				// Key events for player2
				// up
				if (key == KeyEvent.VK_UP)
					keyDown[4] = false;
				// down
				if (key == KeyEvent.VK_DOWN)
					keyDown[5] = false;
				// left
				if (key == KeyEvent.VK_LEFT)
					keyDown[6] = false;
				// right
				if (key == KeyEvent.VK_RIGHT)
					keyDown[7] = false;
				// vertical movement
				if (!keyDown[4] && !keyDown[5])
					tempObject.setSpeedY(0);
				// horizontal movement
				if (!keyDown[6] && !keyDown[7])
					tempObject.setSpeedX(0);
			}

			if (key == KeyEvent.VK_ESCAPE)
				System.exit(1);
		}
	}
}
