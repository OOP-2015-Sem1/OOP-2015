package com.ion.flyingbee.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.ion.flyingbee.controllers.HighscoresController;
import com.ion.flyingbee.utils.PreferencesHandler;

import java.util.ArrayList;

/**
 * Created by Ion on 10.11.2015.
 */
public class HighscoresView extends AbstractView {

    private MainMenuView mainMenuView;
    private BitmapFont font;
    private float timePassed = 0f;
    private ArrayList<Integer> highScores;

    public HighscoresView(Game game, MainMenuView mainMenuView) {
        super(game);
        this.mainMenuView = mainMenuView;
        highScores = PreferencesHandler.getHighScores();
    }

    @Override
    public void show() {
        super.show();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/CFGreenMonster-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 3;
        parameter.color = Color.GOLD;
        font = generator.generateFont(parameter);
        generator.dispose();

        Gdx.input.setInputProcessor(new HighscoresController(game, this));
    }

    @Override
    public void dispose() {
        super.dispose();
        font.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        //font.draw(batch, "HighscoresView", 20, 20);
        font.draw(batch, "1. " + String.valueOf(highScores.get(0)),
                Gdx.graphics.getWidth()/3 + Gdx.graphics.getWidth()/10,
                3*Gdx.graphics.getHeight()/4);
        font.draw(batch, "2. " + String.valueOf(highScores.get(1)),
                Gdx.graphics.getWidth()/3 + Gdx.graphics.getWidth()/10,
                2*Gdx.graphics.getHeight()/4);
        font.draw(batch, "3. " + String.valueOf(highScores.get(2)),
                Gdx.graphics.getWidth()/3 + Gdx.graphics.getWidth()/10,
                1*Gdx.graphics.getHeight()/4);

        timePassed += Gdx.graphics.getDeltaTime();
        batch.end();
    }

    public MainMenuView getMainMenuView() {
        return mainMenuView;
    }
}
