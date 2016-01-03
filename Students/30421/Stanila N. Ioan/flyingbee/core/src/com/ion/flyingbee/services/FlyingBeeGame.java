package com.ion.flyingbee.services;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.ion.flyingbee.utils.PreferencesHandler;
import com.ion.flyingbee.views.MainMenuView;

/**
 * Created by Ion on 08.11.2015.
 */
public class FlyingBeeGame extends Game {

    private Game game;

    @Override
    public void create() {
        game = this;
        PreferencesHandler.setPreferences();
        game.setScreen(new MainMenuView(game));
        Gdx.input.setCatchBackKey(true);
    }

    @Override
    public void render() {
        super.render();
    }
}
