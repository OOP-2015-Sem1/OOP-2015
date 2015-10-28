package com.example.alexh.tictactoe;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Holds game data and game logic
 */
public class GameBoard {
    protected Board board;

    protected GridButtonPanel gridButtonPanel;
    protected Score score;
    protected int turn;
    protected Ai ai;

    public GameBoard(Board board, LinkedList<Button> buttonLinkedList, TextView scoreTextView, TextView aiTextView) {
        this.board = board;

        this.gridButtonPanel = new GridButtonPanel(this, buttonLinkedList);
        this.score = new Score(this, scoreTextView);

        this.turn = 0;
        this.ai = new Ai(this, aiTextView, false);
    }

    // General
    public void reset() {
        this.resetButtons();
        this.resetScore();
    }

    public void resetButtons() {
        this.gridButtonPanel.reset();
        this.resetTurn();
    }

    public void resetScore() {
        this.score.reset();
    }

    // Turn methods
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void resetTurn() {
        this.setTurn(0);
    }

    public void incrementTurn() {
        this.setTurn(this.getTurn() + 1);
    }

    // AI
    public void changeAi() {
        this.ai.aiChangeAlertDialog();
    }

    // Move related methods

    // Returns the next symbol representing who's turn is
    // NOTE: X always starts first
    private GridButtonState getNextSymbol() {
        GridButtonState symbol;

        if (this.getTurn() % 2 == 0) {
            symbol = GridButtonState.X_STATE;
        } else {
            symbol = GridButtonState.O_STATE;
        }

        return symbol;
    }

    public boolean tryMove(GridButtonState symbol, int row, int col) {
        if (this.gridButtonPanel.getValueAt(row, col).equals(GridButtonState.BLANK_STATE)) {
            this.gridButtonPanel.setValueAt(symbol, row, col);
            this.incrementTurn();
            return true;
        } else {
            Toast.makeText(this.board.activity, "Cell already occupied", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    // The move method sets the button label and also takes another move if the ai is on
    // then it checks if the game is not over
    // NOTE: X is the player who also starts first
    public void move(int row, int col) {
        // Player's turn
        GridButtonState playerSymbol = this.getNextSymbol();
        if (!this.tryMove(playerSymbol, row, col)) {
            return;
        }

        // Check winning condition
        String winner = new Game(this.gridButtonPanel.getValue()).gameOver();
        if (winner != null) {
            this.score.sWon(GridButtonState.getValueOf(winner));

            this.resetButtons();

            return;
        }

        // Ai turn
        if (this.ai.isAiOn()) {
            GridButtonState aiSymbol = this.getNextSymbol();
            int[] pos = this.ai.nextMove(aiSymbol);
            this.tryMove(aiSymbol, pos[0], pos[1]);
        }

        // Check winning condition
        winner = new Game(this.gridButtonPanel.getValue()).gameOver();
        if (winner != null) {
            this.score.sWon(GridButtonState.getValueOf(winner));

            this.resetButtons();
        }
    }
}
