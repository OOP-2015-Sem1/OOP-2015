package com.example.alexh.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by alexh on 20.10.2015.
 */
public class GridButtonPanel {

    private static final String DRAW = "Draw";
    private Context context;
    private Activity activity;

    private boolean aiIsOn = false;
    private TextView aiStatus;

    private Score score;
    private GridButton[] gridButtons;
    private int turn;

    private String blankString;
    private String xString;
    private String oString;

    public GridButtonPanel(Context context, Activity activity, Button[] buttons) {
        this.context = context;
        this.activity = activity;

        // Score
        this.score = new Score();
        this.score.reset();
        this.updateScore();

        // Buddons
        this.gridButtons = new GridButton[9];
        for (int i = 0; i < 9; i++) {
            this.gridButtons[i] = new GridButton(this, buttons[i], i / 3 + 1, i % 3 + 1);
        }

        this.blankString = getContext().getString(R.string.blankButton);
        this.xString = getContext().getString(R.string.xButton);
        this.oString = getContext().getString(R.string.oButton);

        this.resetTurn();

        // Load from file
        this.load();

        this.aiStatus = (TextView) activity.findViewById(R.id.aiStatus);
        this.updateAiStatus();
    }

    private void aiNewGameAlertDialog() {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("Time to choose");
        builder.setMessage("AI sau N-AI?");
        builder.setPositiveButton("Challenge accepted", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GridButtonPanel.this.aiIsOn = true;
            }
        });
        builder.setNegativeButton("I prefer PvP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GridButtonPanel.this.aiIsOn = false;
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void aiChangeAlertDialog() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this.activity);
        builder.setTitle("Activate AI?");
        builder.setMessage("AI is " + (this.isAiIsOn() ? "ON" : "OFF") + "." + "\nChanging This will reset the game and the score");
        builder.setPositiveButton(this.isAiIsOn() ? "Turn off" : "Turn on", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                GridButtonPanel.this.setAiIsOn(!GridButtonPanel.this.isAiIsOn());
                GridButtonPanel.this.updateAiStatus();
                reset();
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
    }

    // Activity and context getters
    // used in gridButtons
    public Activity getActivity() {
        return activity;
    }

    public Context getContext() {
        return context;
    }

    // AI gamemode methods
    public boolean isAiIsOn() {
        return aiIsOn;
    }

    public void setAiIsOn(boolean aiIsOn) {
        this.aiIsOn = aiIsOn;
    }

    // Score methods
    public Score getScore() {
        return score;
    }

    public void updateScore() {
        ((TextView) getActivity().findViewById(R.id.scoreTextView)).setText(getScore().toString());
    }

    // Reseters
    public void resetScore() {
        score.reset();
        updateScore();
    }

    public void resetBoard() {
        resetTurn();
        for (GridButton gb : this.gridButtons) {
            gb.resetButton();
        }
    }

    public void reset() {
        resetScore();
        resetBoard();
    }

    // Turn methods
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public void resetTurn() {
        turn = 0;
    }

    public void incTurn() {
        turn++;
    }

    // and some getters
    public GridButton[] getGridButtons() {
        return gridButtons;
    }

    public TextView getAiStatus() {
        return aiStatus;
    }

    public void updateAiStatus() {
        this.aiStatus.setText((isAiIsOn() ? "AI" : "PvP"));
    }

    // Move method - used when a board button is pressed
    // Takes care of the AI
    public void move(Button but, int row, int col) {
        if (but.getText().toString().equals(blankString)) {
            // Select who's turn is
            String symbol = (getTurn() % 2 == 0) ? xString : oString;
            but.setText(symbol);

            this.incTurn();
            if (this.winConditionMet(row, col, symbol) != null) {
                if (this.winConditionMet(row, col, symbol).equals(xString)) {
                    this.getScore().xWon();
                    Toast.makeText(this.getContext(), "X won", Toast.LENGTH_SHORT).show();
                } else if (this.winConditionMet(row, col, symbol).equals(oString)) {
                    this.getScore().oWon();
                    Toast.makeText(this.getContext(), "O won (not you)", Toast.LENGTH_SHORT).show();
                } else {
                    this.getScore().tie();
                    Toast.makeText(this.getContext(), "You are both losers.", Toast.LENGTH_SHORT).show();
                }

                this.updateScore();
                this.resetBoard();
            }
            // do not show toast if the ai tires to move
        } else if ((!this.isAiIsOn()) || (this.isAiIsOn() && this.getTurn() % 2 == 0)) {
            Toast.makeText(this.getContext(), "That cell is already used", Toast.LENGTH_SHORT).show();
        }


        // AI down below
        // Immediately after the player makes a move, the ai makes one as well
        // TODO: make a better ai
        if (this.aiIsOn) {
            if (getTurn() % 2 == 1) {
                int randRow;
                int randCol;
                do {
                    randRow = (int) (Math.random() * 3) + 1;
                    randCol = (int) (Math.random() * 3) + 1;
                    Log.d("De ce nu merge?", "AI: " + randRow + ", " + randCol);
                }
                while (!this.getGridButtons()[(randRow - 1) * 3 + randCol - 1].getBut().getText().equals(blankString));

                move(this.getGridButtons()[(randRow - 1) * 3 + randCol - 1].getBut(), randRow, randCol);
            }
        }
    }

    // Takes the row and column of the pressed button and the button symbol
    // so that only the relevant row, column, diagonals are checked
    public String winConditionMet(int selectedRow, int selectedCol, String symbol) {

        Log.d("ASDF", "turn: " + turn + " rc: " + selectedRow + ", " + selectedCol + " sym: " + symbol);
        for (int i = 0; i < 9; i++) {
            Log.d("ASDF", i + ":" + gridButtons[i].getButtonText());
        }

        // Testing rows
        for (int col = 0; col < 3; col++) {
            int index = (selectedRow - 1) * 3 + col;

            if (!symbol.equals(gridButtons[index].getButtonText())) {
                break;
            }
            if (col == 2) {
                return symbol;
            }
        }

        // Testing cols
        for (int row = 0; row < 3; row++) {
            int index = row * 3 + selectedCol - 1;

            if (!symbol.equals(gridButtons[index].getButtonText())) {
                break;
            }
            if (row == 2) {
                return symbol;
            }
        }

        // Testing diags
        // Primary diag
        if (selectedRow == selectedCol) {
            for (int i = 0; i < 3; i++) {
                int index = i * 3 + i;

                if (!symbol.equals(gridButtons[index].getButtonText())) {
                    break;
                }
                if (i == 2) {
                    return symbol;
                }
            }
        }

        // Secondary diag
        if (selectedRow == 4 - selectedCol) {
            for (int i = 0; i < 3; i++) {
                int index = i * 3 + 2 - i;

                if (!symbol.equals(gridButtons[index].getButtonText())) {
                    break;
                }
                if (i == 2) {
                    return symbol;
                }
            }
        }

        if (turn == 9) {
            return DRAW;
        }

        return null;
    }

    private String getButtonTexts() {
        String buttonTexts = "";
        for (int i = 0; i < 9; i++) {
            buttonTexts += this.gridButtons[i].getButtonText();
        }
        return buttonTexts;
    }

    // File IO
    public void save() {
        FileOutputStream fileOutputStream;
        // Score
        byte[] scoreData = (this.score.toString() + "#").getBytes();
        // Board
        byte[] boardData = (this.getButtonTexts() + "#").getBytes();
        // Game turn
        byte[] turnData = (this.getTurn() + "#").getBytes();
        // Game mode
        byte[] gamemodeData = ("" + this.isAiIsOn()).getBytes();

        try {
            fileOutputStream = activity.openFileOutput("data.txt", Context.MODE_PRIVATE);
            fileOutputStream.write(scoreData);
            fileOutputStream.write(boardData);
            fileOutputStream.write(turnData);
            fileOutputStream.write(gamemodeData);
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        FileInputStream fileInputStream;

        try {
            fileInputStream = this.activity.openFileInput("data.txt");
            int read;
            StringBuffer stringBuffer = new StringBuffer();
            while ((read = fileInputStream.read()) != -1) {
                stringBuffer.append((char) read);
            }

            Log.d("BUFF", stringBuffer.toString());

            // Parsing the data
            String[] resources = stringBuffer.toString().split("#");
            for(String s: stringBuffer.toString().split("#")) {
                Log.d("BUFF", "please work: " + s);
            }
            Log.d("BUFF", "please work: " + resources[0]);

            // Score data
            String[] scoreRes = resources[0].split(" - ");
            for(String s: scoreRes) {
                Log.d("BUFF", "please work2: " + s);
            }

            this.getScore().set(Integer.parseInt(scoreRes[0]), Integer.parseInt(scoreRes[1]), Integer.parseInt(scoreRes[2]));
            this.updateScore();

            // Board data
            String[] boardRes = resources[1].split("");
            for (int i = 0; i < 9; i++) {
                Log.d("BUFF", "please work3: [" + boardRes[i + 1] + "]");
                this.getGridButtons()[i].getBut().setText(boardRes[i + 1]);
            }

            // Turn data
            this.setTurn(Integer.parseInt(resources[2]));

            // Gamemode data
            String gamemodeRes = resources[3];
            this.setAiIsOn(Boolean.parseBoolean(gamemodeRes));

        } catch (FileNotFoundException e) {
            this.aiNewGameAlertDialog();
            e.printStackTrace();
        } catch (IOException e) {
            this.aiNewGameAlertDialog();
            e.printStackTrace();
        } catch (Exception e) {
            this.aiNewGameAlertDialog();
            e.printStackTrace();
        }
    }
}
