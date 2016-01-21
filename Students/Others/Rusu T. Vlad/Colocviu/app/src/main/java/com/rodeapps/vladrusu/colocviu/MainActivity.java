package com.rodeapps.vladrusu.colocviu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import stationAdministration.Train;
import stationAdministration.TrainStation;
import stationAdministration.Wagon;

public class MainActivity extends AppCompatActivity {

    private TrainStation trainStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

        return super.onOptionsItemSelected(item);
    }

    private void fillWagon(Wagon wagon) {
        //
    }

    private ArrayList<Wagon> putWagonsOnTrain() {
        //
        return null;
    }

    private void receiveSomeTrains() {
        TrainStation trainStation = this.trainStation;
        Train tempTrain;

        tempTrain = new Train("chu-chu", this.putWagonsOnTrain());
    }

    private void printSomeValues() {
        Log.v("tag", "will print some values");
    }

    private void createStation() {
        this.trainStation = new TrainStation();
        TrainStation trainStation = this.trainStation;

        this.receiveSomeTrains();
        this.printSomeValues();
    }
}
