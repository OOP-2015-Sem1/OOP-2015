package com.redstonesimulator.model.block;

import utilities.Direction;

public interface Directional {
	public abstract Direction getDirection();

	public abstract void setDirection(Direction direction);
}
