package theGame;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObjects> allObjects = new LinkedList<GameObjects>();
	boolean reset = false;

	public void tick() {
		for (int i = 0; i < allObjects.size(); i++) {
			GameObjects tempGameObjects = allObjects.get(i);

			tempGameObjects.tick();
		}

		verifyColision();
		verifyScore();

	}

	private void verifyScore() {

		// TODO Auto-generated method stub
		Ball ball = (Ball) allObjects.get(0);
		if (reset == false) {
			if (ball.getX() >= 891 && ball.getY() >= 190 && ball.getY() <= 380) {
				Score.SCORE1++;
				reset();

				// player1 score
			} else if (ball.getX() <= 1 && ball.getY() >= 190 && ball.getY() <= 380) {
				Score.SCORE2++;
				reset();

				// player2 scored
			}
		}
	}

	private void reset() {

		Ball ball = (Ball) allObjects.get(0);
		GameObjects player = allObjects.get(1);
		GameObjects player2 = allObjects.get(2);
		//---remove
		removeObjects(ball);
		removeObjects(player);
		removeObjects(player2);
		
		
		//---readd
		addObject(new Ball(MainGame.WIDTH / 2 - 50, MainGame.HEIGHT / 2 - 50, ObjectID.Ball));
		addObject(new Player(50, MainGame.HEIGHT / 2 - 70, ObjectID.Player));
		addObject(new Player2(MainGame.WIDTH - 170, MainGame.HEIGHT / 2 - 70, ObjectID.Player2));
	}

	private void verifyColision() {
		// TODO Auto-generated method stub

		Ball ball = (Ball) allObjects.get(0);
		GameObjects player = allObjects.get(1);
		GameObjects player2 = allObjects.get(2);

		collision(player, ball);
		collision(player2, ball);

	}

	public void collision(GameObjects player, Ball ball) {

		int partX = player.gCenterPX(player.getX(), MainGame.WIDTH) - ball.gCenterPX(ball.getX(), MainGame.WIDTH);
		int partY = ball.gCenterPY(player.getY(), MainGame.HEIGHT) - ball.gCenterPY(ball.getY(), MainGame.HEIGHT);

		int distanceBetCenters = (int) Math.sqrt(Math.pow(partX, 2) + Math.pow(partY, 2));

		if (distanceBetCenters < 60) {
			// ball.setAngle((float) Math.atan2((ball.getY() +13) -
			// (player.getY()+25), (ball.getX()+13) - (player.getX()+25)));
			ball.collided = true;

			if (player.getSpeedX() == 0 && player.getSpeedY() == 0) {
				ball.setSpeedX((int) (ball.getSpeedX() * -1 + Math.atan((ball.getX() + 13) - (player.getX() + 25))));
				ball.setSpeedY((int) (ball.getSpeedY() * -1 + Math.atan((ball.getY() + 13) - (player.getY() + 25))));
			} else {
				ball.setSpeedX((int) (player.getSpeedX() + Math.atan((ball.getX() + 13) - (player.getX() + 25))));
				ball.setSpeedY((int) (player.getSpeedY() + Math.atan((ball.getY() + 13) - (player.getY() + 25))));
			}

		}

	}

	public void render(Graphics graph) {
		for (int i = 0; i < allObjects.size(); i++) {
			GameObjects tempGameObjects = allObjects.get(i);

			tempGameObjects.render(graph);
		}
	}

	public void addObject(GameObjects allObjects) {
		this.allObjects.add(allObjects);
	}

	public void removeObjects(GameObjects allObjects) {
		this.allObjects.remove(allObjects);
	}
}
