package com.example.maria.tictactoe;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    TicTacToeButton[] theButtons; //the nine buttons
    int[] theBoard;               //the game board

    private void initializeBoard() {

        theBoard = new int[9];
        theButtons = new TicTacToeButton[9];

        theButtons[0] = (TicTacToeButton) findViewById(R.id.btn11);
        theButtons[1] = (TicTacToeButton) findViewById(R.id.btn12);
        theButtons[2] = (TicTacToeButton) findViewById(R.id.btn13);

        theButtons[3] = (TicTacToeButton) findViewById(R.id.btn21);
        theButtons[4] = (TicTacToeButton) findViewById(R.id.btn22);
        theButtons[5] = (TicTacToeButton) findViewById(R.id.btn23);

        theButtons[6] = (TicTacToeButton) findViewById(R.id.btn31);
        theButtons[7] = (TicTacToeButton) findViewById(R.id.btn32);
        theButtons[8] = (TicTacToeButton) findViewById(R.id.btn33);

        //empty the game board
        for (int i = 0; i < 9; i++) {
            theBoard[i] = 2; //2 means empty because 0 -> 'O', 1 -> 'X'
            theButtons[i].setText(" ");
            theButtons[i].setEnabled(true);
        }

        TicTacToeButton btnClick = new TicTacToeButton(MainActivity.this);
        btnClick.buttonClick(theBoard, theButtons, MainActivity.this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeBoard();
        GameOver.setScore(0, 0, this, this);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.exit_app) {
            finish();
            System.exit(0);
        }

        if (id == R.id.reset_match) {
            initializeBoard();
        }

        if (id == R.id.reset_score) {
            GameOver.setScore(0, 0, this, this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }
}
