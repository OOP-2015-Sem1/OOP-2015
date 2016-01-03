package com.ion.flyingbee.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.ion.flyingbee.controllers.HighscoresController;
import com.ion.flyingbee.controllers.SettingsController;
import com.ion.flyingbee.utils.PreferencesHandler;

/**
 * Created by Ion on 10.11.2015.
 */
public class SettingsView extends AbstractView {

    private MainMenuView mainMenuView;
    private BitmapFont font;
    private float timePassed = 0f;
    private TextureAtlas soundAtlas;
    public TextureRegion soundOn;
    public TextureRegion soundOff;

    public SettingsView(Game game, MainMenuView mainMenuView) {
        super(game);
        this.mainMenuView = mainMenuView;
    }

    @Override
    public void show() {
        super.show();

        font = new BitmapFont();
        font.setColor(Color.BLUE);

        soundAtlas = new TextureAtlas("buttons/soundButton.atlas");
        soundOn = new TextureRegion(soundAtlas.findRegion("on"));
        soundOff = new TextureRegion(soundAtlas.findRegion("off"));

        Gdx.input.setInputProcessor(new SettingsController(game, this));
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
        soundAtlas.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        //font.draw(batch, "SettingsView",20, 20);
        drawButton();

        timePassed += Gdx.graphics.getDeltaTime();
        batch.end();
    }

    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }

    private void drawButton() {
        if (PreferencesHandler.isSoundEnabled()) {
            batch.draw(soundOn,
                    Gdx.graphics.getWidth()/2 - soundOn.getRegionWidth()/2,
                    Gdx.graphics.getHeight()/2 - soundOn.getRegionHeight()/2);
        } else {
            batch.draw(soundOff,
                    Gdx.graphics.getWidth()/2 - soundOff.getRegionWidth()/2,
                    Gdx.graphics.getHeight()/2 - soundOff.getRegionHeight()/2);
        }
    }
}
