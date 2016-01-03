package com.example.alexh.tictactoe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Contains some game data and the TextView for ai status
 * methods that pop up alert dialogs
 * and a MiniMax AI which specifies the next best move
 */
public class Ai {
    private GameBoard gameBoard;

    private TextView aiStatus;
    private boolean aiOn;

    //

    public Ai(GameBoard gameBoard, TextView aiStatus, boolean aiOn) {
        this.gameBoard = gameBoard;

        this.aiStatus = aiStatus;
        this.setAiOn(aiOn);
    }

    public boolean isAiOn() {
        return aiOn;
    }

    public void setAiOn(boolean aiOn) {
        this.aiOn = aiOn;
        this.updateLabel();
    }

    public void updateLabel() {
        if (this.isAiOn()) {
            aiStatus.setText(R.string.ai_on_display);
        } else {
            aiStatus.setText(R.string.ai_off_display);
        }
    }

    // Called only when data from preferences cannot be found
    public boolean aiNewGameAlertDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this.gameBoard.board.activity);
        builder.setTitle("Time to choose");
        builder.setMessage("AI sau N-AI?");
        builder.setPositiveButton("Challenge accepted", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ai.this.setAiOn(true);
            }
        });
        builder.setNegativeButton("I prefer PvP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ai.this.setAiOn(false);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return this.isAiOn();
    }

    // Called in settings menu
    public boolean aiChangeAlertDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this.gameBoard.board.activity);
        builder.setTitle("Activate AI?");
        builder.setMessage("AI is " + (this.isAiOn() ? "ON" : "OFF") + "." + "\nChanging This will reset the game and the score");
        builder.setPositiveButton(this.isAiOn() ? "Turn off" : "Turn on", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Ai.this.setAiOn(!Ai.this.isAiOn());
                Ai.this.gameBoard.reset();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return this.isAiOn();
    }

    // MiniMax AI
    private static int score(Game game, int depth) {
        String gameOver = game.gameOver();
        if (gameOver == null) {
            return 0;
        } else if (gameOver.equals(GridButtonState.X_STATE.toString())) {
            return 10 - depth;
        } else if (gameOver.equals(GridButtonState.O_STATE.toString())) {
            return depth - 10;
        } else {
            return 0;
        }
    }

    private static int maxScoreIndex(LinkedList<Integer> scores) {
        int max = 0;
        int maxIndex = -1;

        for (Integer n : scores) {
            if (n > max || maxIndex == -1) {
                max = n;
                maxIndex = scores.indexOf(n);
            }
        }

        return maxIndex;
    }

    private static int minScoreIndex(LinkedList<Integer> scores) {
        int min = 0;
        int minIndex = -1;

        for (Integer n : scores) {
            if (n < min || minIndex == -1) {
                min = n;
                minIndex = scores.indexOf(n);
            }
        }

        return minIndex;
    }

    private static MiniMaxOutput miniMax(Game game, int depth, GridButtonState symbol) {
        if (game.gameOver() != null) {
            return new MiniMaxOutput(Ai.score(game, depth), null);
        }

        depth++;

        LinkedList<Integer> scores = new LinkedList<>();
        LinkedList<Move> moves = new LinkedList<>();

        for (Move move : game.getAvailableMoves()) {
            game.move(move, symbol);

            GridButtonState newSymbol;
            if (symbol.equals(GridButtonState.X_STATE)) {
                newSymbol = GridButtonState.O_STATE;
            } else {
                newSymbol = GridButtonState.X_STATE;
            }

            scores.addLast(miniMax(game, depth, newSymbol).getScore());
            moves.addLast(move);
            // un-move
            game.move(move, GridButtonState.BLANK_STATE);
        }

        if (symbol.equals(GridButtonState.X_STATE)) {
            // Max
            int index = maxScoreIndex(scores);
            Move choice = moves.get(index);
            return new MiniMaxOutput(scores.get(index), choice);
        } else {
            // Min
            int index = minScoreIndex(scores);
            Move choice = moves.get(index);
            return new MiniMaxOutput(scores.get(index), choice);
        }
    }

    public int[] nextMove(GridButtonState symbol) {
        Game game = new Game(this.gameBoard.gridButtonPanel.getValue());

        Move perfectMove = miniMax(game, 0, symbol).getMove();

        return new int[]{perfectMove.getRow(), perfectMove.getCol()};
    }
}
