package com.redstonesimulator.model.block;

/**
 * This is for blocks that imply a finite state machine behavior (introducing a
 * delayed response)
 */
public interface DelayedUpdatable {
	public abstract void delayedBlockUpdate();
}
