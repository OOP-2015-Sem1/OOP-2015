package theGame;

import java.awt.Graphics;
import java.util.LinkedList;

import display.Score;
import inputDevices.Audio;
import objects.Ball;
import objects.GameObjects;
import objects.ObjectID;
import objects.Player;
import objects.Player2;
import theGame.MainGame.STATE;

public class Handler {

	public LinkedList<GameObjects> allObjects = new LinkedList<GameObjects>();
	private boolean reset = false;
	private boolean leftGateHit;
	private boolean rightGateHit;
	STATE gameState;

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
		GameObjects ball = allObjects.get(0);
		leftGateHit = ball.getx() >= 891 && ball.gety() >= 175 && ball.gety() <= 365;
		rightGateHit = ball.getx() <= 1 && ball.gety() >= 175 && ball.gety() <= 365;
		if (reset == false) {
			if (leftGateHit) {
				playerScored();
			} else if (rightGateHit) {
				player2Scored();
			}
		}
	}

	private void player2Scored() {
		// TODO Auto-generated method stub
		Score.SCORE2++;
		Audio.getSound("scored_sound").play();
		reset();
	}

	private void playerScored() {
		// TODO Auto-generated method stub
		Score.SCORE1++;
		Audio.getSound("scored_sound").play();
		reset();
	}

	private void reset() {

		Ball ball = (Ball) allObjects.get(0);
		GameObjects player = allObjects.get(1);
		GameObjects player2 = allObjects.get(2);

		// --remove
		removeObjects(ball);
		removeObjects(player);
		removeObjects(player2);
		// ---readd

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

		float partX = player.gCenterPX(player.getx(), MainGame.WIDTH) - ball.gCenterPX(ball.getx(), MainGame.WIDTH);
		float partY = ball.gCenterPY(player.gety(), MainGame.HEIGHT) - ball.gCenterPY(ball.gety(), MainGame.HEIGHT);

		int distanceBetCenters = (int) Math.sqrt(Math.pow(partX, 2) + Math.pow(partY, 2));
		int theoreticalDistanceBetCenters = 60;
		if (distanceBetCenters < theoreticalDistanceBetCenters) {
			ball.collided = true;

			Audio.getSound("collision_sound").play();
			
			boolean playerNotMoving =player.getSpeedX() == 0 && player.getSpeedY() == 0;
		
			if (playerNotMoving) {
				ball.setSpeedX((int) (ball.getSpeedX() * -1 + Math.atan((ball.getx() + 13) - (player.getx() + 25))));
				ball.setSpeedY((int) (ball.getSpeedY() * -1 + Math.atan((ball.gety() + 13) - (player.gety() + 25))));
			} else {
				ball.setSpeedX((int) (player.getSpeedX() + Math.atan((ball.getx() + 13) - (player.getx() + 25))));
				ball.setSpeedY((int) (player.getSpeedY() + Math.atan((ball.gety() + 13) - (player.gety() + 25))));
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
