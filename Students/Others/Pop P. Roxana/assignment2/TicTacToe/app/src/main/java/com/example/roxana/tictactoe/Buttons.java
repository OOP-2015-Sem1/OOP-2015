package com.example.roxana.tictactoe;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Roxana on 10/28/2015.
 */
public class Buttons {

    private static ArrayList<Button> arrayOfButtons;

    public ArrayList<Button> getButtons() {
        return arrayOfButtons;
    }

    public void add(Button b) {
        arrayOfButtons.add(b);
    }

    public Buttons() {
    }

    public void setClickListeners(final Context context, Game game, int mode) {
        for (Button b : arrayOfButtons) {
            b.setOnClickListener(new ButtonActionListener(context,game,mode));
        }
    }

    public void setButtonText(int buttonIndex, String text){
        arrayOfButtons.get(buttonIndex).setText(text);
    }

    public void clearButtonsText() {
        for (Button b : arrayOfButtons) {
            b.setText("");
        }
    }

    public void setDefaultButtonsColor() {
        for (Button b : arrayOfButtons) {
            b.setBackgroundColor(Color.LTGRAY);
        }
    }

    public void emphasizeButtons(int a, int b, int c) {
        if (a != -1) {
            arrayOfButtons.get(a).setBackgroundColor(Color.WHITE);
            arrayOfButtons.get(b).setBackgroundColor(Color.WHITE);
            arrayOfButtons.get(c).setBackgroundColor(Color.WHITE);
        }
    }

    public void refreshButtons(Game game){

        setDefaultButtonsColor();
        //update the score
        //clear the table
        clearButtonsText();
        //restart the game
        game.restartGame();

    }

    public void prepareButtonsArray(){
        arrayOfButtons = new ArrayList<Button>();
    }

    public void restoreText(Game game){

        int moves[] = game.getMoves();
        for(int i=0; i<9; i++){
            if(moves[i]==1){
                arrayOfButtons.get(i).setText("X");
            }
            else if(moves[i]==2){
                arrayOfButtons.get(i).setText("O");
            }
        }
    }
}
