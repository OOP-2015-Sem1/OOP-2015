package com.ion.flyingbee.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Ion on 08.11.2015.
 */
public class Bee extends Entity {

    private int lives;


    public Bee() {
        super("entities/bee.atlas");
        this.id = 0;
        this.lives = 3;
    }

    @Override
    public void setInitialPosition(int x, int y) {
        coordinateX = x;
        coordinateY = y;
    }

    @Override
    public void move(int newX, int newY) {
        if (newY < atlas.getRegions().get(0).getRegionHeight()) {
            coordinateY = Gdx.graphics.getHeight() - atlas.getRegions().get(0).getRegionHeight();
        } else {
            coordinateY = Gdx.graphics.getHeight() - newY;
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void addLife() {
        this.lives++;
    }

    public void subtractLife() {
        this.lives--;
    }
}
