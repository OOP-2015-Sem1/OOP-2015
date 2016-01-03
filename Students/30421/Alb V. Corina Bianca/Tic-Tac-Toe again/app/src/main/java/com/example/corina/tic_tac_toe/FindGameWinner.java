package com.example.corina.tic_tac_toe;

import android.util.Log;
import android.widget.Button;

/**
 * Created by Corina on 10/27/2015.
 */
public class FindGameWinner {
    
        private Boolean mSomebodyWon;
        private Button[] mButtons;

        public FindGameWinner(Boolean somebodyWon, Button[] buttons) {
            this.mSomebodyWon = somebodyWon;
            this.mButtons = buttons;
            Log.e("TAG","find game winner");
        }

        public String gameWinner(){
            Log.e("TAG", "findGameWinner " + mSomebodyWon);
            if (!mSomebodyWon) {
                if (mButtons[0].getText().equals(mButtons[1].getText()) && mButtons[0].getText().equals(mButtons[2].getText())
                        && !(mButtons[0].getText().equals(" "))) {
                    Log.e("TAG", "yufer 1 " + String.valueOf(mButtons[0].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[0].getText());
                }
                if (mButtons[3].getText().equals(mButtons[4].getText()) && mButtons[3].getText().equals(mButtons[5].getText())
                        && !(mButtons[3].getText().equals(" "))) {
                    Log.e("TAG", "yufer 2 " + String.valueOf(mButtons[3].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[3].getText());
                }
                if ((mButtons[6].getText() == (mButtons[7].getText()) && mButtons[7].getText() == mButtons[8].getText())
                        && (mButtons[6].getText() != " ")) {
                    Log.e("TAG", "yufer 3 " + String.valueOf(mButtons[8].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[8].getText());
                }
                if ((mButtons[0].getText() == mButtons[4].getText() && mButtons[4].getText() == mButtons[8].getText())
                        && (mButtons[0].getText() != " ")) {
                    Log.e("TAG", "yufer 4 " + String.valueOf(mButtons[4].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[4].getText());
                }
                if ((mButtons[0].getText() == (mButtons[3].getText()) && mButtons[3].getText() == mButtons[6].getText())
                        && (mButtons[0].getText() !=  " ")) {
                    Log.e("TAG", "yufer 5 " + String.valueOf(mButtons[3].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[3].getText());
                }
                if ((mButtons[1].getText() == (mButtons[4].getText()) && mButtons[4].getText() == mButtons[7].getText())
                        && (mButtons[1].getText() != " ")) {
                    Log.e("TAG", "yufer 6 " + String.valueOf(mButtons[4].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[4].getText());
                }
                if ((mButtons[2].getText() == (mButtons[5].getText()) && mButtons[5].getText() == mButtons[8].getText())
                        && (mButtons[2].getText() != " ")) {
                    Log.e("TAG", "yufer 7 " + String.valueOf(mButtons[8].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[8].getText());
                }
                if ((mButtons[2].getText() == (mButtons[4].getText()) && mButtons[4].getText() == mButtons[6].getText())
                        && (mButtons[2].getText() != " ")) {
                    Log.e("TAG", "yufer 8 " + String.valueOf(mButtons[6].getText()));
                    mSomebodyWon = true;
                    Log.e("TAG","return something");
                    return String.valueOf(mButtons[6].getText());
                }
            }
            return "NO";
        }

    public Boolean getSomebodyWon(){
        return mSomebodyWon;
    }
}