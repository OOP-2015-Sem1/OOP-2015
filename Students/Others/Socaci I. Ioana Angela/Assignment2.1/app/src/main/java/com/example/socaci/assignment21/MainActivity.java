package com.example.socaci.assignment21;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button[] buttons = new Button[9];
        buttons[0] = (Button) findViewById(R.id.button);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);
        buttons[4] = (Button) findViewById(R.id.button5);
        buttons[5] = (Button) findViewById(R.id.button6);
        buttons[6] = (Button) findViewById(R.id.button7);
        buttons[7] = (Button) findViewById(R.id.button8);
        buttons[8] = (Button) findViewById(R.id.button9);

        final TextView score = (TextView) findViewById(R.id.textView3);
        score.setText("Score: 0-0");

        final TicTacToeGame game = new TicTacToeGame();

        for(int i = 0; i < buttons.length; i++){
            final int j = i;
            buttons[i].setOnClickListener(
                    new Button.OnClickListener(){

                        public void onClick(View v){

                            onClickEvent(buttons[j], game, j, score);

                            if (!(game.getWinnerVar().equals(""))) {
                                buttons[0].setText("");
                                buttons[1].setText("");
                                buttons[2].setText("");
                                buttons[3].setText("");
                                buttons[4].setText("");
                                buttons[5].setText("");
                                buttons[6].setText("");
                                buttons[7].setText("");
                                buttons[8].setText("");

                                game.resetGame();
                            }

                        }
                    }
            );
        }

    }

    public void onClickEvent(Button b, TicTacToeGame game, int i, TextView score){
        if (b.getText().equals("")) {//if the button was not used yet

            b.setText(game.setButtonText());

            game.setString(i, b.getText().toString());


        }
        game.getWinner();

        if (game.getCountButtons() == 9 || !(game.getWinnerVar().equals(""))) {

            Toast.makeText(this, game.getWinnerVar(), Toast.LENGTH_SHORT).show();
            score.setText(game.getScore());

        }
    }
}

