package com.ion.flyingbee.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ion.flyingbee.utils.PreferencesHandler;
import com.ion.flyingbee.views.SettingsView;

/**
 * Created by Ion on 10.11.2015.
 */
public class SettingsController extends AbstractController {

    private SettingsView settingsView;

    public SettingsController(Game game, SettingsView settingsView) {
        super(game);
        this.settingsView = settingsView;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        int buttonX = Gdx.graphics.getWidth()/2 - settingsView.soundOn.getRegionWidth()/2;
        int buttonY = Gdx.graphics.getHeight()/2 - settingsView.soundOn.getRegionHeight()/2;
        if (screenX >= buttonX && screenX <= buttonX + settingsView.soundOn.getRegionWidth()) {
            if (screenY >= buttonY && screenY <= buttonY + settingsView.soundOn.getRegionHeight()) {
                PreferencesHandler.setSoundEnabled(!PreferencesHandler.isSoundEnabled());
            }
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return true;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            game.setScreen(settingsView.getMainMenuView());
            settingsView.dispose();
        }
        return true;
    }
}
