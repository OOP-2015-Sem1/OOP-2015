package com.example.alexh.tictactoe;

import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("P", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeGridButtons();
        board.save();
    }


    @Override
    protected void onDestroy() {
        Log.d("P", "onDestroy");
        board.save();
        super.onDestroy();
    }

    @Override
    protected void onPostResume() {
        Log.d("P", "onPostResume");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        Log.d("P", "onStop");
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        Log.d("P", "onBackPressed");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        Log.d("P", "onPause");
        board.save();
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d("P", "onResume");
        super.onResume();
    }

    @Override
    protected void onResumeFragments() {
        Log.d("P", "onResumeFragments");
        super.onResumeFragments();
    }

    @Override
    protected void onStart() {
        Log.d("P", "onStart");
        super.onStart();
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
        if (id == R.id.new_round) {
            board.gameBoard.resetButtons();
            return true;
        } else if (id == R.id.reset_score) {
            board.gameBoard.resetScore();
            return true;
        } else if (id == R.id.game_mode) {
            board.gameBoard.changeAi();
        } else if (id == R.id.exit) {
            //System.exit(0); // too brutal apparently
            board.save();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Board board;

    private void initializeGridButtons() {

        LinkedList<Button> buttonLinkedList = new LinkedList<>();
        buttonLinkedList.add((Button) findViewById(R.id.button1_1));
        buttonLinkedList.add((Button) findViewById(R.id.button1_2));
        buttonLinkedList.add((Button) findViewById(R.id.button1_3));
        buttonLinkedList.add((Button) findViewById(R.id.button2_1));
        buttonLinkedList.add((Button) findViewById(R.id.button2_2));
        buttonLinkedList.add((Button) findViewById(R.id.button2_3));
        buttonLinkedList.add((Button) findViewById(R.id.button3_1));
        buttonLinkedList.add((Button) findViewById(R.id.button3_2));
        buttonLinkedList.add((Button) findViewById(R.id.button3_3));

        board = new Board(this, buttonLinkedList, (TextView) findViewById(R.id.scoreTextView), (TextView) findViewById(R.id.aiStatus));

        // Resize buttons according to the screen size and orientation
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setWidth(width / 3 - 30);
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setHeight(height / 4 - 20);
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setTextSize(85);
                }
            }
        } else {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setWidth(width / 3 - 30);
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setHeight(height / 5 - 50);
                    board.gameBoard.gridButtonPanel.getButtonAt(row, col).setTextSize(26);
                }
            }
        }
    }
}
