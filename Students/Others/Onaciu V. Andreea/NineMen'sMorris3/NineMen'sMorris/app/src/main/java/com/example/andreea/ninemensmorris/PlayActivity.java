package com.example.andreea.ninemensmorris;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Models.Game;


public class PlayActivity extends AppCompatActivity {
    Button matrix[][] = new Button[3][8];
    Button playerButton;
    TextView message;
    Game myGame;

    static final int nrOfRows = 3;
    static final int nrOfColumns = 8;

    int currentPlayer;
    boolean canStole ;
    boolean isButtonSelected;
    int initialRow ;
    int initialColumn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        resetGame();


        for (int i = 0; i < nrOfRows; i++) {
            for (int j = 0; j < nrOfColumns; j++) {
                final int rows = i;
                final int columns = j;
                matrix[rows][columns].setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        if (myGame.checkIfWinner() != 0) {
                            Toast.makeText(getApplicationContext(), "Player " + myGame.checkIfWinner() + " wins !", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (canStole) {
                                currentPlayer = steallingPart(rows, columns, currentPlayer);
                            } else if (!myGame.checkIfAllBeansOnGame()) {
                                currentPlayer = puttingBeansPart(rows, columns, currentPlayer);
                            } else {
                                currentPlayer = moveButtonsPart(rows, columns, currentPlayer);
                            }

                            changeState();
                            //message.setText(setMessage());
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_play, menu);
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
                resetGame();
                break;
            case R.id.Go_Back_To_Menu:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    protected int steallingPart(int rows, int columns, int currentPlayer) {
        if (myGame.stoleBean(rows, columns, currentPlayer)) {
            currentPlayer = changePlayer(currentPlayer);
            canStole = false;
            Drawable colorRoundButton = getResources().getDrawable(R.drawable.roundedbutton);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                matrix[rows][columns].setBackground(colorRoundButton);
            }

        } else  Toast.makeText(getApplicationContext(), "This bean cannot be stolen", Toast.LENGTH_LONG).show();

        return currentPlayer;
    }

    protected int moveButtonsPart(int row, int column, int currentPlayer) {
        if (!isButtonSelected) {
            isButtonSelected = true;
            initialRow = row;
            initialColumn = column;
        } else {

            if (myGame.moveBean(initialRow, initialColumn, row, column, currentPlayer)) {
                //Drawable colorRoundButton;

                //if (currentPlayer == 1) {
                  //  colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
                //} else {
                  //  colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer2);
                //}

                //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                  //  matrix[row][column].setBackground(colorRoundButton);
                //}
                changeBackgroundButtonAccordingToWhosNext(matrix[row][column]);
                Drawable RoundButton = getResources().getDrawable(R.drawable.roundedbutton);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    matrix[initialRow][initialColumn].setBackground(RoundButton);
                }

                if (myGame.checkIfMill(row, column)) {
                    canStole = true;

                } else {
                    currentPlayer = changePlayer(currentPlayer);
                }
            } else Toast.makeText(getApplicationContext(), "Move is not available", Toast.LENGTH_SHORT).show();

            isButtonSelected = false;
        }
        return currentPlayer;
    }


    protected int puttingBeansPart(int rows, int columns, int currentPlayer) {

        if (myGame.putBean(rows, columns, currentPlayer)) {
           /* Drawable colorRoundButton;
            if (currentPlayer == 1) {
                colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
        } else {
                colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer2);
        }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
             matrix[rows][columns].setBackground(colorRoundButton);}*/
              changeBackgroundButtonAccordingToWhosNext(matrix[rows][columns]);

            if (myGame.checkIfMill(rows, columns)) {
                canStole = true;
            } else
                currentPlayer = changePlayer(currentPlayer);
        }
        if (myGame.checkIfAllBeansOnGame()) {
            Toast.makeText(getApplicationContext(), "All beans on game", Toast.LENGTH_LONG).show();
        }
        return currentPlayer;
    }

    protected  void resetGame(){

        initializeButtons();
        myGame = new Game();
        message = (TextView) findViewById(R.id.message);
        message.setText("Player 1, put a bean on the board!");
        currentPlayer=1;
        canStole=false;
        isButtonSelected=false;
        initialRow=0;
        initialColumn=0;
    }

    protected void initializeButtons() {


        matrix[0][0] = (Button) findViewById(R.id.bean00);
        matrix[0][1] = (Button) findViewById(R.id.bean01);
        matrix[0][2] = (Button) findViewById(R.id.bean02);
        matrix[0][3] = (Button) findViewById(R.id.bean03);
        matrix[0][4] = (Button) findViewById(R.id.bean04);
        matrix[0][5] = (Button) findViewById(R.id.bean05);
        matrix[0][6] = (Button) findViewById(R.id.bean06);
        matrix[0][7] = (Button) findViewById(R.id.bean07);
        matrix[1][0] = (Button) findViewById(R.id.bean10);
        matrix[1][1] = (Button) findViewById(R.id.bean11);
        matrix[1][2] = (Button) findViewById(R.id.bean12);
        matrix[1][3] = (Button) findViewById(R.id.bean13);
        matrix[1][4] = (Button) findViewById(R.id.bean14);
        matrix[1][5] = (Button) findViewById(R.id.bean15);
        matrix[1][6] = (Button) findViewById(R.id.bean16);
        matrix[1][7] = (Button) findViewById(R.id.bean17);
        matrix[2][0] = (Button) findViewById(R.id.bean20);
        matrix[2][1] = (Button) findViewById(R.id.bean21);
        matrix[2][2] = (Button) findViewById(R.id.bean22);
        matrix[2][3] = (Button) findViewById(R.id.bean23);
        matrix[2][4] = (Button) findViewById(R.id.bean24);
        matrix[2][5] = (Button) findViewById(R.id.bean25);
        matrix[2][6] = (Button) findViewById(R.id.bean26);
        matrix[2][7] = (Button) findViewById(R.id.bean27);

        for (int i = 0; i < nrOfRows; i++) {
            for (int j = 0; j < nrOfColumns; j++) {
                Drawable colorRoundButton;
                colorRoundButton = getResources().getDrawable(R.drawable.roundedbutton);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    matrix[i][j].setBackground(colorRoundButton);
                }
            }
        }

        playerButton = (Button) findViewById(R.id.whosThePlayer);
        Drawable colorRoundButton;
        colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            playerButton.setBackground(colorRoundButton);
        }
    }

    public int changePlayer(int currentPlayer) {
        if (currentPlayer == 1) return 2;
        else return 1;
    }


    public  void changeState(){
        message.setText(setMessage());
        changeBackgroundButtonAccordingToWhosNext(playerButton);
    }

    public CharSequence setMessage() {
        CharSequence message = "";
        if (canStole) {
            message = "Player" + currentPlayer + ", you can stole from player" + changePlayer(currentPlayer) + " a bean !";
        } else if (!myGame.checkIfAllBeansOnGame()) {
            message = "Player " + currentPlayer + ",put bean on the board!";
        } else
            message = "Player " + currentPlayer + ",move a bean!";
        return message;
    }


    public void changeBackgroundButtonAccordingToWhosNext(Button button) {
        Drawable colorRoundButton;
        if (currentPlayer == 1) {
            colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
        } else {
            colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer2);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackground(colorRoundButton);
        }
    }
}