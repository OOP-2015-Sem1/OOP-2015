package com.ion.flyingbee.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ion.flyingbee.views.GameView;
import com.ion.flyingbee.views.HighscoresView;

/**
 * Created by Ion on 10.11.2015.
 */
public class HighscoresController extends AbstractController {

    private HighscoresView highscoresView;

    public HighscoresController(Game game, HighscoresView highscoresView) {
        super(game);
        this.highscoresView = highscoresView;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
            game.setScreen(highscoresView.getMainMenuView());
            highscoresView.dispose();
        }
        return true;
    }
}
