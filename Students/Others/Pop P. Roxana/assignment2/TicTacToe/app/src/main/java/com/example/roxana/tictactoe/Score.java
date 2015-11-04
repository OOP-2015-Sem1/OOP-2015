package com.example.roxana.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

/**
 * Created by Roxana on 10/28/2015.
 */
public class Score {

    Context context;

    public Score(Context context){
        this.context = context;
    }
    public void setScore(int x, int o){

        SharedPreferences sharedPreferences=context.getSharedPreferences("GameData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putInt("xScore", 0);
        editor.putInt("oScore", 0);
        editor.commit();

    }

    public void displayScore(){

        SharedPreferences sharedPreferences=context.getSharedPreferences("GameData", Context.MODE_PRIVATE);
        int xScore=sharedPreferences.getInt("xScore", 0);
        int oScore=sharedPreferences.getInt("oScore", 0);

        TextView textView = (TextView) ((Activity) context).findViewById(R.id.textView);
        String displayScore = "SCORE(X:O)  " + String.valueOf(xScore) + " : " + String.valueOf(oScore);
        textView.setText(displayScore);

    }

    public void updateScore(int winner){

        SharedPreferences sharedPreferences=context.getSharedPreferences("GameData", Context.MODE_PRIVATE);
        int previousXScore=sharedPreferences.getInt("xScore", 0);
        int previousOScore=sharedPreferences.getInt("oScore", 0);

        SharedPreferences.Editor editor= sharedPreferences.edit();
        if(winner == 1){
            editor.putInt("xScore", previousXScore+1);
        }else if(winner==2){
            editor.putInt("oScore", previousOScore+1);
        }else{
            editor.putInt("xScore", previousXScore+1);
            editor.putInt("oScore", previousOScore+1);
        }
        editor.commit();
    }
}
