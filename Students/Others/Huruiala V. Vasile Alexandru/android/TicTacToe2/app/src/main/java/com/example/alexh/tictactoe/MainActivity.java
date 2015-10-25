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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("P", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeGridButtons();
        gbp.save();
    }


    @Override
    protected void onDestroy() {
        Log.d("P", "onDestroy");
        gbp.save();
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
            gbp.resetBoard();
            return true;
        } else if (id == R.id.reset_score) {
            gbp.resetScore();
            return true;
        } else if (id == R.id.game_mode) {
            gbp.aiChangeAlertDialog();
        } else if (id == R.id.exit) {
            //System.exit(0); // too brutal apparently
            gbp.save();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private GridButtonPanel gbp;

    private void initializeGridButtons() {
        Button[] buttons = {(Button) findViewById(R.id.button1_1), (Button) findViewById(R.id.button1_2), (Button) findViewById(R.id.button1_3),
                (Button) findViewById(R.id.button2_1), (Button) findViewById(R.id.button2_2), (Button) findViewById(R.id.button2_3),
                (Button) findViewById(R.id.button3_1), (Button) findViewById(R.id.button3_2), (Button) findViewById(R.id.button3_3)};

        gbp = new GridButtonPanel(getApplicationContext(), this, buttons);

        // Resize buttons according to the screen size and orientation
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        int width = point.x;
        int height = point.y;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            for (int i = 0; i < 9; i++) {
                gbp.getGridButtons()[i].getBut().setWidth(width / 3 - 30);
                gbp.getGridButtons()[i].getBut().setHeight(height / 4 - 20);
                gbp.getGridButtons()[i].getBut().setTextSize(85);
            }
        } else {
            for (int i = 0; i < 9; i++) {
                gbp.getGridButtons()[i].getBut().setWidth(width / 3 - 30);
                gbp.getGridButtons()[i].getBut().setHeight(height / 5 - 50);
                gbp.getGridButtons()[i].getBut().setTextSize(26);
            }
        }
    }
}
