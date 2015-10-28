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
    private Button[] mButtons = new Button[9];
    private String mPlayerTurn;
    private Boolean somebodyWon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        mPlayerTurn = "X";
        somebodyWon = false;

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
                mScorePlayerO = 0;
                mScorePlayerX = 0;
                mPlayerTurn = "X";
                Log.e("TAG", "restart game ");
            }
        });

        GridLayout table = (GridLayout) findViewById(R.id.table);

        mButtons[0] = (Button) table.findViewById(R.id.button1);
        mButtons[1] = (Button) table.findViewById(R.id.button2);
        mButtons[2] = (Button) table.findViewById(R.id.button3);

        mButtons[3] = (Button) table.findViewById(R.id.button4);
        mButtons[4] = (Button) table.findViewById(R.id.button5);
        mButtons[5] = (Button) table.findViewById(R.id.button6);

        mButtons[6] = (Button) table.findViewById(R.id.button7);
        mButtons[7] = (Button) table.findViewById(R.id.button8);
        mButtons[8] = (Button) table.findViewById(R.id.button9);

        for(int i=0; i<9; i++) {
            buttonClick(mButtons[i]);
        }

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
        FindGameWinner wether = new FindGameWinner(somebodyWon, mButtons);
        String result = wether.gameWinner();
        somebodyWon = wether.getSomebodyWon();
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

    private void emptyGridView() {
        Log.e("TAG", "emptyGridView");

        GridLayout table = (GridLayout) findViewById(R.id.table);

        mButtons[0] = (Button) table.findViewById(R.id.button1);
        mButtons[1] = (Button) table.findViewById(R.id.button2);
        mButtons[2] = (Button) table.findViewById(R.id.button3);

        mButtons[3] = (Button) table.findViewById(R.id.button4);
        mButtons[4] = (Button) table.findViewById(R.id.button5);
        mButtons[5] = (Button) table.findViewById(R.id.button6);

        mButtons[6] = (Button) table.findViewById(R.id.button7);
        mButtons[7] = (Button) table.findViewById(R.id.button8);
        mButtons[8] = (Button) table.findViewById(R.id.button9);

        mButtons[0].setText(" ");
        mButtons[1].setText(" ");
        mButtons[2].setText(" ");
        mButtons[3].setText(" ");
        mButtons[4].setText(" ");
        mButtons[5].setText(" ");
        mButtons[6].setText(" ");
        mButtons[7].setText(" ");
        mButtons[8].setText(" ");

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // noinspection SimplifiableIfStatementtate
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}