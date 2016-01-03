package com.redstonesimulator.model.block;

import utilities.Direction;

/**
 * This is for any block that changes it's behavior based on adjacent blocks.
 * These methods do not infer any kind of finite state machine behavior.
 */
public interface InstantlyUpdateable {
	public abstract void preInstantBlockUpdate();

	public abstract InstantBlockUpdate instantBlockUpdate(Block block, Direction direction);
}
