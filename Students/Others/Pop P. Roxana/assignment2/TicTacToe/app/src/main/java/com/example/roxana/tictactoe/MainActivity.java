package com.example.roxana.tictactoe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    Game game;
    Buttons buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttons = new Buttons();
        buttons.prepareButtonsArray();
        buttons.add((Button) findViewById(R.id.button0));
        buttons.add((Button) findViewById(R.id.button1));
        buttons.add((Button) findViewById(R.id.button2));
        buttons.add((Button) findViewById(R.id.button3));
        buttons.add((Button) findViewById(R.id.button4));
        buttons.add((Button) findViewById(R.id.button5));
        buttons.add((Button) findViewById(R.id.button6));
        buttons.add((Button) findViewById(R.id.button7));
        buttons.add((Button) findViewById(R.id.button8));

        LinearLayout buttonContainer = (LinearLayout)findViewById(R.id.rlBoard);
        int orientation =  this.getResources().getConfiguration().orientation;
        if(orientation== Configuration.ORIENTATION_PORTRAIT){

            //convert dp to pixels
            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (400 * scale );
            buttonContainer.getLayoutParams().height=pixels;
        }
        else{
            final float scale = this.getResources().getDisplayMetrics().density;
            int pixels = (int) (250 * scale );
            buttonContainer.getLayoutParams().height=pixels;
        }

        //in case of orientation change
        if(Game.isGameOn()){
            //the game has already started
            game = Game.getInstance();
            //
            buttons.setClickListeners(MainActivity.this, game, 1);
            buttons.restoreText(game);
        }
        else{
            //the game has now started
            game= Game.getInstance();
            //popping up alert dialog
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("Do you want to play against a computer?");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setPositiveButton("Yes", new yesButtonClickListener());
            alertDialogBuilder.setNegativeButton("No", new noButtonClickListener());

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setTitle("Choose mode");
            alertDialog.show();

        }

        //display the score
        Score score = new Score(this);
        score.displayScore();

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
       if (id == R.id.action_change_game_mode) {

           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
           alertDialogBuilder.setMessage("Choose game mode");
           alertDialogBuilder.setCancelable(true);
           alertDialogBuilder.setPositiveButton("Against computer", new yesButtonClickListener());
           alertDialogBuilder.setNegativeButton("Against human", new noButtonClickListener());

           AlertDialog alertDialog = alertDialogBuilder.create();
           alertDialog.setTitle("Game mode");
           alertDialog.show();

           //reset match
           Buttons buttons = new Buttons();
           buttons.refreshButtons(game);
           return true;
        } else if (id == R.id.action_reset_score) {

            Score score = new Score(this);
            score.setScore(0, 0);
            score.displayScore();
            return true;
        } else if (id == R.id.action_exit_application) {

            finish();
            System.exit(0);
            return true;
        } else if (id == R.id.action_reset_match) {

            Buttons buttons = new Buttons();
            buttons.refreshButtons(game);
            return true;
    }

        return super.onOptionsItemSelected(item);
    }

    class yesButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            //set clickListeners
            buttons.setClickListeners(MainActivity.this, game, 1);
            buttons.clearButtonsText();
            buttons.setDefaultButtonsColor();

            SharedPreferences sharedPreferences=MainActivity.this.getSharedPreferences("GameData", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putInt("mode", 1);
            editor.commit();

        }
    }

    class noButtonClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

            //set clickListeners
            buttons.setClickListeners(MainActivity.this, game, 0);
            buttons.clearButtonsText();
            buttons.setDefaultButtonsColor();

            SharedPreferences sharedPreferences=MainActivity.this.getSharedPreferences("GameData", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putInt("mode", 0);
            editor.commit();

        }
    }
}

