package com.example.alexh.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alexh on 20.10.2015.
 */
public class GridButton {

    private GridButtonPanel gridButtonPanel;
    private Button but;
    private int row;
    private int col;

    public GridButton(GridButtonPanel gridButtonPanel, Button but, int row, int col) {

        this.gridButtonPanel = gridButtonPanel;

        this.but = but;
        this.but.setOnClickListener(new GridButtonOnClickListener());
        this.row = row;
        this.col = col;

        this.but.setText(this.gridButtonPanel.getContext().getString(R.string.blankButton));
    }

    public void resetButton() {
        this.but.setText(this.gridButtonPanel.getContext().getString(R.string.blankButton));
    }

    public String getButtonText() {
        return but.getText().toString();
    }

    public void setClickListener(View.OnClickListener l) {
        this.but.setOnClickListener(l);
    }

    public Button getBut() {
        return but;
    }

    public void setBut(Button but) {
        this.but = but;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    private class GridButtonOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            gridButtonPanel.move(but, row, col);
        }
    }
}
