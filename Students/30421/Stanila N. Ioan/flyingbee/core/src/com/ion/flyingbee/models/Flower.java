package com.ion.flyingbee.models;

/**
 * Created by Ion on 23.12.2015.
 */
public class Flower extends Entity {

    public static final int SPEED = 4;
    public static final float FLOWER_ANIMATION_FRAME_DURATION = .1f;

    public Flower() {
        super("entities/flower.atlas");
        this.id = 2;
        animation.setFrameDuration(FLOWER_ANIMATION_FRAME_DURATION);
    }

    @Override
    public void setInitialPosition(int x, int y) {
        coordinateX = x;
        coordinateY = y;
    }

    @Override
    public void move(int newX, int newY) {
        coordinateX = newX;
    }
}
