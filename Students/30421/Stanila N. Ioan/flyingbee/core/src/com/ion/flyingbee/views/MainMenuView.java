package com.ion.flyingbee.views;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.ion.flyingbee.controllers.MainMenuController;

public class MainMenuView extends AbstractView {

    private BitmapFont font;
    private float timePassed = 0;
    private AnimatedButton playButton;
    private AnimatedButton highscoresButton;
    private AnimatedButton exitButton;
    private AnimatedButton settingsButton;

    public MainMenuView(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        font = new BitmapFont();
        font.setColor(Color.BLUE);
        try {
            playButton = new AnimatedButton("buttons/playButton.atlas");
            highscoresButton = new AnimatedButton("buttons/highscoresButton.atlas");
            exitButton = new AnimatedButton("buttons/exitButton.atlas");
            settingsButton = new AnimatedButton("buttons/settingsButton.atlas");
        } catch (Exception e) {
            Gdx.app.log("error oppening menu buttons", e.getMessage());
        }
        Gdx.input.setInputProcessor(new MainMenuController(game, this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.begin();
        //font.draw(batch, "MainMenuView", 20, 20);
        int dx;
        int dy;
        dx = Gdx.graphics.getWidth()/4;
        dy = Gdx.graphics.getHeight()/2 - playButton.getDimensions().getRegionHeight()/2;
        playButton.setCoordinateX(dx);
        playButton.setCoordinateY(dy);
        batch.draw(playButton.getKeyFrame(timePassed), dx, dy);
        dx = 2*Gdx.graphics.getWidth()/4 - highscoresButton.getDimensions().getRegionWidth()/2;
        dy = Gdx.graphics.getHeight()/2 - highscoresButton.getDimensions().getRegionHeight()/2;
        highscoresButton.setCoordinateX(dx);
        highscoresButton.setCoordinateY(dy);
        batch.draw(highscoresButton.getKeyFrame(timePassed), dx, dy);
        dx = 3*Gdx.graphics.getWidth()/4 - exitButton.getDimensions().getRegionWidth();
        dy = Gdx.graphics.getHeight()/2 - exitButton.getDimensions().getRegionHeight()/2;
        exitButton.setCoordinateX(dx);
        exitButton.setCoordinateY(dy);
        batch.draw(exitButton.getKeyFrame(timePassed), dx, dy);
        dx = Gdx.graphics.getWidth() - settingsButton.getDimensions().getRegionWidth() - 30;
        dy = Gdx.graphics.getHeight() - settingsButton.getDimensions().getRegionHeight() - 30;
        settingsButton.setCoordinateX(dx);
        settingsButton.setCoordinateY(dy);
        batch.draw(settingsButton.getKeyFrame(timePassed), dx, dy);

        timePassed += Gdx.graphics.getDeltaTime();
        batch.end();
    }

    public AnimatedButton getPlayButton() {
        return playButton;
    }

    public AnimatedButton getHighscoresButton() {
        return highscoresButton;
    }

    public AnimatedButton getExitButton() {
        return exitButton;
    }

    public AnimatedButton getSettingsButton() {
        return settingsButton;
    }
}
