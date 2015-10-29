package com.example.alexh.tictactoe;

import android.view.View;
import android.widget.Button;

/**
 * The holder for the button, contains the event listener which calls methods up (gridButtonPanel.GameBoard...)
 */
public class GridButton {

    private GridButtonPanel gridButtonPanel;

    private Button button;
    private GridButtonState gridButtonState;

    private int row;
    private int col;

    public GridButton(GridButtonPanel gridButtonPanel, Button button, int row, int col) {
        // Reference to holder
        this.gridButtonPanel = gridButtonPanel;

        // Button init
        this.button = button;
        this.reset();
        this.button.setOnClickListener(new GridButtonOnClickListener());

        // Location
        this.row = row;
        this.col = col;
    }

    public GridButtonState getGridButtonState() {
        return gridButtonState;
    }

    public void setGridButtonState(GridButtonState gridButtonState) {
        this.gridButtonState = gridButtonState;
        this.updateLabel();
    }

    public Button getButton() {
        return button;
    }

    public void reset() {
        this.setGridButtonState(GridButtonState.BLANK_STATE);
    }

    public void updateLabel() {
        this.button.setText(this.gridButtonState.toString());
    }

    private class GridButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            GridButton.this.gridButtonPanel.gameBoard.move(GridButton.this.row, GridButton.this.col);
        }
    }
}
