package com.gellert.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;


/**
 * Created by Gellért on 2015. 10. 21..
 */
public class TicTacToeButton extends Button {

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private int value;

    public TicTacToeButton(Context context) {
        super(context);
    }

    public TicTacToeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TicTacToeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void resetContent() {
        this.setClickable(true);
        this.setText("");
        this.value = 0;
    }

    public void updateContent(int value) {
        this.value = value;
        switch (this.value) {
            case CHAR_O:
                this.setText("O");
                this.setClickable(false);
                break;
            case CHAR_X:
                this.setText("X");
                this.setClickable(false);
                break;
        }
    }

    public int getValue() {
        return value;
    }
}
