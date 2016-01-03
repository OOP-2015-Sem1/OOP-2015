package com.ion.flyingbee.views;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by Ion on 10.11.2015.
 */
public class AnimatedButton implements Disposable {

    private Animation animation;
    private TextureAtlas atlas;

    private int coordinateX;
    private int coordinateY;

    public AnimatedButton(String atlasResource) {
        atlas = new TextureAtlas(Gdx.files.internal(atlasResource));
        animation = new Animation(1/2f, atlas.getRegions());
    }

    @Override
    public void dispose() {
        if (atlas != null) {
            atlas.dispose();
        }
    }

    public TextureRegion getKeyFrame(float timePassed) {
        return animation.getKeyFrame(timePassed, true);
    }

    public TextureAtlas.AtlasRegion getDimensions() {
        return atlas.getRegions().get(0);
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
}
