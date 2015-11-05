package com.example.andreea.mytictactoe;

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

public class MainActivity extends AppCompatActivity {
    final gameController game=new gameController();
    final Button[] theButtons=new Button[9];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        theButtons[0]=(Button) findViewById(R.id.button0);
        theButtons[1]=(Button) findViewById(R.id.button1);
        theButtons[2]=(Button) findViewById(R.id.button2);
        theButtons[3]=(Button) findViewById(R.id.button3);
        theButtons[4]=(Button) findViewById(R.id.button4);
        theButtons[5]=(Button) findViewById(R.id.button5);
        theButtons[6]=(Button) findViewById(R.id.button6);
        theButtons[7]=(Button) findViewById(R.id.button7);
        theButtons[8]=(Button) findViewById(R.id.button8);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
        game.resetSquares();


        for ( int i=0;i<9;i++) {
            final int pos=i;
            theButtons[pos].setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!game.squaresAreFull()) {

                        theButtons[pos].setText(game.setSquares(pos));

                            String s=game.checksWinner();
                            if (!s.equals("No one")) {
                            Toast.makeText(getApplicationContext(), s+" wins ",
                                    Toast.LENGTH_LONG).show();
                            game.resetSquares();
                                TextView score=(TextView) findViewById(R.id.score);
                                score.setText(game.score(s));

                            for(int i=0;i<9;i++) theButtons[i].setText("");
                        }
                    }

                    else {
                        String s=game.checksWinner();
                        Toast.makeText(getApplicationContext(), s+" wins ",
                                Toast.LENGTH_LONG).show();
                        game.resetSquares();
                        TextView score=(TextView) findViewById(R.id.score);
                        score.setText(game.score(s));
                        for(int i=0;i<9;i++) theButtons[i].setText("");
                    }


                }
            });
        }
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
        switch(id) {
            case R.id.Exit_Application:
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                break;
            case R.id.Reset_Match:
                game.resetSquares();
                for(int i=0;i<9;i++) theButtons[i].setText("");
                break;
            case R.id.Reset_Score:
                game.resetScore();
                TextView score=(TextView) findViewById(R.id.score);
                score.setText(game.score(""));

        }
        return super.onOptionsItemSelected(item);
    }
}
