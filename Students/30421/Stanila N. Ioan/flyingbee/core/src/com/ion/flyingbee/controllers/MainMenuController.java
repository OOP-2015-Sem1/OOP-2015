package com.ion.flyingbee.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ion.flyingbee.views.AnimatedButton;
import com.ion.flyingbee.views.GameView;
import com.ion.flyingbee.views.HighscoresView;
import com.ion.flyingbee.views.MainMenuView;
import com.ion.flyingbee.views.SettingsView;

/**
 * Created by Ion on 08.11.2015.
 */
public class MainMenuController extends AbstractController {

    private MainMenuView mainMenuView;

    public MainMenuController(Game game, MainMenuView mainMenuView) {
        super(game);
        this.mainMenuView = mainMenuView;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (inRegion(screenX, screenY, mainMenuView.getPlayButton())) {
            game.setScreen(new GameView(game, mainMenuView));
        } else if(inRegion(screenX, screenY, mainMenuView.getHighscoresButton())) {
            game.setScreen(new HighscoresView(game, mainMenuView));
        } else if(inRegion(screenX, screenY, mainMenuView.getExitButton())) {
            Gdx.app.exit();
        } else if(inRegion(screenX, screenY, mainMenuView.getSettingsButton())) {
            game.setScreen(new SettingsView(game, mainMenuView));
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean keyDown(int keyCode) {
        if (keyCode == Input.Keys.BACK) {
            mainMenuView.dispose();
            Gdx.app.exit();
        }
        return true;
    }

    private boolean inRegion(int screenX, int screenY, AnimatedButton b) {
        screenY = Gdx.graphics.getHeight() - screenY;
        if (screenX >= b.getCoordinateX() && screenX <= b.getCoordinateX() + b.getDimensions().getRegionWidth()) {
            if (screenY >= b.getCoordinateY() && screenY <= b.getCoordinateY() + b.getDimensions().getRegionHeight()) {
                return true;
            }
        }
        return false;
    }
}
