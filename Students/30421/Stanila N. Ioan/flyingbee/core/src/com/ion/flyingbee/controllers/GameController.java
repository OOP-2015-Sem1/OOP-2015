package com.ion.flyingbee.controllers;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.ion.flyingbee.views.GameView;

/**
 * Created by Ion on 08.11.2015.
 */
public class GameController extends AbstractController {

    private GameView gameView;

    public GameController(Game game, GameView gameView) {
        super(game);
        this.gameView = gameView;
        
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        gameView.getBee().move(screenX, screenY); // move the bee
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // move the bee
        if (java.lang.Math.abs(screenY - gameView.getBee().getCoordinateY()) > 10) {
            gameView.getBee().move(screenX, screenY);
        }
        return true;
    }

    @Override
    public boolean keyDown(int keyCode) {
        // go to main menu if BACK pressed
        if (keyCode == Input.Keys.BACK) {
            game.setScreen(gameView.getMainMenuView());
            gameView.dispose();
        }
        return true;
    }
}
