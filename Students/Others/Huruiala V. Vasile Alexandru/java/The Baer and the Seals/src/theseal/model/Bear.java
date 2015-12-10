package theseal.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Bear extends Entity implements Mobile, Killer {
	private static final String IMAGE_PATH = "bear.png";

	private static final int MIN_WAITING_TICKS = 2;
	private static final int MAX_WAITING_TICKS = 5;

	private int ticksWaited;

	public Bear(Point position) {
		super(position);
		try {
			super.setImage(ImageIO.read(new File(IMAGE_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.ticksWaited = 0;
	}

	public void moveIfWilling(Point nextPosition) {
		if (this.willMove()) {
			this.move(nextPosition);
		} else {
			this.waitForSeal();
		}
	}

	public boolean willMove() {
		if (this.ticksWaited <= MIN_WAITING_TICKS) {
			return false;
		} else if (this.ticksWaited <= MAX_WAITING_TICKS) {
			return new Random().nextBoolean();
		} else {
			return true;
		}
	}

	public void waitForSeal() {
		this.ticksWaited++;
	}

	public int getTicksWaited() {
		return this.ticksWaited;
	}

	@Override
	public void move(Point position) {
		this.ticksWaited = 0;
		super.setPosition(position);
	}

	@Override
	public boolean kill(Mortal m) {
		if (!m.isDead()) {
			this.ticksWaited = 0;
			m.die();
			return true;
		} else {
			return false;
		}
	}
}
