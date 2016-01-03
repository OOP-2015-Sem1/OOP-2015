package com.example.robertvacareanu.zoowsomeandroid1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import models.animals.Animal;
import repositories.AnimalRepository;

public class ListActivity extends AppCompatActivity {


    final DetailsFragment df = new DetailsFragment();
    ArrayList<Animal> animals;

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AnimalRepository ap = new AnimalRepository(this);

        try {
            animals = ap.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new CustomAdapter(this, animals);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getFragmentManager().beginTransaction().replace(R.id.activity_list, df).addToBackStack(null).commit();
                getFragmentManager().executePendingTransactions();
                df.setText(animals.get(position).toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        int fragments = getFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            startActivity(new Intent(this, ListActivity.class));
        }
        super.onBackPressed();
    }
}
