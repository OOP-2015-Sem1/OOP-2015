package sprites;

public class LifePowerUp extends Sprite {

	public LifePowerUp(int x, int y) {
		super(x, y);
		initializePowerUp();
	}

	private void initializePowerUp() {
		loadImage("heart.png");
		getImageDimensions();
	}
}