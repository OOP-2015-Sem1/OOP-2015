package com.example.alexh.zoosome;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.alexh.zoosome.models.animals.Animal;
import com.example.alexh.zoosome.repositories.SQLiteZoosomeDatabase;
import com.example.alexh.zoosome.repositories.ZooData;
import com.example.alexh.zoosome.services.factories.animals.BigFactory;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

    private ListAdapter listAdapter;
    private SQLiteZoosomeDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // db stuff
        this.sql = new SQLiteZoosomeDatabase(this.getApplicationContext());
        int animalCount = this.sql.getNumberOfAnimals();
        Log.d("SQL", "before: " + animalCount);

        if (animalCount == 0) {
            BigFactory bigFactory = new BigFactory();
            this.sql.insertAnimals(BigFactory.convertAnimalArrayToArrayList(
                    bigFactory.generateRandomAnimalAmount(50)));
        }

        animalCount = this.sql.getNumberOfAnimals();
        Log.d("SQL", "after: " + animalCount);

        this.listAdapter = new AnimalAdapter(this, BigFactory.convertAnimalArrayListToArray(this.sql.readAllAnimals()));
        ListView listView = (ListView) findViewById(R.id.animalList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new ListOnItemClickListener());
    }

    private class ListOnItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Animal clickedAnimal = (Animal) ListActivity.this.listAdapter.getItem(position);

            ArrayList[] namesAndValues = clickedAnimal.getAllFieldNamesAndValues();
            String fields = "";
            for (int i = 0; i < namesAndValues[0].size(); i++) {
                fields += namesAndValues[0].get(i) + "=" + namesAndValues[1].get(i) + "; ";
            }

            Toast.makeText(ListActivity.this.getApplicationContext(), fields, Toast.LENGTH_SHORT).show();
        }
    }
}
