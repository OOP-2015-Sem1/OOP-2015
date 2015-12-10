package theseal.model;

import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Seal extends Entity implements Mortal, Mobile, Submersible {
	private static final String IMAGE_PATH = "seal.png";
	private static final int MAX_AIR = 6;

	public boolean isSubmerged;
	public int airLeft;
	public int maxAir;
	public boolean isDead;

	public Seal(Point position) {
		super(position);
		try {
			super.setImage(ImageIO.read(new File(IMAGE_PATH)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.maxAir = MAX_AIR;
		this.replentishAir();
		this.submerge();

		this.isDead = false;
	}

	public void move(Point randomHolePosition, Point randomPosition) {
		if (this.willMoveToHole()) {
			this.move(randomHolePosition);
		} else {
			this.move(randomPosition);
		}
	}

	// Less air -> greater chance to go to a hole
	public boolean willMoveToHole() {
		return (((double) (MAX_AIR - this.airLeft())) / MAX_AIR) >= Math.random();
	}

	@Override
	public void move(Point position) {
		if (this.isSubmerged()) {
			this.consumeAir();
		}
		super.setPosition(position);
	}

	@Override
	public void submerge() {
		this.isSubmerged = true;
	}

	@Override
	public void surface() {
		this.replentishAir();
		this.isSubmerged = false;
	}

	@Override
	public boolean isSubmerged() {
		return this.isSubmerged;
	}

	@Override
	public void consumeAir() {
		this.airLeft--;
	}

	@Override
	public int airLeft() {
		return this.airLeft;
	}

	@Override
	public boolean outOfAir() {
		return (this.airLeft <= 0);
	}

	@Override
	public void replentishAir() {
		this.airLeft = this.maxAir;
	}

	@Override
	public void die() {
		this.isDead = true;
	}

	@Override
	public boolean isDead() {
		return this.isDead;
	}
}
