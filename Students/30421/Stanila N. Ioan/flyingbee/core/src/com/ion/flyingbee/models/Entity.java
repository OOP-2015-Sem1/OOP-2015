package com.ion.flyingbee.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Ion on 20.12.2015.
 */
public abstract class Entity implements Disposable {

    protected Animation animation;
    protected TextureAtlas atlas;
    protected int coordinateX;
    protected int coordinateY;
    protected int id;

    private static final float ANIMATION_FRAME_DURATION = 1/40f;

    public Entity(String atlasPath) {
        atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation(ANIMATION_FRAME_DURATION, atlas.getRegions());
    }

    @Override
    public void dispose() {
        if (atlas != null) {
            atlas.dispose();
        }
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getId() {
        return id;
    }

    public TextureRegion getKeyFrame(float timePassed) {
        return animation.getKeyFrame(timePassed, true);
    }

    public int getWidth() {
        int maxWidth = 0;
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            if (region.originalWidth > maxWidth) {
                maxWidth = region.originalWidth;
            }
        }
        return maxWidth;
    }

    public int getHeight() {
        int maxHeight = 0;
        for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
            if (region.originalHeight > maxHeight) {
                maxHeight = region.originalWidth;
            }
        }
        return maxHeight;
    }

    public abstract void setInitialPosition(int x, int y);

    public abstract void move(int newX, int newY);
}
