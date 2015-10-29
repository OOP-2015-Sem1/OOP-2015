package com.example.alexh.tictactoe;

import java.util.LinkedList;

/**
 * Contains a 3x3 symbol matrix and the essential game logic
 * plus some methods that the ai needs
 */
public class Game {

    private GridButtonState[][] grid;

    public Game(GridButtonState[][] grid) {
        this.grid = grid;
    }

    public GridButtonState[][] getGrid() {
       GridButtonState[][] newGrid = new GridButtonState[3][3];

        for (int row = 0; row < 3; row++) {
            System.arraycopy(grid[row], 0, newGrid[row], 0, 3);
        }

        return newGrid;
    }

    private boolean gameOver(GridButtonState symbol) {
        if (symbol.equals(GridButtonState.BLANK_STATE)) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    if (this.grid[row][col].equals(GridButtonState.BLANK_STATE)) {
                        return false;
                    }
                }
            }

            return true;
        }

        // Check rows
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (!this.grid[row][col].equals(symbol)) {
                    break;
                }
                if (col == 2) {
                    return true;
                }
            }
        }

        // Check cols
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (!this.grid[row][col].equals(symbol)) {
                    break;
                }
                if (row == 2) {
                    return true;
                }
            }
        }

        // Check leading diagonal
        for (int i = 0; i < 3; i++) {
            if (!this.grid[i][i].equals(symbol)) {
                break;
            }
            if (i == 2) {
                return true;
            }
        }

        // Check secondary diagonal
        for (int i = 0; i < 3; i++) {
            if (!this.grid[i][2 - i].equals(symbol)) {
                break;
            }
            if (i == 2) {
                return true;
            }
        }

        return false;
    }

    public String gameOver() {
        if (this.gameOver(GridButtonState.X_STATE)) {
            return GridButtonState.X_STATE.toString();
        } else if (this.gameOver(GridButtonState.O_STATE)) {
            return GridButtonState.O_STATE.toString();
        } else if (this.gameOver(GridButtonState.BLANK_STATE)) {
            return GridButtonState.BLANK_STATE.toString();
        } else {
            return null;
        }
    }

    public LinkedList<Move> getAvailableMoves() {
        LinkedList<Move> moves = new LinkedList<>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (this.grid[row][col].equals(GridButtonState.BLANK_STATE)) {
                    moves.addLast(new Move(row, col));
                }
            }
        }

        return moves;
    }

    public void move(Move move, GridButtonState symbol) {
        this.grid[move.getRow()][move.getCol()] = symbol;
    }
}
