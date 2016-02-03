package com.alexasapps.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.widget.Toast;

public class ScoreHolder {

    public static void updateScores(Activity activity, String noOfTriesKey, String timeElapsedKey, long currentTries, long currentTimeElapsed) {
        SharedPreferences data = activity.getSharedPreferences("scores", activity.MODE_PRIVATE);

        long previousBestTries = data.getLong(noOfTriesKey, Long.MAX_VALUE);
        long previousBestTime = data.getLong(timeElapsedKey, Long.MAX_VALUE);

        StringBuilder message = new StringBuilder();

        if (previousBestTries != Long.MAX_VALUE) {
            message.append("Previous record moves: " + previousBestTries + " in " + previousBestTime / 1000 + "seconds.\n");
        }
        message.append("Your moves: " + currentTries + " in " + currentTimeElapsed / 1000 + "seconds.");

        if ((currentTries < previousBestTries) || (currentTries == previousBestTries && currentTimeElapsed < previousBestTime)) {
            SharedPreferences.Editor editor = data.edit();
            editor.putLong(noOfTriesKey, currentTries);
            editor.putLong(timeElapsedKey, currentTimeElapsed);
            editor.apply();

            message.append("\nNEW RECORD!!!!!!");
        } else {
            message.append("\nNEVER LUCKY!");
        }

        Toast.makeText(activity, message.toString(), Toast.LENGTH_LONG).show();
    }
}
