package com.ion.flyingbee.models;

/**
 * Created by Ion on 23.12.2015.
 */
public class Wasp extends Entity {

    public static final int SPEED = 8;

    public Wasp() {
        super("entities/wasp.atlas");
        this.id = 1;
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
