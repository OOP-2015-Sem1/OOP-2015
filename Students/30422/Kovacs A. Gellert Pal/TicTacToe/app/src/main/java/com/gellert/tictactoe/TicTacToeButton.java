package com.gellert.tictactoe;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


/**
 * Created by Gellért on 2015. 10. 21..
 */
public class TicTacToeButton extends Button implements View.OnClickListener{

    private static final int CHAR_X = 1;
    private static final int CHAR_O = 4;

    private static final int X_WINS = 1;
    private static final int O_WINS = 2;
    private static final int NO_WINNER = 0;

    private int value;
    private Context context;

    public TicTacToeButton(Context context) {
        super(context);
        this.context = context;
        setOnClickListener(this);
    }

    public TicTacToeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setOnClickListener(this);
    }

    public TicTacToeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setOnClickListener(this);
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

    @Override
    public void onClick(View btn) {
        GameState gameStateInstance = GameState.getInstance();
        TicTacToeMainActivity mainActivity = (TicTacToeMainActivity)context;
                ((TicTacToeButton) btn).updateContent(gameStateInstance.currentCharacter());
        gameStateInstance.nextState();

        switch (GameUtilities.checkWin(mainActivity.getButtonMatrix())) {
            case X_WINS:
                Toast.makeText(context, "X wins!", Toast.LENGTH_SHORT).show();
                gameStateInstance.incScore(CHAR_X);
                gameStateInstance.displayScore(mainActivity.getScoreText());
                mainActivity.resetMatch();
                break;
            case O_WINS:
                Toast.makeText(context, "O wins!", Toast.LENGTH_SHORT).show();
                gameStateInstance.incScore(CHAR_O);
                gameStateInstance.displayScore(mainActivity.getScoreText());
                mainActivity.resetMatch();
                break;
            case NO_WINNER:
                if (gameStateInstance.getState() >= 9) {
                    Toast.makeText(context, "Draw match!", Toast.LENGTH_SHORT).show();
                    mainActivity.resetMatch();
                }
                break;
        }
        mainActivity.saveScore();
    }
}
