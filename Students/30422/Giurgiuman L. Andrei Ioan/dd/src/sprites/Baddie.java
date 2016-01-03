package sprites;

public class Baddie extends Sprite {

	private int side;

	public Baddie(int x, int y, int side) {
		super(x, y);
		this.side = side;
		initializeBaddie();
	}

	private void initializeBaddie() {

		loadImage("baddie.png");
		getImageDimensions();
	}

	public int getSide() {
		return side;
	}

	public void move() {

		if (side == 0) {
			x += 1;
		}
		if (side == 1) {
			y += 1;
		}
		if (side == 2) {
			x -= 1;
		}
	}
}