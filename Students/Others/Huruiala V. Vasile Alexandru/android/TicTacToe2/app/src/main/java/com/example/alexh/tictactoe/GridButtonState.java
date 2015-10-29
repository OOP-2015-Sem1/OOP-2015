package com.example.alexh.tictactoe;

/**
 * Created by alexh on 25.10.2015.
 */
public enum GridButtonState {
    X_STATE("X"), O_STATE("O"), BLANK_STATE(" ");
    private String value;

    GridButtonState(String value) {
        this.value = value;
    }

    public static GridButtonState getValueOf(String value) {
        switch (value) {
            case "X":
                return X_STATE;
            case "O":
                return O_STATE;
            case " ":
                return BLANK_STATE;
        }

        return BLANK_STATE;
    }

    @Override
    public String toString() {
        return value;
    }
}
