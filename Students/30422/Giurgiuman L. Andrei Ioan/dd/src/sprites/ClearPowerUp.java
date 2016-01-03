package sprites;

public class ClearPowerUp extends Sprite {

	public ClearPowerUp(int x, int y) {
		super(x, y);
		initializePowerUp2();
	}

	private void initializePowerUp2() {
		loadImage("blue-ball.png");
		getImageDimensions();
	}
}