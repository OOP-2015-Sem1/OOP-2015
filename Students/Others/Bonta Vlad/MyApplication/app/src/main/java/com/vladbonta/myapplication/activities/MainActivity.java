package com.vladbonta.myapplication.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vladbonta.myapplication.R;
import com.vladbonta.myapplication.logic.Game;

/**
 * @author VladBonta on 27/12/15.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        prepareForNewGame();

    }

    public void prepareForNewGame(){
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 8));
        Game game = new Game(mRecyclerView, MainActivity.this);
    }
}

