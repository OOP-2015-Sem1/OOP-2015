package com.example.alexh.tictactoe;

import android.widget.Button;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * A class holder for the 3x3 matrix of GridButtons
 */
public class GridButtonPanel {

    protected GameBoard gameBoard;

    private GridButton[][] gridButtons;

    public GridButtonPanel(GameBoard gameBoard, LinkedList<Button> buttonLinkedList) {

        this.gameBoard = gameBoard;

        this.gridButtons = new GridButton[3][3];
        Iterator<Button> iterator = buttonLinkedList.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            int row = index / 3;
            int col = index % 3;
            this.gridButtons[row][col] = new GridButton(this, iterator.next(), row, col);
            index++;
        }
    }

    public Button getButtonAt(int row, int col) {
        return this.gridButtons[row][col].getButton();
    }

    public GridButtonState getValueAt(int row, int col) {
        return this.gridButtons[row][col].getGridButtonState();
    }

    public GridButtonState[][] getValue() {
        GridButtonState[][] value = new GridButtonState[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                value[row][col] = this.getValueAt(row, col);
            }
        }

        return value;
    }

    public void setValueAt(GridButtonState gridButtonState, int row, int col) {
        this.gridButtons[row][col].setGridButtonState(gridButtonState);
    }

    public void setValue(String value) {
        String[] buttonValues = value.split("");
        for (int i = 0; i < 9; i++) {
            this.setValueAt(GridButtonState.getValueOf(buttonValues[i + 1]), i / 3, i % 3);
        }
    }

    public void reset() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                this.gridButtons[row][col].reset();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(9);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                stringBuilder.append(this.getValueAt(row, col).toString());
            }
        }

        return stringBuilder.toString();
    }
}
