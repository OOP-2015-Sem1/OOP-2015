package com.example.corina.tic_tac_toe;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private int mScorePlayerO = 0;
    private int mScorePlayerX = 0;
    private String mPlayerTurn = "X";
    private Button XO1;
    private Button XO2;
    private Button XO3;
    private Button XO4;
    private Button XO5;
    private Button XO6;
    private Button XO7;
    private Button XO8;
    private Button XO9;
    private Boolean somebodyWon = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        // initialize text state
        changeTextState(0, 0);
        // initialize grid view
        emptyGridView();

        Button restartGame = (Button) findViewById(R.id.restart_game);
        Button newGame = (Button) findViewById(R.id.new_game);

        restartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emptyGridView();
                changeTextState(0, 0);
                somebodyWon = false;
                mPlayerTurn = "X";
                Log.e("TAG", "restart game ");
            }
        });

        GridLayout table = (GridLayout) findViewById(R.id.table);

        XO1 = (Button) table.findViewById(R.id.button1);
        XO2 = (Button) table.findViewById(R.id.button2);
        XO3 = (Button) table.findViewById(R.id.button3);

        XO4 = (Button) table.findViewById(R.id.button4);
        XO5 = (Button) table.findViewById(R.id.button5);
        XO6 = (Button) table.findViewById(R.id.button6);

        XO7 = (Button) table.findViewById(R.id.button7);
        XO8 = (Button) table.findViewById(R.id.button8);
        XO9 = (Button) table.findViewById(R.id.button9);

        buttonClick(XO1);
        buttonClick(XO2);
        buttonClick(XO3);
        buttonClick(XO4);
        buttonClick(XO5);
        buttonClick(XO6);
        buttonClick(XO7);
        buttonClick(XO8);
        buttonClick(XO9);

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                somebodyWon = false;
                mPlayerTurn = "X";
                emptyGridView();
            }
        });
    }

    private void buttonClick(final Button b){
        Log.e("TAG","button "+b);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b.getText().equals(" ")) {
                    String complete = switchXorO();
                    b.setText(complete);
                    if (!somebodyWon) {
                        Log.e("TAG","buttonClick "+somebodyWon);
                        calculate();
                    }
                }
            }
        });
    }

    private void calculate() {
        Log.e("TAG","calculate");
        String result = findGameWinner();
        if (result.equals("O")) {
            Log.e("TAG","calculate O");
            Toast.makeText(this, "The winner is O", Toast.LENGTH_SHORT).show();
            mScorePlayerO++;
            changeTextState(mScorePlayerO, mScorePlayerX);
        } else if (result.equals("X")) {
            Log.e("TAG","calculate X");
            Toast.makeText(this, "The winner is X", Toast.LENGTH_SHORT).show();
            mScorePlayerX++;
            changeTextState(mScorePlayerO, mScorePlayerX);
        } else {
            Log.e("TAG","no winner yet");
        }
    }

    private String switchXorO() {
        if (mPlayerTurn.equals("X")) {
            mPlayerTurn = "O";
            return "X";
        } else {
            mPlayerTurn = "X";
        }
        return "O";
    }

    private String findGameWinner() {
        Log.e("TAG","findGameWinner "+somebodyWon);
        if (!somebodyWon) {
            if (XO1.getText().equals(XO2.getText()) && XO1.getText().equals(XO3.getText())
                    && !(XO1.getText().equals(" "))) {
                Log.e("TAG", "yufer 1 " + String.valueOf(XO1.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO1.getText());
            }
            if (XO4.getText().equals(XO5.getText()) && XO4.getText().equals(XO6.getText())
                    && !(XO4.getText().equals(" "))) {
                Log.e("TAG", "yufer 2 " + String.valueOf(XO4.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO4.getText());
            }
            if ((XO7.getText() == (XO8.getText()) && XO8.getText() == XO9.getText())
                    && (XO7.getText() != " ")) {
                Log.e("TAG", "yufer 3 " + String.valueOf(XO9.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO9.getText());
            }
            if ((XO1.getText() == XO5.getText() && XO5.getText() == XO9.getText())
                    && (XO1.getText() != " ")) {
                Log.e("TAG", "yufer 4 " + String.valueOf(XO5.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO5.getText());
            }
            if ((XO1.getText() == (XO4.getText()) && XO4.getText() == XO7.getText())
                    && (XO1.getText() !=  " ")) {
                Log.e("TAG", "yufer 5 " + String.valueOf(XO4.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO4.getText());
            }
            if ((XO2.getText() == (XO5.getText()) && XO5.getText() == XO8.getText())
                    && (XO2.getText() != " ")) {
                Log.e("TAG", "yufer 6 " + String.valueOf(XO5.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO5.getText());
            }
            if ((XO3.getText() == (XO6.getText()) && XO6.getText() == XO9.getText())
                    && (XO3.getText() != " ")) {
                Log.e("TAG", "yufer 7 " + String.valueOf(XO9.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO9.getText());
            }
            if ((XO3.getText() == (XO5.getText()) && XO5.getText() == XO7.getText())
                    && (XO3.getText() != " ")) {
                Log.e("TAG", "yufer 8 " + String.valueOf(XO7.getText()));
                somebodyWon = true;
                Log.e("TAG","return something");
                return String.valueOf(XO7.getText());
            }
        }
        return "NO";
    }

    private void emptyGridView() {
        Log.e("TAG", "emptyGridView");

        GridLayout table = (GridLayout) findViewById(R.id.table);

        XO1 = (Button) table.findViewById(R.id.button1);
        XO2 = (Button) table.findViewById(R.id.button2);
        XO3 = (Button) table.findViewById(R.id.button3);

        XO4 = (Button) table.findViewById(R.id.button4);
        XO5 = (Button) table.findViewById(R.id.button5);
        XO6 = (Button) table.findViewById(R.id.button6);

        XO7 = (Button) table.findViewById(R.id.button7);
        XO8 = (Button) table.findViewById(R.id.button8);
        XO9 = (Button) table.findViewById(R.id.button9);

        XO1.setText(" ");
        XO2.setText(" ");
        XO3.setText(" ");
        XO4.setText(" ");
        XO5.setText(" ");
        XO6.setText(" ");
        XO7.setText(" ");
        XO8.setText(" ");
        XO9.setText(" ");
    }

    private void changeTextState(Integer O, Integer X) {
        Log.e("TAG","changeTextState");
        TextView playerO = (TextView) findViewById(R.id.player_O);
        TextView playerX = (TextView) findViewById(R.id.player_X);

        playerO.setText(" " + O);
        playerX.setText(" " + X);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatementtate

        return super.onOptionsItemSelected(item);
    }
}