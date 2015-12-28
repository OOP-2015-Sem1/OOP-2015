package com.example.andreea.ninemensmorris;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Models.Game;


public class PlayActivity extends AppCompatActivity {
    Button matrix[][]=new Button[3][8];
    TextView message;

    static final int nrOfRows=3;
    static final int nrOfColumns=8;
    Game myGame=new Game();
    boolean whosNext=true;
    boolean canStole=false;
    boolean isButtonSelected=false;
    int initialRow=0;
    int initialColumn=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initializeButtons();
        myGame=new Game();
        message=(TextView) findViewById(R.id.message);


       for(int i=0;i<nrOfRows;i++){
            for(int j=0;j<nrOfColumns;j++){
                final int rows=i;
                final int columns=j;
                matrix[rows][columns].setOnClickListener(new View.OnClickListener(){

                    public void onClick(View view) {
                        if (myGame.checkIfWinner()!=0) {
                            Toast.makeText(getApplicationContext(), "Player "+myGame.checkIfWinner()+ " wins !", Toast.LENGTH_LONG).show();
                        }
                        else {
                            if (canStole) {
                                whosNext = steallingPart(rows, columns, whosNext);
                            }
                            else if (!myGame.checkIfAllBeansOnGame()) {

                                if (whosNext)
                                    message.setText("Player 2, put bean on the board !");
                                else
                                    message.setText("Player 1, put bean on the board !");

                                whosNext = puttingBeansPart(rows, columns, whosNext);

                                if (myGame.checkIfAllBeansOnGame()) {
                                    Toast.makeText(getApplicationContext(), "All beans on game", Toast.LENGTH_LONG).show();
                                    if (!canStole) {
                                        if (whosNext)
                                            message.setText("Player 1, move a bean !");
                                        else
                                            message.setText("Player 2, move a bean !");
                                    }
                                }
                                if (canStole) {
                                    if (whosNext)
                                        message.setText("Player 1, you can stole from player 2 a bean !");
                                    else
                                        message.setText("Player 2, you can stole from player 1 a bean  !");
                                }
                            }
                            else {
                                if (whosNext)
                                    message.setText("Player 1, move a bean !");
                                else
                                    message.setText("Player 2, move a bean !");
                                whosNext = moveButtonsPart(rows, columns, whosNext);
                                if (canStole) {
                                    if (whosNext)
                                        message.setText("Player 1, you can stole from player 2 a bean !");
                                    else
                                        message.setText("Player 2, you can stole from player 1 a bean  !");
                                }
                            }

                        }
                    }
                });
            }
       }
    }


    protected boolean steallingPart(int rows,int columns,boolean player) {
        if (myGame.stoleBean(rows, columns, player)) {
            whosNext = !whosNext;
            canStole = false;
            Drawable colorRoundButton = getResources().getDrawable(R.drawable.roundedbutton);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                matrix[rows][columns].setBackground(colorRoundButton);
            }
            if (!myGame.checkIfAllBeansOnGame())
                if (whosNext)
                message.setText("Player 1, put bean on the board !");
                else
                message.setText("Player 2, put bean on the board !" );
            else {
                if (whosNext)
                    message.setText("Player 1, move a bean !");
                else
                    message.setText("Player 2, move a bean !" );
        }
        }
        else  message.setText("This bean cannot be stolen");
        return whosNext;
    }

    protected boolean moveButtonsPart(int row,int column,boolean whosNext){
        if (!isButtonSelected) {
            isButtonSelected = true;
            initialRow = row;
            initialColumn = column;
        }

        else{

                if (myGame.moveBean(initialRow, initialColumn, row, column, whosNext)) {
                    if (whosNext) {
                        Drawable colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            matrix[row][column].setBackground(colorRoundButton);
                        }
                    } else {
                        Drawable colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer2);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            matrix[row][column].setBackground(colorRoundButton);
                        }
                    }
                    Drawable colorRoundButtonn = getResources().getDrawable(R.drawable.roundedbutton);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        matrix[initialRow][initialColumn].setBackground(colorRoundButtonn);
                    }
                    if (myGame.checkIfMill(row, column)) {
                        canStole = true;
                        if (whosNext)
                            message.setText("Player 1, you can stole from player 2 a bean !");
                        else
                            message.setText("Player 2, you can stole from player 1 a bean  !" );

                    }
                    else {
                        whosNext=!whosNext;
                        if (whosNext)
                            message.setText("Player 1, move a bean !");
                        else
                            message.setText("Player 2, move a bean !" );
                    }
                }
               else message.setText("Move is not available");
                isButtonSelected = false;
            }
        return whosNext;
        }




    protected  boolean puttingBeansPart(int rows,int columns,boolean whosNext){
      if (myGame.putBean(rows, columns, whosNext)){
             if (whosNext) {
                 Drawable colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer1);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                     matrix[rows][columns].setBackground(colorRoundButton);
                 }
             }
          else {
                 Drawable colorRoundButton = getResources().getDrawable(R.drawable.buttonsofplayer2);
                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                     matrix[rows][columns].setBackground(colorRoundButton);
                 }
             }
          if (myGame.checkIfMill(rows,columns)){
              canStole=true;
          }
          else
          whosNext=!(whosNext);

      }
      return whosNext;
    }
    protected void initializeButtons(){


        matrix[0][0]=(Button) findViewById(R.id.bean00);
        matrix[0][1]=(Button) findViewById(R.id.bean01);
        matrix[0][2]=(Button) findViewById(R.id.bean02);
        matrix[0][3]=(Button) findViewById(R.id.bean03);
        matrix[0][4]=(Button) findViewById(R.id.bean04);
        matrix[0][5]=(Button) findViewById(R.id.bean05);
        matrix[0][6]=(Button) findViewById(R.id.bean06);
        matrix[0][7]=(Button) findViewById(R.id.bean07);
        matrix[1][0]=(Button) findViewById(R.id.bean10);
        matrix[1][1]=(Button) findViewById(R.id.bean11);
        matrix[1][2]=(Button) findViewById(R.id.bean12);
        matrix[1][3]=(Button) findViewById(R.id.bean13);
        matrix[1][4]=(Button) findViewById(R.id.bean14);
        matrix[1][5]=(Button) findViewById(R.id.bean15);
        matrix[1][6]=(Button) findViewById(R.id.bean16);
        matrix[1][7]=(Button) findViewById(R.id.bean17);
        matrix[2][0]=(Button) findViewById(R.id.bean20);
        matrix[2][1]=(Button) findViewById(R.id.bean21);
        matrix[2][2]=(Button) findViewById(R.id.bean22);
        matrix[2][3]=(Button) findViewById(R.id.bean23);
        matrix[2][4]=(Button) findViewById(R.id.bean24);
        matrix[2][5]=(Button) findViewById(R.id.bean25);
        matrix[2][6]=(Button) findViewById(R.id.bean26);
        matrix[2][7]=(Button) findViewById(R.id.bean27);
    }
}


