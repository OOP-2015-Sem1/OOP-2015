package com.ion.flyingbee.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.ArrayList;

/**
 * Created by Ion on 28.12.2015.
 */
public class PreferencesHandler {

    public static Preferences preferences;

    public static void setPreferences() {
        if (preferences == null) {
            preferences = Gdx.app.getPreferences("flyingbee");
        }
        if (!preferences.contains("highscore1")) {
            preferences.putInteger("highscore1", 0);
            preferences.flush();
        }
        if (!preferences.contains("highscore2")) {
            preferences.putInteger("highscore2", 0);
            preferences.flush();
        }
        if (!preferences.contains("highscore3")) {
            preferences.putInteger("highscore3", 0);
            preferences.flush();
        }
        if (!preferences.contains("soundenabled")) {
            preferences.putBoolean("soundenabled", true);
            preferences.flush();
        }
    }

    public static ArrayList<Integer> getHighScores() {
        ArrayList<Integer> highScores = new ArrayList<Integer>();
        highScores.add(preferences.getInteger("highscore1"));
        highScores.add(preferences.getInteger("highscore2"));
        highScores.add(preferences.getInteger("highscore3"));
        return highScores;
    }

    public static boolean isSoundEnabled() {
        return preferences.getBoolean("soundenabled");
    }

    public static void addHighScore(int newHighScore) {
        ArrayList<Integer> highScores = getHighScores();
        if (newHighScore > highScores.get(0)) {
            highScores.set(0, newHighScore);
        } else if (newHighScore > highScores.get(1)) {
            highScores.set(1, newHighScore);
        } else if (newHighScore > highScores.get(2)) {
            highScores.set(2, newHighScore);
        }
        preferences.putInteger("highscore1", highScores.get(0));
        preferences.flush();
        preferences.putInteger("highscore2", highScores.get(1));
        preferences.flush();
        preferences.putInteger("highscore3", highScores.get(2));
        preferences.flush();
    }

    public static void setSoundEnabled(boolean state) {
        preferences.putBoolean("soundenabled", state);
        preferences.flush();
    }
}
