package com.example.socaci.assignment21;

import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Socaci on 10/28/2015.
 */
public class TicTacToeGame{

    //instance variables

    private String[] buttonsText;
    private static int countButtons;//counts how many buttons are already pressed(they have O or X in them).
    private String winner;
    private int countGamesX;
    private int countGamesO;

    //constructors

    public TicTacToeGame(){
        buttonsText = new String[]{"", "", "", "", "", "", "", "", ""};
        countButtons = 0;
        winner = "";
        countGamesX = 0;
        countGamesO = 0;
    }

    //methods

    public String setButtonText(){
        if(countButtons % 2 == 0){
            countButtons++;
            return "X";
        }
        else{
            countButtons++;
            return "O";
        }
    }

    public void setString(int i, String text){//here is the index(meaning what button was pressed) and the text on that button

        buttonsText[i] = text;
    }

    public void getWinner(){

        String thewinner = new String("");

        if(buttonsText[0].equals(buttonsText[1]) && buttonsText[1].equals(buttonsText[2]) && !(buttonsText[0].equals(""))){
            thewinner = buttonsText[0];
        }
        else if(buttonsText[3].equals(buttonsText[4]) && buttonsText[4].equals(buttonsText[5]) && !(buttonsText[3].equals(""))){
            thewinner= buttonsText[3];
        }
        else if(buttonsText[6].equals(buttonsText[7]) && buttonsText[7].equals(buttonsText[8]) && !(buttonsText[6].equals(""))){
            thewinner = buttonsText[6];
        }
        else if(buttonsText[0].equals(buttonsText[3]) && buttonsText[3].equals(buttonsText[6]) && !(buttonsText[0].equals(""))){
            thewinner = buttonsText[0];
        }
        else if(buttonsText[1].equals(buttonsText[4]) && buttonsText[4].equals(buttonsText[7]) && !(buttonsText[1].equals(""))){
            thewinner = buttonsText[1];
            System.out.println(winner);
        }
        else if(buttonsText[2].equals(buttonsText[5]) && buttonsText[5].equals(buttonsText[8]) && !(buttonsText[2].equals(""))){
            thewinner = buttonsText[2];
        }
        else if(buttonsText[0].equals(buttonsText[4]) && buttonsText[4].equals(buttonsText[8]) && !(buttonsText[0].equals(""))){
            thewinner = buttonsText[0];
        }
        else if(buttonsText[2].equals(buttonsText[4]) && buttonsText[4].equals(buttonsText[6]) && !(buttonsText[2].equals(""))) {
            thewinner = buttonsText[2];
        }
        if(countButtons == 9 || !(thewinner.equals(""))){//If game has finished and all buttons are filled
            if (thewinner.equals("")) {
                this.winner = "There is no winner!";

            } else {

                this.winner = "The winner is: " + thewinner + "!";

                if(thewinner.equals("X"))countGamesX++;
                else countGamesO++;
            }
        }
    }

    public String getScore(){

        String score;
        score = "Score: " + String.valueOf(countGamesX) + "-" + String.valueOf(countGamesO);

        if(countButtons == 9 || !(winner.equals(""))){
            return score;
        }
        else return "Score: 0-0";
    }

    public void resetGame(){
        if(countButtons == 9 || !(winner.equals(""))) {

            buttonsText = new String[]{"", "", "", "", "", "", "", "", ""};
            countButtons = 0;
            this.winner = "";
        }
    }

    public int getCountButtons(){
        return countButtons;
    }

    public String getWinnerVar(){
        return winner;
    }

}
