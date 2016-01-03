package theGame;

import java.awt.Graphics;
import java.util.LinkedList;

import display.Score;
import objects.Ball;
import objects.GameObject;
import objects.ObjectID;
import objects.Player;
import theGame.MainGame.STATE;

public class Handler {

	public LinkedList<GameObject> allObjects = new LinkedList<GameObject>();
	private boolean reset = false;
	private boolean leftGateHit;
	private boolean rightGateHit;
	STATE gameState;
	
	//Sound collision = new Sound("/res/Laser_Shoot7.ogg");

	public void tick() {
		for (int i = 0; i < allObjects.size(); i++) {
			GameObject tempGameObjects = allObjects.get(i);

			tempGameObjects.tick();
		}

		verifyColision();
		verifyScore();

	}

	private void verifyScore() {

		// TODO Auto-generated method stub
		GameObject ball = allObjects.get(0);
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
		if (MainGame.soundMute == false) {
			//Audio.getSound("scored_sound").play();
			//collision.PlaySound();
		}
		reset();
	}

	private void playerScored() {
		// TODO Auto-generated method stub
		Score.SCORE1++;
		if (MainGame.soundMute == false) {
			//Audio.getSound("scored_sound").play();
			//collision.PlaySound();
		}
		reset();
	}

	private void reset() {

		Ball ball = (Ball) allObjects.get(0);
		GameObject player = allObjects.get(1);
		GameObject player2 = allObjects.get(2);

		// --remove
		removeObjects(ball);
		removeObjects(player);
		removeObjects(player2);
		// ---readd

		addObject(new Ball(MainGame.WIDTH / 2 - 50, MainGame.HEIGHT / 2 - 50, ObjectID.Ball));
		addObject(new Player(50, MainGame.HEIGHT / 2 - 70, ObjectID.Player));
		addObject(new Player(MainGame.WIDTH - 170, MainGame.HEIGHT / 2 - 70, ObjectID.Player2));

	}

	private void verifyColision() {
		// TODO Auto-generated method stub

		Ball ball = (Ball) allObjects.get(0);
		GameObject player = allObjects.get(1);
		GameObject player2 = allObjects.get(2);

		collision(player, ball);
		collision(player2, ball);
	}

	public void collision(GameObject player, Ball ball) {

		float partX = player.gCenterPX(player.getx(), MainGame.WIDTH) - ball.gCenterPX(ball.getx(), MainGame.WIDTH);
		float partY = ball.gCenterPY(player.gety(), MainGame.HEIGHT) - ball.gCenterPY(ball.gety(), MainGame.HEIGHT);

		int distanceBetCenters = (int) Math.sqrt(Math.pow(partX, 2) + Math.pow(partY, 2));
		int theoreticalDistanceBetCenters = 60;
		if (distanceBetCenters < theoreticalDistanceBetCenters) {
			ball.collided = true;

			//if (MainGame.soundMute == false) {
				//Audio.getSound("collision_sound").play();
			//}

			boolean playerNotMoving = player.getSpeedX() == 0 && player.getSpeedY() == 0;

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
			GameObject tempGameObjects = allObjects.get(i);

			tempGameObjects.render(graph);
		}
	}

	public void addObject(GameObject allObjects) {
		this.allObjects.add(allObjects);
	}

	public void removeObjects(GameObject allObjects) {
		this.allObjects.remove(allObjects);
	}

}
