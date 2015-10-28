package com.example.robertvacareanu.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        TicTacToeManager.getInstance().setActivity(this);
        DataManager.getInstance().setActivity(this);
        DataManager.getInstance().loadData();
        TicTacToeManager.getInstance().newGame();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        DataManager.getInstance().saveData();
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
        if (id == R.id.action_exit) {
            DataManager.getInstance().saveData();
            finish();
            return true;
        } else if (id == R.id.action_reset_score) {
            TicTacToeManager.getInstance().resetScore();
            return true;
        } else if (id == R.id.action_new_game) {
            TicTacToeManager.getInstance().newGame();
            return true;
        } else if (id == R.id.action_two_players) {
            TicTacToeManager.getInstance().newGame();
            TicTacToeManager.getInstance().setTypeOfMatch(true);
            return true;
        } else if (id == R.id.action_vs_computer) {
            TicTacToeManager.getInstance().newGame();
            TicTacToeManager.getInstance().setTypeOfMatch(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}